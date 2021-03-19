package com.thanhnguyen.smartordermienbac.FragmentApp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thanhnguyen.smartordermienbac.CustomAdapter.AdapterHienThiBanAnKhach;
import com.thanhnguyen.smartordermienbac.DAO.BanAnDAO;
import com.thanhnguyen.smartordermienbac.DTO.BanAnDTO;
import com.thanhnguyen.smartordermienbac.R;
import com.thanhnguyen.smartordermienbac.Func.ThemBanAnActivity;
import com.thanhnguyen.smartordermienbac.config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MANH;
import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MyPREFERENCES;
import static com.thanhnguyen.smartordermienbac.Func.dangnhap.USERNAME;

public class ShowTableKhach extends AppCompatActivity {

    public static int RESQUEST_CODE_THEM = 111;
    private final int MY_PERMISSIONS_RECORD_AUDIO = 1;
    private PublisherAdView adView;
    int maban;
    Button btnkhac;
    Button btnhdsd;
    String tenban, manh,tenb, tennh,loa;
    TextView tennhahang;
    public static int RESQUEST_CODE_GOP = 69;
    public static int RESQUEST_CODE_SUA = 16;
    final List<BanAnDTO> listBA = new ArrayList<BanAnDTO>();
    //String urlgoimon=config.domain +"android/goimon.php";
    String URL_GET_PRODUCT=config.domain +"android/getalltablekhach.php";
    DatabaseReference mData = FirebaseDatabase.getInstance("https://smartorder-8f077.firebaseio.com/").getReference();

    public List<BanAnDTO> LayTatCaBanAn(final String a, final String b){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_GET_PRODUCT+"?manh="+a+"&tenban="+b, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(MANH, a.toUpperCase());
                        editor.putString(USERNAME, "Khách");
                        editor.commit();
                        //Toast.makeText(ShowTableKhach.this, a+ "-"+ b, Toast.LENGTH_SHORT).show();
                        try {
                           for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                BanAnDTO product = new BanAnDTO();
                                product.setMaBan(item.getInt("maban"));
                                product.setTenBan(item.getString("tenban"));
                                product.setDuocChon(item.getBoolean("tinhtrang"));
                                listBA.add(product);
                            }
                            HienThiDanhSachBanAn();
                        } catch (Exception ex) {
                            //Toast.makeText(getActivity(), ""+ ex.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShowTableKhach.this, "Bật 3G/Wifi"+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(ShowTableKhach.this);
        requestQueue.add(request);
        return listBA;

    }

    GridView gvHienThiBanAn;
    List<BanAnDTO> banAnDTOList;
    BanAnDAO banAnDAO;
    AdapterHienThiBanAnKhach adapterHienThiBanAnkhach;
    int maquyen = 0;
    SharedPreferences sharedpreferences;
    public void gettennh()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(ShowTableKhach.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.domain +"android/gettennhahang.php?manh="+manh,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(ShowTableKhach.this, "xx"+ response, Toast.LENGTH_SHORT).show();
                        tennh=response.toString().trim();
                        tennhahang.setText(tennh);
                        LayTatCaBanAn(manh,tenban);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShowTableKhach.this, "Kết nối sever thất bại! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("manh",manh);

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hienthibanankhach);
        tennhahang=findViewById(R.id.tennhahangg);
        btnhdsd= findViewById(R.id.hdsd);
        btnkhac=findViewById(R.id.btnudk);
        requestAudioPermissions();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        adView = findViewById(R.id.ad_view);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();

        adView.loadAd(adRequest);
        btnkhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://goimon.000webhostapp.com/udk.html";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });
        btnhdsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://goimon.000webhostapp.com/huongdan.html";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });

        Intent intent = getIntent();
        manh = intent.getStringExtra("manh");
        tenban= intent.getStringExtra("tenban");
        loa = intent.getStringExtra("loa");
        listBA.clear();
        gettennh();
        if(String.valueOf(loa)=="mot")
        {

        }
        if(String.valueOf(loa)=="hai")
        {

        }



        sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //manh= sharedpreferences.getString(MANH, "");
        //Toast.makeText(getContext(), manh, Toast.LENGTH_SHORT).show();



        gvHienThiBanAn = findViewById(R.id.gvHienBanAnKhach);

        sharedpreferences = this.getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedpreferences.getInt("maquyen",0);

        banAnDAO = new BanAnDAO(this);


       // LayTatCaBanAn(manh, tenban);
        banAnDTOList = listBA;

        HienThiDanhSachBanAn();
        //registerForContextMenu(gvHienThiBanAn);
        {
            registerForContextMenu(gvHienThiBanAn);
        }

    }


    @Nullable







    private void requestAudioPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
                Toast.makeText(this, "Cấp quyền Mic để nói chuyện với trợ lý ảo", Toast.LENGTH_LONG).show();

                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);

            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);
            }
        }
        //If permission is granted, then go ahead recording audio
        else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {

            //Go ahead with recording audio now

        }
    }

    //Handling callback
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_RECORD_AUDIO: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Bạn cần cấp quyền mic để nói chuyện với trợ lý ảo", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public void xoa()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.domain +"android/xoabanan.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            listBA.clear();
                            LayTatCaBanAn(manh,tenban);
                            HienThiDanhSachBanAn();

                        mData.child("MienBac").child(manh.toUpperCase()).child("BanAn").child(tenban).removeValue();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShowTableKhach.this, "Kết nối sever thất bại! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("tenban",tenban);
                params.put("maban",String.valueOf(maban));
                params.put("manh",manh.toUpperCase());

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){

            case R.id.itThemBanAn:
                Intent iThemBanAn = new Intent(ShowTableKhach.this, ThemBanAnActivity.class);
                iThemBanAn.putExtra("manh",manh);
                startActivityForResult(iThemBanAn,RESQUEST_CODE_THEM);
                ;break;
        }

        return true;
    }

    private void HienThiDanhSachBanAn(){
        banAnDTOList = listBA;
        adapterHienThiBanAnkhach = new AdapterHienThiBanAnKhach(ShowTableKhach.this,R.layout.custom_layout_hienthibanan,banAnDTOList);
        gvHienThiBanAn.setAdapter(adapterHienThiBanAnkhach);
        adapterHienThiBanAnkhach.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESQUEST_CODE_THEM){
            if(resultCode == Activity.RESULT_OK){
                Intent intent = data;
                boolean kiemtra = intent.getBooleanExtra("ketquathem",false);
                if(kiemtra){
                    HienThiDanhSachBanAn();
                }else{
                }
            }
        }else if(requestCode == RESQUEST_CODE_SUA){
            if(resultCode == Activity.RESULT_OK){
                Intent intent = data;
                boolean kiemtra = intent.getBooleanExtra("kiemtra",false);
                HienThiDanhSachBanAn();
                if(kiemtra){
                    Toast.makeText(ShowTableKhach.this, "Xong!", Toast.LENGTH_SHORT).show();
                    listBA.clear();
                    LayTatCaBanAn(manh,tenban);
                    HienThiDanhSachBanAn();
                }else{
                    Toast.makeText(ShowTableKhach.this, getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
