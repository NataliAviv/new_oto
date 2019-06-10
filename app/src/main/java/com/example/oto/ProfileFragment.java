package com.example.oto;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    TextView profileNameTextView;
    TextView profilePhoneTextView;
    TextView profileEmailTextView;
    TextView profileAddressTextView;

    EditText profileNameEditText;
    EditText profilePhoneEditText;
    EditText profileEmailEditText;
    EditText profileAddressEditText;

    //boolean editDisable = true;

    Switch editSwitch;

    String tokenIDFirebase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profileNameTextView = view.findViewById(R.id.profile_name);
        final String fullName = App.getFirstName() + " " + App.getLastName();
        profileNameTextView.setText(fullName);
        profilePhoneTextView = view.findViewById(R.id.profile_phone);
        profilePhoneTextView.setText(App.getPhone());
        profileEmailTextView = view.findViewById(R.id.profile_email);
        profileEmailTextView.setText(App.getEmail());
        profileAddressTextView = view.findViewById(R.id.profile_address);
        profileAddressTextView.setText(App.getAddress());

        editSwitch = view.findViewById(R.id.edit_switch);

        profileNameEditText = view.findViewById(R.id.profile_name_edit_text);
        profilePhoneEditText = view.findViewById(R.id.profile_phone_edit_text);
        profileEmailEditText = view.findViewById(R.id.profile_email_edit_text);
        profileAddressEditText = view.findViewById(R.id.profile_address_edit_text);

        editSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    profileNameTextView.setVisibility(View.INVISIBLE);
                    profileNameEditText.setVisibility(View.VISIBLE);

                    profilePhoneTextView.setVisibility(View.INVISIBLE);
                    profilePhoneEditText.setVisibility(View.VISIBLE);

                    profileEmailTextView.setVisibility(View.INVISIBLE);
                    profileEmailEditText.setVisibility(View.VISIBLE);

                    profileAddressTextView.setVisibility(View.INVISIBLE);
                    profileAddressEditText.setVisibility(View.VISIBLE);
                } else {
                    final String newName = profileNameEditText.getText().toString();
                    final String newPhone = profilePhoneEditText.getText().toString();
                    final String newEmail = profileEmailEditText.getText().toString();
                    final String newAddress = profileAddressEditText.getText().toString();

                    profileNameTextView.setText(newName);
                    profileNameTextView.setVisibility(View.VISIBLE);
                    profileNameEditText.setVisibility(View.INVISIBLE);

                    profilePhoneTextView.setText(newPhone);
                    profilePhoneTextView.setVisibility(View.VISIBLE);
                    profilePhoneEditText.setVisibility(View.INVISIBLE);

                    profileEmailTextView.setText(newEmail);
                    profileEmailTextView.setVisibility(View.VISIBLE);
                    profileEmailEditText.setVisibility(View.INVISIBLE);

                    profileAddressTextView.setText(newAddress);
                    profileAddressTextView.setVisibility(View.VISIBLE);
                    profileAddressEditText.setVisibility(View.INVISIBLE);

                    /*     Update start     */
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        // User is signed in
                        //Toast.makeText(ShareActivity.this, user.getUid(), Toast.LENGTH_SHORT).show();
                        user.getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {

                            @Override
                            public void onComplete(@NonNull Task<GetTokenResult> task) {
                                if (task.isSuccessful()) {
                                    String uid = App.getUID();
                                    Toast.makeText(getActivity(), uid, Toast.LENGTH_SHORT).show();
                                    tokenIDFirebase = task.getResult().getToken();  //ID token from Firebase

                                    final JSONObject obj = new JSONObject();
                                    try {
                                        obj.put("uid", App.getUID());
                                        obj.put("email", newEmail);
                                        obj.put("fullname", newName);      //Puting the new info in JSON object
                                        obj.put("phone", newPhone);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                            (Request.Method.POST, App.url + "user/update", obj, new Response.Listener<JSONObject>() {

                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    Toast.makeText(getActivity(), "UID: " + App.getUID(), Toast.LENGTH_SHORT).show();
                                                    try {
                                                        if (response.has("user")) {
                                                            Toast.makeText(getActivity(), "user updated successfully", Toast.LENGTH_LONG).show();
                                                        } else {
                                                            Toast.makeText(getActivity(), "no user update", Toast.LENGTH_LONG).show();
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    error.printStackTrace();
                                                    Toast.makeText(getActivity(), "Login Server Failed", Toast.LENGTH_LONG).show();
                                                    //Log.i(error.getMessage(), "HttpPost() - onErrorResponse() ");

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
                                } else {
                                    Toast.makeText(getActivity(), "Failed To Get Token From User", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        // No user is signed in
                    }
                    /*     Update ended      */
                }
            }
        });


        return view;
    }
}
