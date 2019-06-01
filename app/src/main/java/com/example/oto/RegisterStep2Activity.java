package com.example.oto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterStep2Activity extends AppCompatActivity {
    Button button_to_register_3;
    EditText addressInput;
    EditText cityInput;
    EditText countryInput;
    EditText phoneInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step2);

        addressInput = findViewById(R.id.reg2_address);
        cityInput = findViewById(R.id.reg2_city);
        countryInput = findViewById(R.id.reg2_country);
        phoneInput = findViewById(R.id.reg2_phone);

        button_to_register_3 = (Button) findViewById(R.id.buttonToRegisterStep3);
        button_to_register_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.address = addressInput.getText().toString();
                App.city = cityInput.getText().toString();
                App.country = countryInput.getText().toString();
                App.phone = phoneInput.getText().toString();
                openRegisterStep3Activity(v);
            }
        });
    }

    public void openRegisterStep3Activity(View v) {
        Intent intent = new Intent(this, RegisterStep3Activity.class);
        startActivity(intent);
    }
}
