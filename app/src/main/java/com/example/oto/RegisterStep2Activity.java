package com.example.oto;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegisterStep2Activity extends AppCompatActivity {
    Button button_to_register_3;
    EditText addressInput;
    EditText cityInput;
    EditText countryInput;
    EditText phoneInput;
    CheckBox checkBoxInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step2);

        addressInput = findViewById(R.id.reg2_address);
        cityInput = findViewById(R.id.reg2_city);
        countryInput = findViewById(R.id.reg2_country);
        phoneInput = findViewById(R.id.reg2_phone);
        checkBoxInput = findViewById(R.id.reg2_checkBoxCarOwner);

        button_to_register_3 = (Button) findViewById(R.id.buttonToRegisterStep3);
        button_to_register_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.setAddress(addressInput.getText().toString());
                App.setCity(cityInput.getText().toString());
                App.setCountry(countryInput.getText().toString());
                App.setPhone(phoneInput.getText().toString());

                //TODO: Ziv - Server Connection - Register Step 1 And 2 - Need To Do The Transformation Of Data To The Server.

                /*    Start communication to server    */

                JSONObject obj = new JSONObject();
                try{
                    obj.put("firstname", App.getFirstName());
                    obj.put("lastname",App.getLastName());
                    obj.put("phone",App.getPhone());
                    obj.put("address",App.getAddress());
                    obj.put("password",App.getPassword());
                    obj.put("email",App.getEmail());
                }catch(Exception e){
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, App.url + "user", obj, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject user = (JSONObject) response.get("user");
                                    Toast.makeText(RegisterStep2Activity.this, "got user", Toast.LENGTH_LONG).show();
                                    if(user.has("token")){
                                        String token = user.getString("token");
                                        App.setToken(token);
                                        Toast.makeText(RegisterStep2Activity.this, App.getToken(), Toast.LENGTH_LONG).show();
                                        nextActivity();
                                    } else {
                                        Toast.makeText(RegisterStep2Activity.this, "No Such User", Toast.LENGTH_LONG).show();
                                        registerAlertDialog();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                Toast.makeText(RegisterStep2Activity.this, "Login Server Failed", Toast.LENGTH_LONG).show();
                            }
                        });

                /*    Finished communication to server    */
                RequestQueue queue = Volley.newRequestQueue(App.getContext());
                queue.add(jsonObjectRequest);

                //openRegisterStep3Activity();
            }
        });
    }

    public void nextActivity() {
        if(checkBoxInput.isChecked()){
            openRegisterStep3Activity();
        } else {
            openRegisterStep4Activity();
        }
    }

    public void openRegisterStep3Activity() {
        Intent intent = new Intent(this, RegisterStep3Activity.class);
        startActivity(intent);
    }

    public void openRegisterStep4Activity() {
        Intent intent = new Intent(this, RegisterStep4Activity.class);
        startActivity(intent);
    }

    public void registerAlertDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Error!");
        alert.setMessage("Unable to register to the server,\n"+"Maybe the connection is bad\n"+"Try again");

        /*alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openRegisterStep1Activity();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });*/
        alert.show();
    }
}
