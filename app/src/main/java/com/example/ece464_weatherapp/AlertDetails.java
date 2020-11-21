package com.example.ece464_weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class AlertDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_details);
        Intent in = getIntent();
        String value = in.getStringExtra(HomeFragment.alert);
        TextView tv = findViewById(R.id.tvText1);
        tv.setText(value);
        WebView browser = findViewById(R.id.wbWeb1);
        browser.setWebViewClient(new WebViewClient());
        browser.loadUrl("https://www.nhs.uk/live-well/healthy-body/heatwave-how-to-cope-in-hot-weather/");
    }

    public void goHome(View v){
        Intent in = new Intent(this,MainActivity.class);
        startActivity(in);
    }
}