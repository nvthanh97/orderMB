package com.thanhnguyen.smartordermienbac.Func;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thanhnguyen.smartordermienbac.FragmentApp.TimKiemTheoLoai;
import com.thanhnguyen.smartordermienbac.FragmentApp.Vitri;
import com.thanhnguyen.smartordermienbac.FragmentApp.hot;
import com.thanhnguyen.smartordermienbac.R;

import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MyPREFERENCES;

public class homekhachhang extends AppCompatActivity {

    DrawerLayout drawerLayout;
    public String tendn;
    SharedPreferences sharedpreferences;
    FragmentManager fragmentManager;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homekhachhang);
        BottomNavigationView navigation = findViewById(R.id.nav_viewkhachhang);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction tranHienThiBanAn = fragmentManager.beginTransaction();
        hot hot = new hot();
        tranHienThiBanAn.replace(R.id.content, hot);
        tranHienThiBanAn.commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.itGoiY:
                    FragmentTransaction tranHienThiBanAn = fragmentManager.beginTransaction();
                    hot hienThiBanAnFagment = new hot();
                    tranHienThiBanAn.setCustomAnimations(R.anim.hieuung_activity_vao, R.anim.hieuung_activity_ra);
                    tranHienThiBanAn.replace(R.id.content,hienThiBanAnFagment);
                    tranHienThiBanAn.commit();
                    item.setChecked(true);
                    ;break;

                case R.id.itLoai:
                    FragmentTransaction tranHienThiThucDon = fragmentManager.beginTransaction();
                    TimKiemTheoLoai timKiemTheoLoai = new TimKiemTheoLoai();
                    tranHienThiThucDon.setCustomAnimations(R.anim.hieuung_activity_vao, R.anim.hieuung_activity_ra);
                    tranHienThiThucDon.replace(R.id.content, timKiemTheoLoai);
                    tranHienThiThucDon.commit();
                    item.setChecked(true);
                    ;break;

                case R.id.itGanToi:
                {
                    FragmentTransaction x = fragmentManager.beginTransaction();
                    Vitri y = new Vitri();
                    x.setCustomAnimations(R.anim.hieuung_activity_vao, R.anim.hieuung_activity_ra);
                    x.replace(R.id.content,y);
                    x.commit();
                    item.setChecked(true);
                    ;break;
                }
                case R.id.itQR:
                {
                    Intent intent= new Intent(homekhachhang.this, Qrcode.class);
                    startActivity(intent);
                    ;break;
                }


            }
            return false;
        }
    };


}
