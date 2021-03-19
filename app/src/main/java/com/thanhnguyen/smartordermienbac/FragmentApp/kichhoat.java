package com.thanhnguyen.smartordermienbac.FragmentApp;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.thanhnguyen.smartordermienbac.R;
import com.thanhnguyen.smartordermienbac.config;

public class kichhoat extends AppCompatActivity {

WebView webView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.kichhoat);


        super.onCreate(savedInstanceState);
        webView= findViewById(R.id.webView);
        goUrl();

    }

    private void goUrl()  {
        String url = config.domain;

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
    }



}
