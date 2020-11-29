package com.example.ece464_weatherapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText pass = (EditText) findViewById(R.id.inputPassword);
        final EditText cpass = (EditText) findViewById(R.id.inputConfirmPassword);
        final Button testButton = (Button) findViewById(R.id.btnRegister);
        final Button button1 = (Button) findViewById(R.id.button);
        final EditText username = (EditText) findViewById(R.id.inputUsername);
        final EditText email = (EditText) findViewById(R.id.inputEmail);


        testButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        if (username.getText().length() == 0) {
                            Toast.makeText(getApplicationContext(), "Invalid Username",
                                    Toast.LENGTH_LONG).show();
                            button1.setEnabled(false);
                        } else if (email.getText().length() == 0) {
                            Toast.makeText(getApplicationContext(), "Invalid Email",
                                    Toast.LENGTH_LONG).show();
                            button1.setEnabled(false);
                        } else if (pass.getText().length() == 0) {
                            Toast.makeText(getApplicationContext(), "Invalid Password",
                                    Toast.LENGTH_LONG).show();
                            button1.setEnabled(false);
                        } else if (cpass.getText().length() == 0) {
                            Toast.makeText(getApplicationContext(), "Invalid Confirm Password",
                                    Toast.LENGTH_LONG).show();
                            button1.setEnabled(false);
                        } else if (!pass.getText().toString().equals(cpass.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Password does not match!",
                                    Toast.LENGTH_LONG).show();
                            button1.setEnabled(false);
                        } else {
                            Toast.makeText(getApplicationContext(), "Password match!",
                                    Toast.LENGTH_LONG).show();
                            button1.setEnabled(true);
                        }

                    }
                });

    }
    public void sendMain (View v){
        Intent in = new Intent(this, RatingActivity.class);
        startActivity(in);
    }


}