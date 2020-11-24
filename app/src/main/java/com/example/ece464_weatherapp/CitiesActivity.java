package com.example.ece464_weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.google.android.material.bottomappbar.BottomAppBar;

public class CitiesActivity extends AppCompatActivity {
    double lat,lng;

    String name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);


    }


    public void chooseCity(View v){
        RadioGroup rbGroup=findViewById(R.id.rbGroupCities);
       int id=rbGroup.getCheckedRadioButtonId();
       Bundle coords=new Bundle();


       if(id==R.id.rbLarnaca){
        lat=34.9003;
        lng=33.6232;
        name="Larnaca";
       }
        else if(id==R.id.rbNicosia){
           lat=35.1856;
           lng=33.3823;
           name="Nicosia";
        }
        else if(id==R.id.rbFamagusta){
           lat=35.1149;
           lng=33.9192;
           name="Famagusta";
        }
        else if(id==R.id.rbLimassol){
           lat=34.7071;
           lng=33.0226;
           name="Limassol";
        }
        else if(id==R.id.rbPaphos){
           lat=34.7720;
           lng=32.4297;
           name="Paphos";
        }
        Intent in=new Intent(this,MapsActivity.class);
        coords.putDouble("lat",lat);
        coords.putDouble("lng",lng);
        coords.putString("onoma",name);

        in.putExtras(coords);
        startActivity(in);

    }
}