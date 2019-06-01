package com.example.oto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterStep2Activity extends AppCompatActivity {
    Button button_to_register_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step2);

        button_to_register_3 = (Button) findViewById(R.id.buttonToRegisterStep3);
        button_to_register_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterStep3Activity(v);
            }
        });
    }

    public void openRegisterStep3Activity(View v){
        Intent intent=new Intent(this, RegisterStep3Activity.class);
        startActivity(intent);
    }
}
