package com.example.oto;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class RegisterStep4Activity extends AppCompatActivity {
    Button button_to_register_5;

    EditText datePicked;
    DatePickerDialog.OnDateSetListener myDateSetListener;
    Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step4);

        button_to_register_5 = (Button) findViewById(R.id.buttonToRegisterStep5);
        button_to_register_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterStep5Activity(v);
            }
        });

        datePicked = findViewById(R.id.editTextExpiryDate);
        //datePicked.setText("06.05");
        datePicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                //int day = cal.get(Calender.DAY_OF_MONTH);
                int day = 01;
                DatePickerDialog dialog = new DatePickerDialog(RegisterStep4Activity.this, android.R.style.Theme_Holo_Dialog_MinWidth, myDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getDatePicker().findViewById(getResources().getIdentifier("day","id","android")).setVisibility(View.GONE);
                dialog.setTitle("Choose Expiry Date");
                dialog.show();
            }
        });

        myDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = month+"/"+year;
                datePicked.setText(date);
            }
        };
    }

    public void openRegisterStep5Activity(View v){
        Intent intent=new Intent(this, RegisterStep5Activity.class);
        startActivity(intent);
    }
}
