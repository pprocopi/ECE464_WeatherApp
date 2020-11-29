package com.example.ece464_weatherapp;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kwabenaberko.openweathermaplib.constants.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;

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
        final Timestamp[] ts = new Timestamp[1];
        final String[] temp = {""};

        if(name.equals("My Location")){
            OpenWeatherMapHelper helper = new OpenWeatherMapHelper(getString(R.string.OPEN_WEATHER_MAP_API_KEY));
            helper.setUnits(Units.METRIC);
            helper.getCurrentWeatherByGeoCoordinates(Lat,Lng, new CurrentWeatherCallback() {
                @Override
                public void onSuccess(CurrentWeather currentWeather) {


                    String tempData = String.valueOf(currentWeather.getMain().getTempMax()+" °C");
                    System.out.println(tempData.toString());

                    temp[0] =tempData;
                    System.out.println(temp[0]);

                    ImageView icon= findViewById(R.id.ivIcon);
                    TextView tvtemp=findViewById(R.id.tvTemp);
                    tvtemp.setText(temp[0]);

                    LatLng MyLocation = new LatLng(Lat,Lng);
                    String url = "https://openweathermap.org/img/wn/"+currentWeather.getWeather().get(0).getIcon()+"@2x.png";
                    Picasso.get().load(url).into(icon);
                    mMap.addMarker(new MarkerOptions().position(MyLocation).title("Temperature in "+name+" :"+temp[0])).showInfoWindow();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MyLocation,12),5000,null);
                }

                @Override
                public void onFailure(Throwable throwable) {

                }
            });
        }

        else {
            OpenWeatherMapHelper helper = new OpenWeatherMapHelper(getString(R.string.OPEN_WEATHER_MAP_API_KEY));
            helper.setUnits(Units.METRIC);

            helper.getCurrentWeatherByCityName(name, new CurrentWeatherCallback() {
                @Override
                public void onSuccess(CurrentWeather currentWeather) {
                    ts[0] = new Timestamp(currentWeather.getSys().getSunrise());
                    System.out.println(ts[0].toString());

                    String tempData = String.valueOf(currentWeather.getMain().getTemp()) + " °C";
                    System.out.println(tempData.toString());

                    temp[0] = tempData;
                    System.out.println(temp[0]);

                    if (name.equals("Larnaca")) {
                        ImageView icon = findViewById(R.id.ivIcon);
                        TextView tvtemp = findViewById(R.id.tvTemp);
                        tvtemp.setText(temp[0]);

                        LatLng Larnaca = new LatLng(Lat, Lng);
                        String url = "https://openweathermap.org/img/wn/" + currentWeather.getWeather().get(0).getIcon() + "@2x.png";
                        Picasso.get().load(url).into(icon);
                        mMap.addMarker(new MarkerOptions().position(Larnaca).title("Temperature in " + name + " :" + temp[0])).showInfoWindow();
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Larnaca, 12), 5000, null);
                    } else if (name.equals("Nicosia")) {
                        ImageView icon = findViewById(R.id.ivIcon);
                        TextView tvtemp = findViewById(R.id.tvTemp);
                        tvtemp.setText(temp[0]);

                        LatLng Nicosia = new LatLng(Lat, Lng);
                        String url = "https://openweathermap.org/img/wn/" + currentWeather.getWeather().get(0).getIcon() + "@2x.png";
                        Picasso.get().load(url).into(icon);
                        mMap.addMarker(new MarkerOptions().position(Nicosia).title("Temperature in " + name + " :" + temp[0])).showInfoWindow();
                        ;
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Nicosia, 12), 5000, null);

                    } else if (name.equals("Limassol")) {
                        ImageView icon = findViewById(R.id.ivIcon);
                        TextView tvtemp = findViewById(R.id.tvTemp);
                        tvtemp.setText(temp[0]);

                        String url = "https://openweathermap.org/img/wn/" + currentWeather.getWeather().get(0).getIcon() + "@2x.png";
                        Picasso.get().load(url).into(icon);
                        LatLng Limassol = new LatLng(Lat, Lng);
                        mMap.addMarker(new MarkerOptions().position(Limassol).title("Temperature in " + name + " :" + temp[0])).showInfoWindow();
                        ;
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Limassol, 12), 5000, null);

                    } else if (name.equals("Paphos")) {
                        ImageView icon = findViewById(R.id.ivIcon);
                        TextView tvtemp = findViewById(R.id.tvTemp);
                        tvtemp.setText(temp[0]);

                        String url = "https://openweathermap.org/img/wn/" + currentWeather.getWeather().get(0).getIcon() + "@2x.png";
                        Picasso.get().load(url).into(icon);
                        LatLng Paphos = new LatLng(Lat, Lng);
                        mMap.addMarker(new MarkerOptions().position(Paphos).title("Temperature in " + name + " :" + temp[0])).showInfoWindow();
                        ;
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Paphos, 12), 5000, null);

                    } else if (name.equals("Famagusta")) {
                        ImageView icon = findViewById(R.id.ivIcon);
                        TextView tvtemp = findViewById(R.id.tvTemp);
                        tvtemp.setText(temp[0]);

                        String url = "https://openweathermap.org/img/wn/" + currentWeather.getWeather().get(0).getIcon() + "@2x.png";
                        Picasso.get().load(url).into(icon);
                        LatLng Famagusta = new LatLng(Lat, Lng);
                        mMap.addMarker(new MarkerOptions().position(Famagusta).title("Temperature in " + name + " :" + temp[0])).showInfoWindow();
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Famagusta, 12), 5000, null);

                    }
                }

                @Override
                public void onFailure(Throwable throwable) {

                }
            });


        }



    }
}