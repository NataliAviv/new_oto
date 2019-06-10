package com.example.oto;

import android.annotation.SuppressLint;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.json.JSONObject;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ShareActivity extends AppCompatActivity {

    String TAG = "placeAutoComplete";

///Vkiew elements
    //suggest button
    Button btnSuggest;

   //Auto complete
    String origin_ac;
    String dest_ac;


    //time
    EditText chooseTime;
    TimePickerDialog timePickerDialog;
    String time;
    String ampm;
    int currentHour;
    int currentMinute;


    //date
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    Calendar c2;
    EditText chooseDate;
    String date;


    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_share);


        //Initialize views
        chooseTime = findViewById(R.id.time_suggest);
        btnSuggest = findViewById(R.id.suggest_btn);


        /*start autocomplete */
        // Initialize Places.
        Places.initialize(getApplicationContext(), "AIzaSyCp5mD7PtTX4ikEnIhSBXPQwp45ze4qLAE");
        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.to_ac_suggest);
        //add to autocomplete
        AutocompleteSupportFragment autocompleteFragment2 = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.from_ac_suggest);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ADDRESS,Place.Field.NAME));
        autocompleteFragment2.setPlaceFields(Arrays.asList(Place.Field.ADDRESS,Place.Field.NAME));
        autocompleteFragment.setCountry("il");
        autocompleteFragment2.setCountry("il");
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                //txtView.setText(place.getName()+","+place.getId());
                Log.i(TAG, "Place: " + place.getAddress() + ", " + place.getId());
                origin_ac = place.getAddress();
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
                Log.i(TAG, "Place: " + place.getAddress() + ", " + place.getId());
                dest_ac = place.getAddress();
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        /*finish autocomplete */
        //time dialog
        chooseTime=findViewById(R.id.time_suggest);
        chooseTime.setOnTouchListener(new View.OnTouchListener() {
            int callCount=0;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Boolean isShowing = true;
                    if (callCount == 0) {
                        c2 = Calendar.getInstance();
                        currentHour = c2.get(Calendar.HOUR_OF_DAY);
                        currentMinute = c2.get(Calendar.MINUTE);
                        timePickerDialog = new TimePickerDialog(ShareActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                if (hourOfDay >= 12) {
                                    ampm = " PM";
                                } else {
                                    ampm = " AM";
                                }
                                time=String.format("%02d:%02d", hourOfDay, minute);
                                chooseTime.setText(time + ampm);
                            }
                        }, currentHour, currentMinute, true);
                    }
                    timePickerDialog.show();
                }
                return false;
            }
        });
        //Date dialog
        chooseDate=findViewById(R.id.date_suggest);
        chooseDate.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH);
                    int year = calendar.get(Calendar.YEAR);
                    datePickerDialog = new DatePickerDialog(ShareActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int mYear, int mMonth, int mday) {
                            date=mday + "/" + (mMonth + 1) + "/" + mYear;
                            chooseDate.setText(date);
                        }
                    }, day, month, year);
                    datePickerDialog.show();

                }
                return false;
            }
        });



        btnSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//json create
                final JSONObject obj = new JSONObject();
                try {
                    obj.put("origin", origin_ac);
                    obj.put("dest", dest_ac);
                    obj.put("time", time);
                    obj.put("driver", App.getUID());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.i(TAG, "onClick: " + obj.toString());
                Log.i(TAG, "onClick: " + App.getToken());
                Log.i(TAG, "onClick: " + "eyJhbGciOiJSUzI1NiIsImtpZCI6IjU0OGYzZjk4N2IxNzMxOWZlZDhjZDc2ODNmNTIyNWEyOTY0YzY5OWQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vb3RvcHJvamVjdC0xNTUxMTAzNzkyNTEzIiwiYXVkIjoib3RvcHJvamVjdC0xNTUxMTAzNzkyNTEzIiwiYXV0aF90aW1lIjoxNTYwMTIyMjYwLCJ1c2VyX2lkIjoib3BFQzhGUXQzbWV6OVRSSTNPUjUzaGxuUUs1MyIsInN1YiI6Im9wRUM4RlF0M21lejlUUkkzT1I1M2hsblFLNTMiLCJpYXQiOjE1NjAxNzEzMDYsImV4cCI6MTU2MDE3NDkwNiwiZW1haWwiOiJnYWxvcmxhbm92QGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJnYWxvcmxhbm92QGdtYWlsLmNvbSJdfSwic2lnbl9pbl9wcm92aWRlciI6InBhc3N3b3JkIn19.i6uBwSN8zA-QzG-0g9YBI6IWOkrgWBrvDQqb7QxuHeJeeYS30GnqgYJhuFTXJQLqOmXxfIfOqTTfz8k91yef8_ThLlyQdaLvJFCx708c7Rxsh_bvBQM-fHD78cXjgep2v0C5BPoajZB8Eur2U5ewOFvI0ARqadiAGROKIn_GyeC3x8cS8pvkFzrF7vBB9ppmovhJ-2ZR8js2XQpJ7zNUwvwD7b8PiIt1P1tnxED7ioiDgRUYp_KrwCfVG4BffUkvFJNG1pB27wOZudbzWRuir6Sqt6ncmgy8cAGBQ0DL7agD0kp8H-_nBzJln-iJUAkMu3CsE3DQN3DuR1TI24QpXA");
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, App.url + "ride", obj, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.i(TAG, "onResponse: " + response.toString());


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i(error.getMessage(), "HttpPost() - onErrorResponse() ");
                            }
                        }) {
                    @Override
                    public byte[] getBody() {
                        String stringobj = obj.toString();
                        return stringobj.getBytes();
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json");
                        headers.put("Authorization", App.getToken());
                        return headers;
                    }

                };

                RequestQueue queue = Volley.newRequestQueue(App.getContext());
                queue.add(jsonObjectRequest);


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