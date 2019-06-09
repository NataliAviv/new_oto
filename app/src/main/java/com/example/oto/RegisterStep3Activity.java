package com.example.oto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterStep3Activity extends AppCompatActivity {
    Button button_to_register_4;
    EditText carModelInput;
    EditText modelInput;
    EditText carColorInput;
    EditText licenseInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step3);

        carModelInput=findViewById(R.id.reg3_CarCompany);
        modelInput=findViewById(R.id.reg3_Model);
        carColorInput=findViewById(R.id.reg3_Color);
        licenseInput=findViewById(R.id.reg3_LicensePlate);

        button_to_register_4 = (Button) findViewById(R.id.buttonToRegisterStep4);
        button_to_register_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.setCarModel(carModelInput.getText().toString());
                App.setModel(modelInput.getText().toString());
                App.setCarColor(carColorInput.getText().toString());
                App.setLicense(licenseInput.getText().toString());
                openRegisterStep4Activity(v);
            }
        });
    }

    public void openRegisterStep4Activity(View v) {
        Intent intent = new Intent(this, RegisterStep4Activity.class);
        startActivity(intent);
    }
}
