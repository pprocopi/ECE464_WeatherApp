package com.example.ece464_weatherapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class RatingActivity extends AppCompatActivity {

    Button button;
    RatingBar ratingStars;
    float myRating = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        Intent in1 = getIntent();

        button = findViewById(R.id.button1);
        ratingStars = findViewById(R.id.ratingBar);

        ratingStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                int rate = (int) rating;
                String message = null;
                myRating = ratingBar.getRating();

                switch (rate) {
                    case 0:
                        message = "Bad News!";
                        break;
                    case 1:
                        message = "Sorry to hear that!";
                        break;
                    case 2:
                        message = "You always accept suggestions!";
                        break;
                    case 3:
                        message = "Good enough!";
                        break;
                    case 4:
                        message = "Great! Thank you!";
                        break;
                    case 5:
                        message = "Awesome! You are the best!";
                        break;
                }
                Toast.makeText(RatingActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RatingActivity.this, "Your rating is: " + myRating + " out of 5", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void sendFeedback(View v) {
        Intent in = new Intent(this, RatingActivity2.class);
        startActivity(in);
    }
}

