package com.thanhnguyen.smartordermienbac.FragmentApp;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.thanhnguyen.smartordermienbac.R;

public class noti extends AppCompatActivity {
    TextView notii;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noti);
        notii= findViewById(R.id.notii);
        String message= getIntent().getStringExtra("message");
        notii.setText(message);

    }
}
