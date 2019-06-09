package com.example.oto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterStep4Activity extends AppCompatActivity {
    Button btn_to_main_page;
    CheckBox checkBox_terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step4);
        checkBox_terms = findViewById(R.id.reg4_check_box_terms);
        btn_to_main_page = findViewById(R.id.reg4_btn_to_main_page);
        btn_to_main_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject obj = new JSONObject();
                            try {
                                obj.put("uid", App.getUID());
                                obj.put("email", App.getEmail());
                                obj.put("password", App.getPassword());
                                obj.put("fullname", App.getFirstName()+" "+App.getLastName());
                                obj.put("birthday", App.getBirthday());
                                obj.put("gender", App.getGender());
                                obj.put("terms", true);
                                obj.put("brand", App.getCarModel());
                                obj.put("model", App.getModel());
                                obj.put("color", App.getCarColor());
                                obj.put("license", App.getLicense());
                                obj.put("phone", App.getPhone());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                    (Request.Method.POST, App.url + "user", obj, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                String requestStatus = response.toString();


                                                    //Toast.makeText(LoginActivity.this, App.getToken(), Toast.LENGTH_LONG).show();
                                                    /*App.setFirstName(user.get("firstname").toString());
                                                    App.setLastName(user.get("lastname").toString());
                                                    App.setPhone(user.get("phone").toString());
                                                    App.setAddress(user.get("address").toString());
                                                    App.setEmail(user.get("email").toString());*/
                                                    openMainPageActivity();

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                            Toast.makeText(RegisterStep4Activity.this, "Login Server Failed", Toast.LENGTH_LONG).show();
                                        }
                                    });
                            RequestQueue queue = Volley.newRequestQueue(App.getContext());
                            queue.add(jsonObjectRequest);

                openMainPageActivity();
            }
        });
    }

    public void openMainPageActivity() {
        if (checkBox_terms.isChecked()) {
            Toast.makeText(RegisterStep4Activity.this, "Checked!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainPageActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(RegisterStep4Activity.this, "Not Checked!", Toast.LENGTH_LONG).show();
            return;
        }
    }





    /*
    JSONObject obj = new JSONObject();

                try{
                    Toast.makeText(RegisterStep2Activity.this, App.getUID(), Toast.LENGTH_LONG).show();
                    obj.put("uid", App.getUID());
                    //obj.put("firstname", App.getFirstName());
                    //obj.put("lastname",App.getLastName());
                    obj.put("fullname",App.getFirstName()+" "+App.getLastName());
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
                                    Toast.makeText(RegisterStep2Activity.this, "got user", Toast.LENGTH_LONG).show();
                                    if(response.toString().compareTo("OK") == 0){
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

                RequestQueue queue = Volley.newRequestQueue(App.getContext());
                queue.add(jsonObjectRequest);
     */


}
