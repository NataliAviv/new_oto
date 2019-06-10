package com.example.oto;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchRideFragment extends AppCompatActivity {
    private final int EMPTY = 0;
private RecyclerView _RecyclerView;
private RecyclerView.Adapter _Adapter;
private RecyclerView.LayoutManager _LayoutManger;

//json vars
String objStr;
    JSONArray objArr;


private String Tag="searchRide";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backgroung_drives);



        objStr= getIntent().getStringExtra("response");
        try {
            objArr = new JSONArray(objStr);
        }
        catch(JSONException e){}

        Log.i(Tag, "->" + objStr);



        ArrayList<card_item> cardList = new ArrayList<>();
if(objArr.length()==EMPTY){
    getSupportFragmentManager().beginTransaction().replace(R.id.root_drives,new nomatches_fragment()).commit();

}
else {
    for (int i = 0; i < objArr.length(); i++) {
        try {
            JSONObject currentResult = objArr.getJSONObject(i);
            JSONObject driver = currentResult.getJSONObject("driver");


            cardList.add(new card_item(driver.getString("fullname"), currentResult.getString("origin"), currentResult.getString("dest"), currentResult.getString("time")));
        } catch (JSONException e) {
        }
    }

}

        _RecyclerView =findViewById(R.id.recyclerView);
        _RecyclerView.setHasFixedSize(true);
        _LayoutManger = new LinearLayoutManager(this);
        _Adapter = new cardAdapter(cardList);

        _RecyclerView.setLayoutManager(_LayoutManger);
        _RecyclerView.setAdapter(_Adapter);
    }

    }

