package com.thanhnguyen.smartordermienbac.Func;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.thanhnguyen.smartordermienbac.CustomAdapter.AdapterHienThiLoaiMonAnThucDon;
import com.thanhnguyen.smartordermienbac.DAO.LoaiMonAnDAO;
import com.thanhnguyen.smartordermienbac.DTO.LoaiMonAnDTO;
import com.thanhnguyen.smartordermienbac.R;
import com.thanhnguyen.smartordermienbac.config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KhachXemThucDon extends AppCompatActivity {

    GridView gridView;
    List<LoaiMonAnDTO> loaiMonAnDTOs;
    LoaiMonAnDAO loaiMonAnDAO;
    FragmentManager fragmentManager;
    int maLoai;
    int maban;
    int maquyen;
    SharedPreferences sharedpreferences;
    String manh;
    SharedPreferences sharedPreferences;
    List<LoaiMonAnDTO> listBA = new ArrayList<LoaiMonAnDTO>();
    String URL_GET_PRODUCT= config.domain +"android/getloaimon.php";
    public void LayTatCaLoaiMon(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_GET_PRODUCT+"?manh="+manh, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                            // Convert json array to jsonobject
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                LoaiMonAnDTO product = new LoaiMonAnDTO();
                                product.setMaLoai(item.getInt("maloai"));
                                product.setTenLoai(item.getString("tenloai"));
                                product.setHinhAnh(item.getString("hinhanh"));
                                listBA.add(product);
                            }
                            HienThiLoaiMon();

                        } catch (Exception ex) {
//                            Toast.makeText(getActivity(), ""+ ex.toString(), Toast.LENGTH_SHORT).show();
                        }
                        //                  loading.dismiss();

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getActivity(), ""+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(KhachXemThucDon.this);
        requestQueue.add(request);

    }
    public void HienThiLoaiMon()
    {
        AdapterHienThiLoaiMonAnThucDon adapdater = new AdapterHienThiLoaiMonAnThucDon(KhachXemThucDon.this, R.layout.custom_layout_hienloaimonan,loaiMonAnDTOs);
        gridView.setAdapter(adapdater);
        adapdater.notifyDataSetChanged();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hienthithucdon);
        Intent intent= getIntent();
        manh=intent.getStringExtra("manh");
        listBA.clear();

        gridView = (GridView) findViewById(R.id.gvHienThiThucDon);
        LayTatCaLoaiMon();
        loaiMonAnDTOs = listBA;
        loaiMonAnDAO = new LoaiMonAnDAO(KhachXemThucDon.this);


       gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int maloai = loaiMonAnDTOs.get(position).getMaLoai();
                Intent bundle= new Intent(KhachXemThucDon.this, KhachXemDSMon.class);
                bundle.putExtra("maloai", maloai);
                bundle.putExtra("maban",maban);
                bundle.putExtra("manh",manh);
                startActivity(bundle);


            }
        });


        registerForContextMenu(gridView);

    }



}
