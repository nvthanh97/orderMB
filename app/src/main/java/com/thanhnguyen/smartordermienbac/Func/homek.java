package com.thanhnguyen.smartordermienbac.Func;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.thanhnguyen.smartordermienbac.FragmentApp.HienThiThucDonFragmentk;
import com.thanhnguyen.smartordermienbac.R;

import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MyPREFERENCES;

public class homek extends AppCompatActivity {

    DrawerLayout drawerLayout;
    public String tendn;
    SharedPreferences sharedpreferences;
    FragmentManager fragmentManager;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homek);
        //BottomNavigationView navigation = findViewById(R.id.nav_viewkhachhang);
        //navigation.setItemIconTintList(null);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction tranHienThiBanAn = fragmentManager.beginTransaction();
        HienThiThucDonFragmentk hot = new HienThiThucDonFragmentk();
        tranHienThiBanAn.replace(R.id.content, hot);
        tranHienThiBanAn.commit();
    }




}
