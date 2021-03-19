package com.thanhnguyen.smartordermienbac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.thanhnguyen.smartordermienbac.Func.dangnhap;

public class read_webview extends AppCompatActivity {
    WebView webView;
    ImageView imgback, imgnext;
    TextView tvTieuDe;
    Button btn_next;
    String url, name, manh;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_webview);
        webView= findViewById(R.id.wv_main);
        imgback=findViewById(R.id.btnBack);
        tvTieuDe= findViewById(R.id.tv_title);
        btn_next= findViewById(R.id.btn_next);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        name = intent.getStringExtra("name");
        try{
            manh = intent.getStringExtra("manh");
        }
        catch (Exception e)
        {
            manh="null";
        }
        tvTieuDe.setText(name);
        if ( name.equals("Hướng dẫn sử dụng"))
        {
            btn_next.setText("Mua gói/ nâng cấp");
            btn_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i= new Intent(read_webview.this, mua_goi_cuoc.class);
                    startActivity(i);
                }
            });
        }
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient()
        {
            // Links clicked will be shown on the webview
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webView.loadUrl(url);


        if ( String.valueOf(name).equals("Gói cước"))
        {
            btn_next.setText("Mua/gia hạn");
            btn_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i= new Intent(read_webview.this, mua_goi_cuoc.class);
                    startActivity(i);
                }
            });
        }
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(read_webview.this, dangnhap.class);
                startActivity(i);
            }
        });
    }
}


