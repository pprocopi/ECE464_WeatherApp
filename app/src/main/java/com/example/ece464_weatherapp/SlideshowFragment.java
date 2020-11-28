package com.example.ece464_weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ece464_weatherapp.R;


public class SlideshowFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        Button bLoc = root.findViewById(R.id.btLocation);
        bLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inLoc = new Intent(getActivity(), CitiesActivity.class);
                startActivity(inLoc);
            }
        });

        Button bRate = root.findViewById(R.id.btRate);
        bLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inLoc = new Intent(getActivity(), CitiesActivity.class);
                startActivity(inLoc);
            }
        });


        return root;
    }




    /*public void startLocation(View v){

    }

    public void startRate(View v){
        Intent inRate = new Intent(getActivity(), CitiesActivity.class);
        startActivity(inRate);
    }*/
}