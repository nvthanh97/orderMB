package com.thanhnguyen.smartordermienbac.FragmentApp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.thanhnguyen.smartordermienbac.CustomAdapter.AdapterShowFull;
import com.thanhnguyen.smartordermienbac.DTO.NhaHangDTO;
import com.thanhnguyen.smartordermienbac.Func.KhachXemThucDon;
import com.thanhnguyen.smartordermienbac.Func.danhgia;
import com.thanhnguyen.smartordermienbac.Func.home;
import com.thanhnguyen.smartordermienbac.R;
import com.thanhnguyen.smartordermienbac.config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class show_full extends AppCompatActivity implements View.OnClickListener {

    GridView gridView;
    String sdt,nd,manhgui;
    Button btngoi, btndanhgia, btnxembaidang;
    ImageView btnback;
    private boolean wc=false, dieuhoa=false, chodexe=false,wifi=false,chonauan=false,anninh=false;
    List<NhaHangDTO> nhanVienDTOList;
    String matinh="1",mahuyen="1",maxa="1", maloaihinh="1", mabaidang, tendn;


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.list_show_full);
        gridView= findViewById(R.id.gridviewkq);
        btngoi= findViewById(R.id.btngoidien);
        btnback= findViewById(R.id.btnBack);
        btndanhgia=findViewById(R.id.btndanhgia);
        btnxembaidang=findViewById(R.id.btnthongtin);
        btngoi.setOnClickListener(this);
        btnback.setOnClickListener(this);
        btnxembaidang.setOnClickListener(this);
        btndanhgia=findViewById(R.id.btndanhgia);
        btndanhgia.setOnClickListener(this);
        //btnxembaidang= findViewById(R.id.xem)

        Intent intent = getIntent();
        mabaidang = intent.getStringExtra("manh");
        tendn = intent.getStringExtra("tendn");
        //Toast.makeText(this, "x"+ mabaidang+ "x"+tendn, Toast.LENGTH_SHORT).show();
        getbd(mabaidang);
        super.onCreate(savedInstanceState);


    }

    final List<NhaHangDTO> listBA = new ArrayList<NhaHangDTO>();
    String urlbaidang= config.domain +"android/showfull.php";
    public List<NhaHangDTO> getbd(String a){
        listBA.clear();
        //Toast.makeText(this, "a"+ a+ "b"+ b, Toast.LENGTH_SHORT).show();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlbaidang+"?manh="+a,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(show_full.this, "tra ve"+ response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                NhaHangDTO bd = new NhaHangDTO();
                                bd.setManh(item.getString("manh"));
                                manhgui=item.getString("manh");
                                bd.setTennh(item.getString("tennh"));
                                bd.setTinh(item.getString("tinh"));
                                bd.setHuyen(item.getString("huyen"));
                                bd.setXa(item.getString("xa"));
                                bd.setMota(item.getString("mota"));
                                bd.setLoaihinh(item.getString("loainh"));
                                bd.setHinhanh(item.getString("hinhanh"));
                                bd.setTrangthai(item.getString("trangthai"));
                                bd.setBanonline(item.getString("banonline"));
                                bd.setNgaydang(item.getString("thoigiandang"));

                                listBA.add(bd);

                            }
                            HienThiDanhSachNhanVien();

                        } catch (Exception ex) {
                            Toast.makeText(show_full.this, "loi " +ex.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(show_full.this, "xx"+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(show_full.this);
        requestQueue.add(request);
        return listBA;

    }


    @Nullable


    private void HienThiDanhSachNhanVien(){
        nhanVienDTOList = listBA;
        AdapterShowFull adapterShowFull = new AdapterShowFull(show_full.this, R.layout.show_full_new,nhanVienDTOList);
        gridView.setAdapter(adapterShowFull);
        adapterShowFull.notifyDataSetChanged();
    }




    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        show_full.this.getMenuInflater().inflate(R.menu.menu_nv,menu);
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){

            case R.id.btngoidien: {
                Intent intent= new Intent(show_full.this, dattruoc.class);
                intent.putExtra("manh",mabaidang);
                intent.putExtra("tendn",tendn);
                startActivity(intent);
                break;
            }
            case R.id.btnthongtin:
            {
                Intent intent= new Intent(show_full.this, KhachXemThucDon.class);
                intent.putExtra("manh",mabaidang);
                intent.putExtra("tendn",tendn);
                startActivity(intent);
                break;
            }
            case R.id.btndanhgia:
            {
                Intent intent= new Intent(show_full.this, danhgia.class);
                intent.putExtra("manh",mabaidang);
                intent.putExtra("tendn",tendn);
                startActivity(intent);
                break;
            }
            case R.id.btnBack:
            {
                Intent intent= new Intent(show_full.this, home.class);
                startActivity(intent);
                break;
            }
        }

    }
}
