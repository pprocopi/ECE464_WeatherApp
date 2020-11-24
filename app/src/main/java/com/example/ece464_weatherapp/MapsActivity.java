package com.example.ece464_weatherapp;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double Lat;
    double Lng;
    String name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Intent in=getIntent();
        Bundle info=in.getExtras();
        Lat=info.getDouble("lat");
        Lng=info.getDouble("lng");
        name=info.getString("onoma");


        if(name.equals("Larnaca")){
            LatLng Larnaca = new LatLng(Lat,Lng);
            mMap.addMarker(new MarkerOptions().position(Larnaca).title(name+" Lat: "+Lat+" Lng:"+Lng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Larnaca,12),5000,null);
        }
        else if(name.equals("Nicosia")){
            LatLng Nicosia = new LatLng(Lat,Lng);
            mMap.addMarker(new MarkerOptions().position(Nicosia).title(name+" Lat: "+Lat+" Lng:"+Lng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Nicosia,12),5000,null);

        }
        else if(name.equals("Limassol")){
            LatLng Limassol = new LatLng(Lat,Lng);
            mMap.addMarker(new MarkerOptions().position(Limassol).title(name+" Lat: "+Lat+" Lng:"+Lng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Limassol,12),5000,null);

        }
        else if(name.equals("Paphos")){
            LatLng Paphos = new LatLng(Lat,Lng);
            mMap.addMarker(new MarkerOptions().position(Paphos).title(name+" Lat: "+Lat+" Lng:"+Lng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Paphos,12),5000,null);

        }
        else if(name.equals("Famagusta")){
            LatLng Famagusta = new LatLng(Lat,Lng);
            mMap.addMarker(new MarkerOptions().position(Famagusta).title(name+" Lat: "+Lat+" Lng:"+Lng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Famagusta,12),5000,null);

        }

        // Add a marker in Sydney and move the camera

    }
}