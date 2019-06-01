package com.example.oto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterStep3Activity extends AppCompatActivity {
    Button button_to_register_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiester_step3);

        button_to_register_4 = (Button) findViewById(R.id.buttonToRegisterStep4);
        button_to_register_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterStep4Activity(v);
            }
        });
    }

    public void openRegisterStep4Activity(View v) {
        Intent intent = new Intent(this, RegisterStep4Activity.class);
        startActivity(intent);
    }
}
