package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ShowMap extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Log.d("location lat lng31",MapLocation.getInstance().getLng()+" "+MapLocation.getInstance().getLat());


    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng sydney = new LatLng(MainScreen.latitude, MainScreen.longitude);
        Log.d("location lat lng3",MapLocation.getInstance().getLng()+" "+MapLocation.getInstance().getLat());
        LatLng desty = new LatLng(MapLocation.getInstance().getLat(), MapLocation.getInstance().getLng());
        googleMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Src"));
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        googleMap.addMarker(new MarkerOptions()
                .position(desty)
                .title("Marker in Dest"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(desty));
    }
}