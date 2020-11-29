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
        Bundle bund = in.getExtras();
        //String value = bund.getString("alert");
        String value = in.getStringExtra(HomeFragment.alert);
        //String cond = in.getStringExtra(HomeFragment.cond);
        TextView tv = findViewById(R.id.tvText1);
        tv.setText(value);
        WebView browser = findViewById(R.id.wbWeb1);
        browser.setWebViewClient(new WebViewClient());
        System.out.println(value);
        if (value.contains("hot")){
            browser.loadUrl("https://www.nhs.uk/live-well/healthy-body/heatwave-how-to-cope-in-hot-weather/");
        }else if (value.contains("cold")){
            browser.loadUrl("https://www.nhs.uk/live-well/healthy-body/keep-warm-keep-well/");
        }
    }

    public void goHome(View v){
        Intent in = new Intent(this,MainActivity.class);
        startActivity(in);
    }
}