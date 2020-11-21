package com.example.ece464_weatherapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ece464_weatherapp.R;
import com.kwabenaberko.openweathermaplib.constants.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;


public class HomeFragment extends Fragment {
    TextView country, city, lat, lng, humid, sunrise, sunset, pressure, wind, temp;
    ImageButton search;
    ImageView icon;
    EditText searchCity;
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss"); //dd-MM-yyyy
    Timestamp ts;
    String file = "currentweather.txt";
    public static  String alert = "";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //TextView textView = root.findViewById(R.id.text_home);
        OpenWeatherMapHelper helper = new OpenWeatherMapHelper(getString(R.string.OPEN_WEATHER_MAP_API_KEY));
        helper.setUnits(Units.METRIC);

        country = root.findViewById(R.id.tvCountry);
        city = root.findViewById(R.id.tvCity);
        lat = root.findViewById(R.id.tvLatitude);
        lng = root.findViewById(R.id.tvLongitude);
        humid = root.findViewById(R.id.tvHumidity);
        sunrise = root.findViewById(R.id.tvSunrise);
        sunset = root.findViewById(R.id.tvSunset);
        pressure = root.findViewById(R.id.tvPressure);
        wind = root.findViewById(R.id.tvWind);
        temp = root.findViewById(R.id.tvTemp);

        icon = (ImageView) root.findViewById(R.id.ivIcon);

        searchCity = root.findViewById(R.id.etCity);

        search = root.findViewById(R.id.ibSearch);

        try{
            FileInputStream fin = getActivity().openFileInput(file);
            DataInputStream din = new DataInputStream(fin);
            InputStreamReader isr = new InputStreamReader(din);
            BufferedReader br = new BufferedReader(isr);

            int i = 0;
            String lines[] = new String[11];
            String strLine;

            while((strLine = br.readLine()) != null){
                lines[i] = strLine;
                i++;
            }
            country.setText(lines[0]);
            city.setText(lines[1]);
            lat.setText(lines[2]);
            lng.setText(lines[3]);
            humid.setText(lines[4]);
            sunrise.setText(lines[5]);
            sunset.setText(lines[6]);
            pressure.setText(lines[7]);
            wind.setText(lines[8]);
            temp.setText(lines[9]);
            Picasso.get().load(lines[10]).into(icon);


            fin.close();
            //Toast.makeText(getActivity().getApplicationContext(), "All Works well!!", Toast.LENGTH_LONG).show();
        }
        catch(Exception ex){
            ex.printStackTrace();
            //Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        }




        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String citySearch = searchCity.getText().toString();
                helper.getCurrentWeatherByCityName(citySearch, new CurrentWeatherCallback() {
                    @Override
                    public void onSuccess(CurrentWeather currentWeather) {
                        ts = new Timestamp(currentWeather.getSys().getSunrise());
                        System.out.println(ts.toString());

                        Date datesnr = new Date(currentWeather.getSys().getSunrise() * 1000);
                        Date datesunset = new Date(currentWeather.getSys().getSunset() * 1000);

                        String countryData = currentWeather.getSys().getCountry();
                        String cityData = currentWeather.getName();
                        String latData = String.valueOf(currentWeather.getCoord().getLat());
                        String lngData = String.valueOf(currentWeather.getCoord().getLon());
                        String humidData = String.valueOf(currentWeather.getMain().getHumidity())+"%";
                        String sunriseData = formatter.format(datesnr);
                        String sunsetData = formatter.format(datesunset);
                        String pressureData = String.valueOf(currentWeather.getMain().getPressure());
                        String windData = String.valueOf(currentWeather.getWind().getSpeed());
                        String tempData = String.valueOf(currentWeather.getMain().getTemp())+" oC";


                        country.setText(countryData);
                        city.setText(cityData);
                        lat.setText(latData);
                        lng.setText(lngData);
                        humid.setText(humidData);
                        sunrise.setText(sunriseData);
                        sunset.setText(sunsetData);
                        pressure.setText(pressureData);
                        wind.setText(windData);
                        temp.setText(tempData);

                        if (currentWeather.getMain().getTemp() >= 10.0) {
                            Intent in = new Intent(getActivity(),AlertDetails.class);
                            in.putExtra(alert,"The weather in " + cityData +", " + countryData + " is dangerous hot!\nTemperature is "+ tempData + "\nPlease avoid get exposed to this weather.\nRead this web to learn how to cope hot weather.");
                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),0,in,0);
                            NotificationChannel channel = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                channel = new NotificationChannel(
                                        "1",
                                        "channel1",
                                        NotificationManager.IMPORTANCE_DEFAULT);

                                //create the notification manager
                                NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
                                manager.createNotificationChannel(channel);

                                //create the notification
                                NotificationCompat.Builder notification = new NotificationCompat.Builder(getActivity(), "1")
                                        .setSmallIcon(android.R.drawable.ic_dialog_alert)
                                        .setContentTitle("Hot Weather Alert")
                                        .setContentText("The weather in " + cityData + ", " + countryData + " is very high!")
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true);

                                NotificationManagerCompat notifyAdmin = NotificationManagerCompat.from(getActivity());
                                notifyAdmin.notify(1, notification.build());
                            }
                        }

                        String url = "https://openweathermap.org/img/wn/"+currentWeather.getWeather().get(0).getIcon()+"@2x.png";
                        System.out.println(url);
                        System.out.println(icon.getDrawable());
                        Picasso.get().load(url).into(icon);
                        System.out.println(icon.getDrawable());

                        try{
                            FileOutputStream fout = getActivity().openFileOutput(file,0);
                            fout.write((countryData + "\n").getBytes());
                            fout.write((cityData + "\n").getBytes());
                            fout.write((latData + "\n").getBytes());
                            fout.write((lngData + "\n").getBytes());
                            fout.write((humidData + "\n").getBytes());
                            fout.write((sunriseData + "\n").getBytes());
                            fout.write((sunsetData + "\n").getBytes());
                            fout.write((pressureData + "\n").getBytes());
                            fout.write((windData + "\n").getBytes());
                            fout.write((tempData + "\n").getBytes());
                            fout.write((url + "\n").getBytes());
                            fout.close();
                            //Toast.makeText(getActivity().getApplicationContext(), "File Saved", Toast.LENGTH_LONG).show();
                        }
                        catch(Exception ex){
                            ex.printStackTrace();
                            //Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                });
            }
        });
        return root;
    }
}