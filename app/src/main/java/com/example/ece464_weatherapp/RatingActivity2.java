package com.example.ece464_weatherapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RatingActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating2);

        Intent in = getIntent();
    }

    public void processResponse(View v) {
        RadioGroup group = findViewById(R.id.rgGroup);

        int selection = group.getCheckedRadioButtonId();
        String message = null;

        if (selection == R.id.rbYes) {
            message = "Great to hear!";
        }
        else if (selection == R.id.rbNo) {
            message = "We are sorry to hear that!\nTell us below what you suggest to improve!";
        } else {
            message = "Please select one!";
        }
        Toast.makeText(RatingActivity2.this, message, Toast.LENGTH_LONG).show();
    }

    public void sendImprovements(View v) {
        CheckBox general = findViewById(R.id.cbGeneral);
        CheckBox weather = findViewById(R.id.cbWeather);
        CheckBox meteorological = findViewById(R.id.cbMeteor);
        CheckBox features = findViewById(R.id.cbFeatures);

        TextView tv1 = findViewById(R.id.tvMessage1);
        tv1.setText("Υour suggestion for improving WeatherApp is:\n");

        if(general.isChecked())

        {
            tv1.append("General Info\n");
        }
        if(weather.isChecked())

        {
            tv1.append("Weather Forecast\n");
        }
        if(meteorological.isChecked())

        {
            tv1.append("Meteorological Data\n");
        }
        if(features.isChecked())

        {
            tv1.append("Features\n");
        }
    }
    public void sendReturn(View v ){
        Intent in1 = new Intent(this, MainActivity.class);
        startActivity(in1);
    }

}