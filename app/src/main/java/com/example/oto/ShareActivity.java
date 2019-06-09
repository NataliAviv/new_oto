package com.example.oto;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ShareActivity extends AppCompatActivity {

    String TAG = "placeAutoComplete";
    Button mDatebtn;
    Calendar calendar;
    TextView ShowTheDate;
    DatePickerDialog datePickerDialog;
    EditText chooseTime;
    TimePickerDialog timePickerDialog;
    Calendar c2;
    int currentHour;
    int currentMinute;
    String ampm;
    Button btnSuggest;
    String source;
    String destination;
    EditText freePlaces;
    TextView textView;

    //ziv
    String tokenIDFirebase;
    //ziv end

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_share);

        /*start autocomplete */
        // Initialize Places.
        Places.initialize(getApplicationContext(), "AIzaSyCp5mD7PtTX4ikEnIhSBXPQwp45ze4qLAE");
        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_suggest);
        //add to autocomplete
        AutocompleteSupportFragment autocompleteFragment2 = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocompletefragment_suggest);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.ADDRESS, Place.Field.NAME));
        autocompleteFragment2.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.ADDRESS, Place.Field.NAME));
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                //txtView.setText(place.getName()+","+place.getId());
                Log.i(TAG, "Place: " + place.getAddress() + ", " + place.getId());
                source = place.getAddress();

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                //txtView.setText(place.getName()+","+place.getId());
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                destination = place.getAddress();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        /*finish autocomplete */
        ShowTheDate = (TextView) findViewById(R.id.date_show_sug);
        mDatebtn = (Button) findViewById(R.id.date_suggest);
        mDatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(com.example.oto.ShareActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mday) {
                        ShowTheDate.setText(mday + "/" + (mMonth + 1) + "/" + mYear);
                    }
                }, day, month, year);
                datePickerDialog.show();
            }
        });

        //TIMER
        chooseTime = findViewById(R.id.time_suggest);
        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c2 = Calendar.getInstance();
                currentHour = c2.get(Calendar.HOUR_OF_DAY);
                currentMinute = c2.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(com.example.oto.ShareActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12) {
                            ampm = "PM";
                        } else {
                            ampm = "AM";
                        }
                        chooseTime.setText(String.format("%02d:%02d", hourOfDay, minute) + ampm);
                    }
                }, currentHour, currentMinute, false);
                timePickerDialog.show();
            }

        });
        // freePlaces=findViewById(R.id.free_place);
        btnSuggest = (Button) findViewById(R.id.suggest_ride);
        textView = findViewById(R.id.tv_suggest);

        btnSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // User is signed in
                    App.setSource(source);
                    App.setDest(destination);
                    App.setDate(ShowTheDate.getText().toString());
                    App.setTime(chooseTime.getText().toString());

                    //Toast.makeText(ShareActivity.this, user.getUid(), Toast.LENGTH_SHORT).show();

                    user.getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        @Override
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if(task.isSuccessful()){
                                tokenIDFirebase = task.getResult().getToken();
                                JSONObject obj = new JSONObject();
                                try {
                                    obj.put("origin", App.getFirstName());
                                    obj.put("dest", App.getLastName());
                                    obj.put("date", App.getDate());
                                    obj.put("time", App.getTime());
                                    obj.put("driver", tokenIDFirebase);
                                    //obj.put("free Places",App.getPassword());
                                    // obj.put("driver",App.getEmail());
                                    // obj.put("id",App.getEmail());

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                        (Request.Method.POST, App.url + "ride/", obj, new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                Toast.makeText(ShareActivity.this, App.getUID()+"\n"+tokenIDFirebase, Toast.LENGTH_SHORT).show();
                                                textView.setText("connect to server!");
                                                try {
                                                    JSONObject user = (JSONObject) response.get("ride");
                                                    Toast.makeText(ShareActivity.this, "got ride", Toast.LENGTH_LONG).show();
                                                    if (user.has("token")) {
                                                        String token = user.getString("token");
                                                        App.setToken(token);
                                                        Toast.makeText(ShareActivity.this, App.getToken(), Toast.LENGTH_LONG).show();
                                                        openSuggestResult();
                                                    } else {
                                                        Toast.makeText(ShareActivity.this, "No Such User", Toast.LENGTH_LONG).show();
                                                        openRegisterStep1Activity();
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                error.printStackTrace();
                                                textView.setText("faild to connect to server!");
                                                //Toast.makeText(ShareActivity.this, "Login Server Failed", Toast.LENGTH_LONG).show();
                                                Log.i(error.getMessage(), "HttpPost() - onErrorResponse() ");

                                            }
                                        }) {
                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        HashMap<String, String> params = new HashMap<String, String>();
                                        String creds = String.format("%s:%s", "hqplayer", "valvole");
                                        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                                        params.put("Authorization", auth);
                                        return params;
                                    }
                                };

                                RequestQueue queue = Volley.newRequestQueue(App.getContext());
                                queue.add(jsonObjectRequest);

                                openSuggestResult();
                            }else{
                                Toast.makeText(ShareActivity.this, "Failed To Get Token From User", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                } else {
                    // No user is signed in
                }


                // App.setFreePlaces(freePlaces.getText().toString());
                /*    Start communication to server    */



                /*    Finished communication to server    */

            }
        });
    }


    public void openSuggestResult() {
        Intent intent = new Intent(this, SuggestResultActivity.class);
        startActivity(intent);
    }

    public void openRegisterStep1Activity() {
        Intent intent = new Intent(this, RegisterStep1Activity.class);
        startActivity(intent);
    }


}