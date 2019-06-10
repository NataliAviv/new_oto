package com.example.oto;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    GoogleMap cmap;
    Button btnDirection;
    ArrayList markerPoints= new ArrayList();
    MarkerOptions place1,place2;
    Polyline currentPoli;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment fragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
        place1=new MarkerOptions().position(new LatLng(27.658143,85.3199503)).title("first location");
        place2=new MarkerOptions().position(new LatLng(27.667491,85.3208583)).title("second location");
        /*
        String url=getUrl(place1.getPosition(),place2.getPosition(),"driving");
        new FetchURL()*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        cmap=googleMap;
        //LatLng sydney=‏new LatLng(-34,151);

    }
}
