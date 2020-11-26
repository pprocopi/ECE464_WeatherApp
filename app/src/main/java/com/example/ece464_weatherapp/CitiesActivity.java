package com.example.ece464_weatherapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;


import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kwabenaberko.openweathermaplib.constants.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class CitiesActivity extends AppCompatActivity {
    double lat,lng;
    String name="";
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_cities);
        TextView tvCity=findViewById(R.id.tvChooseCity);
        final String[] temp = {""};
        tvCity.append(" Larnaca");
        OpenWeatherMapHelper helper = new OpenWeatherMapHelper(getString(R.string.OPEN_WEATHER_MAP_API_KEY));
        helper.setUnits(Units.METRIC);

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        Button btn = (Button) findViewById(R.id.btFindLocation);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                locationTrack = new LocationTrack(CitiesActivity.this);
                if (locationTrack.canGetLocation()) {

                    double longitude = locationTrack.getLongitude();
                    double latitude = locationTrack.getLatitude();
                    TextView tvflocation=findViewById(R.id.tvlocationdata);
                    tvflocation.setText(getCompleteAddressString(latitude,longitude ));


                    helper.getCurrentWeatherByGeoCoordinates(latitude,longitude, new CurrentWeatherCallback() {
                        @Override
                        public void onSuccess(CurrentWeather currentWeather) {

                            TextView tvftemp=findViewById(R.id.tvTemp);
                            String tempData = String.valueOf(currentWeather.getMain().getTemp())+" °C";
                            System.out.println(tempData.toString());

                            temp[0] =tempData;
                            System.out.println(temp[0]);
                            tvftemp.setText(temp[0]);
                        }
                        @Override
                        public void onFailure(Throwable throwable) {
                        }
                    });

                } else {
                    locationTrack.showSettingsAlert();
                }

            }
        });

    }


    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();

            Toast.makeText(getApplicationContext(), strReturnedAddress.toString(), Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(getApplicationContext(), "No Address returned!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Canont get Address!", Toast.LENGTH_SHORT).show();
        }
        return strAdd;
    }

    private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();

        for (Object perm : wanted) {
            if (!hasPermission((String) perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (Object perms : permissionsToRequest) {
                    if (!hasPermission((String) perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale((String) permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(CitiesActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTrack.stopListener();
    }

    public void rbLar(View v){
        TextView tvCity=findViewById(R.id.tvChooseCity);
        tvCity.setText("City:");
        tvCity.append(" Larnaca");
    }
    public void rbNic(View v){
        TextView tvCity=findViewById(R.id.tvChooseCity);
        tvCity.setText("City:");
        tvCity.append(" Nicosia");
    }
    public void rbLim(View v){
        TextView tvCity=findViewById(R.id.tvChooseCity);
        tvCity.setText("City:");
        tvCity.append(" Limassol");
    }
    public void rbPap(View v){
        TextView tvCity=findViewById(R.id.tvChooseCity);
        tvCity.setText("City:");
        tvCity.append(" Paphos");
    }
    public void rbFam(View v){
        TextView tvCity=findViewById(R.id.tvChooseCity);
        tvCity.setText("City:");
        tvCity.append(" Famagusta");
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