package com.thanhnguyen.smartordermienbac;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thanhnguyen.smartordermienbac.CustomAdapter.AdapterHienThiThanhToan;
import com.thanhnguyen.smartordermienbac.CustomAdapter.Adapter_hienthi_nguyenlieu;
import com.thanhnguyen.smartordermienbac.DAO.BanAnDAO;
import com.thanhnguyen.smartordermienbac.DAO.GoiMonDAO;
import com.thanhnguyen.smartordermienbac.DTO.NguyenLieuDTO;
import com.thanhnguyen.smartordermienbac.DTO.ThanhToanDTO;
import com.thanhnguyen.smartordermienbac.Func.home;
import com.thanhnguyen.smartordermienbac.Func.themnguyenlieu;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MANH;
import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MyPREFERENCES;

public class nhapnguyenlieu extends AppCompatActivity{

    GridView gridView;
    ImageView imgView, imgback;
    int kt=0;
    int i;
    private NotificationCompat.Builder notBuilder;

    private static final int MY_NOTIFICATION_ID = 123459;

    private static final int MY_REQUEST_CODE = 1009;
    String tkhachdua, tthoilai;
    public String gc,tm,sl;
    public static int RESQUEST_CODE_TT = 999;
    int magoimon;

    public List<NguyenLieuDTO> listNew = new ArrayList<>();
    LinearLayout btnNhapNguyenLieu,btnUpdate,goback,btninhoadon;
    ImageButton btnchat;
    TextView txtTongTien,txtGhiChu;
    TextView txtchat1;
    GoiMonDAO goiMonDAO;
    List<ThanhToanDTO> listBA = new ArrayList<ThanhToanDTO>();
    String mgm, tennv;
    TextView tinhtrang;
    SharedPreferences sharedpreferences;
    public String manh;
    List<ThanhToanDTO> thanhToanDTOList;
    AdapterHienThiThanhToan adapterHienThiThanhToan;
    long tongtien = 0;
    String hinha,tendn;
    int mamonan, magoi;
    BanAnDAO banAnDAO;
    String ttgui;
    int maban=0;
    public String tenban;
    String urlupdategoimon= config.domain+ "android/themnguyenlieu.php";
    String urlnguyenlieu= config.domain +"android/getnguyenlieu.php";
    DatabaseReference mData = FirebaseDatabase.getInstance("https://smartorder-8f077.firebaseio.com/").getReference();
    FragmentManager fragmentManager;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    public List<NguyenLieuDTO> getbd(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlnguyenlieu+"?manh="+manh ,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("thanhnv", response.toString());

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                NguyenLieuDTO bd = new NguyenLieuDTO();
                                bd.setId(item.getString("id"));
                                bd.setTen(item.getString("ten"));
                                bd.setNgaynhap(item.getString("ngaynhap"));
                                bd.setThanhtien(item.getString("thanhtien"));
                                bd.setDonvi(item.getString("donvi"));
                                bd.setConlai(item.getString("conlai"));
                                listNew.add(bd);

                            }
                            HienThiDanhSachBanAn();

                        } catch (Exception ex) {
                            Log.d("thanhnv", ex+ "");
                            Toast.makeText(nhapnguyenlieu.this, "Bạn cần bật 3G/4G hoặc Wifi để ứng dụng kết nối tới Server" + ex, Toast.LENGTH_SHORT).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("thanhnv", error+ "");
                        Toast.makeText(nhapnguyenlieu.this, "Bạn cần bật 3G/4G hoặc Wifi để ứng dụng truy xuất dữ liệu từ server!" + error, Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(nhapnguyenlieu.this);
        requestQueue.add(request);
        return listNew;

    }

    private void HienThiDanhSachBanAn(){
        //Toast.makeText(getContext(), "da vao"+ banAnDTOList.size(), Toast.LENGTH_SHORT).show();
        Adapter_hienthi_nguyenlieu a  = new Adapter_hienthi_nguyenlieu(nhapnguyenlieu.this, R.layout.custom_nguyenlieu,listNew);
        gridView.setAdapter(a);
        a.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhapnguyenlieu);
        imgView = findViewById(R.id.imHinhThucDon);
        btnchat= findViewById(R.id.chatx);
        tinhtrang = findViewById(R.id.txtTinhTrang);
        gridView =  findViewById(R.id.gv_nguyenlieu);
        imgback= findViewById(R.id.btnBack);

        txtchat1=findViewById(R.id.txtchat1);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        manh= sharedpreferences.getString(MANH, "");
        //thanhnv
        getbd();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent iSoLuong = new Intent(ThanhToanActivity.this, updatesoluong.class);
//                iSoLuong.putExtra("magoimon",thanhToanDTOList.get(position).getMaGoiMon());
//                iSoLuong.putExtra("mamonan",thanhToanDTOList.get(position).getMaMonAn());
//                iSoLuong.putExtra("soluong",thanhToanDTOList.get(position).getSoLuong());
//                iSoLuong.putExtra("maban",maban);
//                iSoLuong.putExtra("tenban",tenban);
//                startActivity(iSoLuong);



            }
        });
        goback= findViewById(R.id.goback);
        btnNhapNguyenLieu =  findViewById(R.id.btn_nhapnguyenlieu);
        btnUpdate = findViewById(R.id.btn_update);
        txtTongTien = (TextView) findViewById(R.id.txtTongTien);
        txtGhiChu=findViewById(R.id.ghichubep);
        btninhoadon= findViewById(R.id.inhoadon);
        goiMonDAO = new GoiMonDAO(this);
        banAnDAO = new BanAnDAO(this);
        registerForContextMenu(gridView);

        Intent intent = getIntent();
        fragmentManager = getSupportFragmentManager();
        maban = getIntent().getIntExtra("maban",0);
        tenban = getIntent().getStringExtra("tenban");
        tendn=getIntent().getStringExtra("tendn");
        tennv=tendn;
        //Toast.makeText(ThanhToanActivity.this, "tdn: "+ tennv, Toast.LENGTH_SHORT).show();
        //laymagoimon(String.valueOf(maban), "false");
        //getthanhtoan(20);

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(nhapnguyenlieu.this, home.class);
                startActivity(i);
            }
        });
        btnNhapNguyenLieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(nhapnguyenlieu.this, themnguyenlieu.class);
                startActivity(i);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(nhapnguyenlieu.this, nhapkho.class);
                startActivity(i);
            }
        });

    }
}
