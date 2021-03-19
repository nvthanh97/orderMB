package com.thanhnguyen.smartordermienbac;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.thanhnguyen.smartordermienbac.Func.dangnhap;

public class splash_screen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, dangnhap.class);
        startActivity(intent);
        finish();
    }
}
