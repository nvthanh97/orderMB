package com.thanhnguyen.smartordermienbac.Func;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.thanhnguyen.smartordermienbac.R;
import com.thanhnguyen.smartordermienbac.config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MANH;
import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MyPREFERENCES;

public class HTLichSu  extends AppCompatActivity {
    ListView lwLS;
    List<lsgoimon> mangLS;
    SharedPreferences sharedpreferences;
    String manh;
    ImageView btnback;
    final List<lsgoimon> listBA = new ArrayList<lsgoimon>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hienthilsgoimon);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        manh= sharedpreferences.getString(MANH, "");

        lwLS= findViewById(R.id.listviewLSthanhtoan);
        btnback=findViewById(R.id.btnBack);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(HTLichSu.this, home.class);
                startActivity(i);
            }
        });
        mangLS= new ArrayList<lsgoimon>();
        mangLS=getLSTT();
    }

    private void HienThiDanhSachBanAn(){
        lsgoimonAdapter lsgoimonAdapter= new lsgoimonAdapter(HTLichSu.this, R.layout.lsthanhtoan, mangLS);
        lwLS.setAdapter(lsgoimonAdapter);
        lsgoimonAdapter.notifyDataSetChanged();
    }
    String URL_GET_PRODUCT= config.domain +"android/getlstt.php";

    public List<lsgoimon> getLSTT(){
        listBA.clear();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_GET_PRODUCT+"?manh="+manh, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                          //Toast.makeText(HTLichSu.this, response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                            // Convert json array to jsonobject
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                lsgoimon product = new lsgoimon();
                                product.setTenbanls(item.getString("bansd"));
                                product.setNgaygoils(item.getString("ngaygoi"));
                                product.setTongtienls(item.getString("tongtien"));
                                listBA.add(product);
                            }

                            HienThiDanhSachBanAn();
                            //Toast.makeText(HTLichSu.this, listBA.size(), Toast.LENGTH_SHORT).show();
                        } catch (Exception ex) {
                            Toast.makeText(HTLichSu.this, ""+ ex.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HTLichSu.this, ""+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
        return listBA;

    }

}
