package com.example.oto;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Arrays;
import java.util.Calendar;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.api.Places;

public class FindActivity extends AppCompatActivity {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_find);

        /*start autocomplete */
        // Initialize Places.
        Places.initialize(getApplicationContext(), "AIzaSyCp5mD7PtTX4ikEnIhSBXPQwp45ze4qLAE");
        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        //add to autocomplete
        AutocompleteSupportFragment autocompleteFragment2 = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.auto_complete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        autocompleteFragment2.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                //txtView.setText(place.getName()+","+place.getId());
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener(){ @Override
        public void onPlaceSelected(Place place) {
            // TODO: Get info about the selected place.
            //txtView.setText(place.getName()+","+place.getId());
            Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
        }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }});

        /*finish autocomplete */
        ShowTheDate=(TextView)findViewById(R.id.date_show);
        mDatebtn=(Button)findViewById(R.id.date);
        mDatebtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                calendar=Calendar.getInstance();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);

                datePickerDialog=new DatePickerDialog(FindActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mday) {
                        ShowTheDate.setText(mday+"/"+(mMonth+1)+"/"+ mYear);
                    }
                },day,month,year);
                datePickerDialog.show();
            }
        });

        //TIMER
        chooseTime=findViewById(R.id.time);
        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c2=Calendar.getInstance();
                currentHour=c2.get(Calendar.HOUR_OF_DAY);
                currentMinute=c2.get(Calendar.MINUTE);
                timePickerDialog=new TimePickerDialog(FindActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay>=12){
                            ampm="PM";}
                        else {ampm="AM";
                        }
                        chooseTime.setText(String.format("%02d:%02d",hourOfDay,minute)+ampm);
                        }
                    },currentHour,currentMinute,false);
                timePickerDialog.show();
                }

        });
    }



}