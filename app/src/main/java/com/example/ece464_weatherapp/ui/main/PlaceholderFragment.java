package com.example.ece464_weatherapp.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ece464_weatherapp.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cities_web_view, container, false);

        TextView tvcitiestabbed=root.findViewById(R.id.tvCityTabbed);

        if(getArguments().getInt(ARG_SECTION_NUMBER)==1){
            tvcitiestabbed.setText("Larnaca");
            WebView wv =root.findViewById(R.id.wvtabbed);
            String Url="https://weather.com/weather/today/l/34.90,33.62?par=google&temp=c";

            wv.setWebViewClient(new WebViewClient());
            wv.loadUrl(Url);
        }
        else if(getArguments().getInt(ARG_SECTION_NUMBER)==2){
            tvcitiestabbed.setText("Limassol");
            WebView wv =root.findViewById(R.id.wvtabbed);
            String Url="https://weather.com/weather/today/l/34.71,33.02?par=google&temp=c";
            wv.setWebViewClient(new WebViewClient());
            wv.loadUrl(Url);
        }
        else if(getArguments().getInt(ARG_SECTION_NUMBER)==3){
            tvcitiestabbed.setText("Nicosia");
            WebView wv =root.findViewById(R.id.wvtabbed);
            String Url="https://weather.com/weather/today/l/35.19,33.38?par=google&temp=c";
            wv.setWebViewClient(new WebViewClient());
            wv.loadUrl(Url);
        }
        else if(getArguments().getInt(ARG_SECTION_NUMBER)==4){
            tvcitiestabbed.setText("Paphos");
            WebView wv =root.findViewById(R.id.wvtabbed);
            String Url="https://weather.com/weather/today/l/34.77,32.43?par=google&temp=c";
            wv.setWebViewClient(new WebViewClient());
            wv.loadUrl(Url);
        }
        else if(getArguments().getInt(ARG_SECTION_NUMBER)==5){
            tvcitiestabbed.setText("Famagusta");
            WebView wv =root.findViewById(R.id.wvtabbed);
            String Url="https://weather.com/weather/today/l/35.11,33.92?par=google&temp=c";
            wv.setWebViewClient(new WebViewClient());
            wv.loadUrl(Url);
        }

        return root;
    }
}