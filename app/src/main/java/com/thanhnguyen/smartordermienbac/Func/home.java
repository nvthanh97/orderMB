package com.thanhnguyen.smartordermienbac.Func;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.thanhnguyen.smartordermienbac.FragmentApp.HienThiDanhSachMonAnFragment;
import com.thanhnguyen.smartordermienbac.FragmentApp.HienThiNhanVienFragment;
import com.thanhnguyen.smartordermienbac.FragmentApp.HienThiThucDonFragment;
import com.thanhnguyen.smartordermienbac.FragmentApp.ShowTable;
import com.thanhnguyen.smartordermienbac.R;
import com.thanhnguyen.smartordermienbac.nhapnguyenlieu;

import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MANH;
import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MyPREFERENCES;
import static com.thanhnguyen.smartordermienbac.Func.dangnhap.QUYEN;

public class home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    int maq;
    TextView txtTenNhanVien_Navigation;
    SharedPreferences sharedpreferences;
    FragmentManager fragmentManager;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        BottomNavigationView navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        navigation.setItemIconTintList(null);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationview_trangchu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        View view = navigationView.inflateHeaderView(R.layout.layout_header_navigation_trangchu);
        txtTenNhanVien_Navigation = (TextView) view.findViewById(R.id.txtTenNhanVien_Navigation);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.mo,R.string.dong){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        String tendn = intent.getStringExtra("tendn");
        maq= intent.getIntExtra("maquyen",0);
        maq=Integer.parseInt(sharedpreferences.getString(QUYEN, "1"));
        txtTenNhanVien_Navigation.setText(tendn);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction tranHienThiBanAn = fragmentManager.beginTransaction();
        ShowTable hienThiBanAnFagment = new ShowTable();
        tranHienThiBanAn.replace(R.id.content, hienThiBanAnFagment);
        tranHienThiBanAn.commit();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itTrangChu:
                FragmentTransaction tranHienThiBanAn = fragmentManager.beginTransaction();
                ShowTable hienThiBanAnFagment = new ShowTable();
                tranHienThiBanAn.setCustomAnimations(R.anim.hieuung_activity_vao,R.anim.hieuung_activity_ra);
                tranHienThiBanAn.replace(R.id.content,hienThiBanAnFagment);
                tranHienThiBanAn.commit();
                item.setChecked(true);
                drawerLayout.closeDrawers();
                ;break;

            case R.id.itThucDon:
                FragmentTransaction tranHienThiThucDon = fragmentManager.beginTransaction();
                HienThiDanhSachMonAnFragment hienThiThucDonFragment = new HienThiDanhSachMonAnFragment();
                tranHienThiThucDon.setCustomAnimations(R.anim.hieuung_activity_vao, R.anim.hieuung_activity_ra);
                tranHienThiThucDon.replace(R.id.content,hienThiThucDonFragment);
                tranHienThiThucDon.commit();
                item.setChecked(true);
                drawerLayout.closeDrawers();
                ;break;

            case R.id.itNhanVien:
                if( maq==1){
                    FragmentTransaction x = fragmentManager.beginTransaction();
                    HienThiNhanVienFragment y = new HienThiNhanVienFragment();
                    x.setCustomAnimations(R.anim.hieuung_activity_vao, R.anim.hieuung_activity_ra);
                    x.replace(R.id.content,y);
                    x.commit();
                    item.setChecked(true);
                    drawerLayout.closeDrawers();
                }
                else
                    new AlertDialog.Builder(this)
                            .setTitle("Thông báo")
                            .setMessage("Bạn không phải Admin")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                ;break;
            case R.id.itThemTD:
                if( maq==1){
                    Intent themtd = new Intent(home.this, ThemThucDonActivity.class);
                    SharedPreferences.Editor editor1 = sharedpreferences.edit();
                    String b=sharedpreferences.getString(MANH, "");
                    editor1.putString(MANH, b);
                    editor1.commit();
                    startActivity(themtd);
                    ;break;
                }
                else
                    new AlertDialog.Builder(this)
                            .setTitle("Thông báo")
                            .setMessage("Bạn không phải Admin")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                ;break;
            case R.id.itNhapNguyenLieu:
                Intent nhapnl = new Intent(home.this, nhapnguyenlieu.class);
                SharedPreferences.Editor editor1 = sharedpreferences.edit();
                String b=sharedpreferences.getString(MANH, "");
                editor1.putString(MANH, b);
                editor1.commit();
                startActivity(nhapnl);
                ;break;
            case R.id.logout:
                Intent login = new Intent(home.this, dangnhap.class);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                String a=sharedpreferences.getString(MANH, "");
                editor.putString(MANH, a);
                editor.commit();
                startActivity(login);
                ;break;
            case R.id.itNB:
            {
                if( maq==1 || maq==3){
                    Intent nhabep = new Intent(home.this, NhaBepNhanDL.class);
                    startActivity(nhabep);
                    item.setChecked(true);
                    drawerLayout.closeDrawers();
                    ;break;
                }
                else
                    new AlertDialog.Builder(this)
                            .setTitle("Thông báo")
                            .setMessage("Bạn không phải nhân viên nhà bếp")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                ;break;

            }
            case R.id.itLS:
            {
                Intent nhabep = new Intent(home.this, HTLichSu.class);
                startActivity(nhabep);
                item.setChecked(true);
                drawerLayout.closeDrawers();
                ;break;
            }
            case R.id.itTK:
            {
                if( maq==1){
                    Intent nhabep = new Intent(home.this, HTThongKe.class);
                    startActivity(nhabep);
                    item.setChecked(true);
                    drawerLayout.closeDrawers();
                    ;break;
                }
                else
                    new AlertDialog.Builder(this)
                            .setTitle("Thông báo")
                            .setMessage("Bạn không phải Admin")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                ;break;

            }
            case R.id.itGoiNhieu:
            {
                Intent nhabep = new Intent(home.this, HtThongKeMonAn.class);
                startActivity(nhabep);
                item.setChecked(true);
                drawerLayout.closeDrawers();
                ;break;
            }



        }
        return false;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.itTrangChu:
                    FragmentTransaction tranHienThiBanAn = fragmentManager.beginTransaction();
                    ShowTable hienThiBanAnFagment = new ShowTable();
                    tranHienThiBanAn.setCustomAnimations(R.anim.hieuung_activity_vao,R.anim.hieuung_activity_ra);
                    tranHienThiBanAn.replace(R.id.content,hienThiBanAnFagment);
                    tranHienThiBanAn.commit();
                    item.setChecked(true);
                    drawerLayout.closeDrawers();
                    ;break;

                case R.id.itThucDon:
                    FragmentTransaction tranHienThiThucDon = fragmentManager.beginTransaction();
                    HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
                    tranHienThiThucDon.setCustomAnimations(R.anim.hieuung_activity_vao, R.anim.hieuung_activity_ra);
                    tranHienThiThucDon.replace(R.id.content,hienThiThucDonFragment);
                    tranHienThiThucDon.commit();
                    item.setChecked(true);
                    drawerLayout.closeDrawers();
                    ;break;

                case R.id.itLS:
                {
                    Intent nhabep = new Intent(home.this, HTLichSu.class);
                    startActivity(nhabep);
                    item.setChecked(true);
                    drawerLayout.closeDrawers();
                    ;break;
                }


            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
