package com.example.ece464_weatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.kwabenaberko.openweathermaplib.constants.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.ThreeHourForecastCallback;
import com.kwabenaberko.openweathermaplib.models.threehourforecast.ThreeHourForecast;
import com.kwabenaberko.openweathermaplib.models.threehourforecast.ThreeHourWeather;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Integer.parseInt;


public class ChartsFragment extends Fragment {

    String cityName = "London";
    String file = "currentweather.txt";
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yy-mm-dd");
    String date;
    String time;
    String timeStamp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_charts, container, false);

        TextView tvMaxTemp = root.findViewById(R.id.tvMaxTemp);
        TextView tvMaxRain = root.findViewById(R.id.tvMaxRain);
        TextView tvMaxSnow = root.findViewById(R.id.tvMaxSnow);

        try
        {
            FileInputStream fin = getActivity().openFileInput(file);
            DataInputStream din = new DataInputStream(fin);
            InputStreamReader isr = new InputStreamReader(din);
            BufferedReader br = new BufferedReader(isr);

            String lines[] = new String[11];
            int i = 0;
            String strLine;

            while((strLine = br.readLine()) != null){
                lines[i] = strLine;
                i++;
            }
            if(lines[1] != null){
                cityName = lines[1];
            }

            fin.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        System.out.println("City Name:" + cityName);



        OpenWeatherMapHelper helper = new OpenWeatherMapHelper(getString(R.string.OPEN_WEATHER_MAP_API_KEY));
        helper.setUnits(Units.METRIC);
        helper.getThreeHourForecastByCityName(cityName, new ThreeHourForecastCallback() {
            @Override
            public void onSuccess(ThreeHourForecast threeHourForecast) {
                List<ThreeHourWeather> weatherList = threeHourForecast.getList();

                int count = threeHourForecast.getCnt();
                List<Double> RainList = new ArrayList<Double>(Collections.nCopies(count, 0.0));
                List<Double> TempList = new ArrayList<Double>(Collections.nCopies(count, 0.0));
                List<Double> SnowList = new ArrayList<Double>(Collections.nCopies(count, 0.0));
                List<String> TimeList =  new ArrayList<String>();

                /*System.out.println("Count:" + count);
                System.out.println("ListTest:" + RainList);
                System.out.println("ListTestSize:" + RainList.size());*/

                for(int i = 0;i < count;i++)
                {
                    {
                        System.out.println("Counter:" + i + "Temp:" +
                                weatherList.get(i).getMain().getTemp());
                        TempList.set(i, weatherList.get(i).getMain().getTemp());
                    }

                    if(weatherList.get(i).getRain() != null)
                    {
                        System.out.println("Counter:" + i + "Rain:" +
                                weatherList.get(i).getRain().get3h());
                        RainList.set(i, weatherList.get(i).getRain().get3h());
                    }

                    if(weatherList.get(i).getSnow() != null)
                    {
                        System.out.println("Counter:" + i + "Temp:" +
                                weatherList.get(i).getSnow().get3h());
                        SnowList.set(i, weatherList.get(i).getSnow().get3h());
                    }

                    {
                        System.out.println("Counter:" + i + "Temp:" +
                                weatherList.get(i).getDtTxt()); //getDtTxt()

                        System.out.println("giannis" + date + " " + time);
                        timeStamp = weatherList.get(i).getDtTxt();
                        timeStamp = timeStamp.replace(" ", "\n");
                        TimeList.add(timeStamp);
                    }
                }

                System.out.println("ListTest:" + RainList);
                System.out.println("ListTest:" + SnowList);
                System.out.println("ListTest:" + TempList);
                System.out.println("ListTest:" + TimeList);

                BarChart tempChart = (BarChart) root.findViewById(R.id.tempChart);
                List<BarEntry> tempEntry = new ArrayList<BarEntry>();

                for(int i = 0;i < count;i++)
                {
                    float xPos = (float) (i * 1.0);
                    tempEntry.add(new BarEntry((float) xPos, TempList.get(i).floatValue()));
                }

                BarDataSet tempSet = new BarDataSet(tempEntry, "TemperatureDataSet");
                BarData tempData = new BarData(tempSet);
                //data.setBarWidth(1.3f); // set custom bar width
                tempChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(TimeList));
                tempChart.getXAxis().setLabelRotationAngle(-90);
                tempChart.setDrawGridBackground(false);
                tempChart.setNoDataText("There is no data to present at the time!");
                tempChart.setData(tempData);
                tempChart.setVisibleXRangeMaximum(8);
                tempChart.invalidate(); // refresh

                BarChart rainChart = (BarChart) root.findViewById(R.id.rainChart);
                List<BarEntry> rainEntry = new ArrayList<BarEntry>();

                for(int i = 0;i < count;i++)
                {
                    float xPos = (float) (i * 1.0);
                    rainEntry.add(new BarEntry((float) xPos, RainList.get(i).floatValue()));
                }

                BarDataSet rainSet = new BarDataSet(rainEntry, "RainDataSet");
                BarData rainData = new BarData(rainSet);
                //data.setBarWidth(1.3f); // set custom bar width
                rainChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(TimeList));
                rainChart.getXAxis().setLabelRotationAngle(-90);
                rainChart.setDrawGridBackground(false);
                rainChart.setNoDataText("There is no data to present at the time!");
                rainChart.setData(rainData);
                rainChart.setVisibleXRangeMaximum(8);
                rainChart.invalidate(); // refresh

                BarChart snowChart = (BarChart) root.findViewById(R.id.snowChart);
                List<BarEntry> snowEntry = new ArrayList<BarEntry>();

                for(int i = 0;i < count;i++)
                {
                    float xPos = (float) (i * 1.0);
                    snowEntry.add(new BarEntry((float) xPos, SnowList.get(i).floatValue()));
                }

                BarDataSet snowSet = new BarDataSet(snowEntry, "SnowDataSet");
                BarData snowData = new BarData(snowSet);
                //data.setBarWidth(1.3f); // set custom bar width
                snowChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(TimeList));
                snowChart.getXAxis().setLabelRotationAngle(-90);
                snowChart.setDrawGridBackground(false);
                snowChart.setNoDataText("There is no data to present at the time!");
                snowChart.setData(snowData);
                snowChart.setVisibleXRangeMaximum(8);
                snowChart.invalidate(); // refresh

                tvMaxTemp.setText("Max. Temperature: " + Collections.max(TempList) + "C");
                tvMaxRain.setText("Max. Rainfall: " + Collections.max(RainList) + "mm");
                tvMaxSnow.setText("Max. Snowfall: " + Collections.max(SnowList) + "mm");
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(getActivity().getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}