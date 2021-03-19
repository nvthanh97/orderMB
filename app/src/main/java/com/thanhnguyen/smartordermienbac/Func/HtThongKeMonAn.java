package com.thanhnguyen.smartordermienbac.Func;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

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

public class HtThongKeMonAn extends AppCompatActivity {
    ListView lwLS;
    List<tkmonan> mangLS;
    SharedPreferences sharedpreferences;
    String manh;
    final List<tkmonan> listBA = new ArrayList<tkmonan>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hienthongkemonan);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        manh= sharedpreferences.getString(MANH, "");

        lwLS= findViewById(R.id.listviewLSthanhtoan);
        mangLS= new ArrayList<tkmonan>();
        mangLS=getLSTT();
    }

    private void HienThiDanhSachBanAn(){
        tkMonAnadapter lsgoimonAdapter= new tkMonAnadapter(HtThongKeMonAn.this, R.layout.tkmonan, mangLS);
        lwLS.setAdapter(lsgoimonAdapter);
        lsgoimonAdapter.notifyDataSetChanged();
    }
    String URL_GET_PRODUCT= config.domain +"android/gettkmon.php";

    public List<tkmonan> getLSTT(){
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
                                tkmonan product = new tkmonan();
                                product.setTenmon(item.getString("monan"));
                                product.setSolangoi(item.getString("solangoi"));
                                listBA.add(product);
                            }

                            HienThiDanhSachBanAn();
                            //Toast.makeText(HTLichSu.this, listBA.size(), Toast.LENGTH_SHORT).show();
                        } catch (Exception ex) {
                            Toast.makeText(HtThongKeMonAn.this, ""+ ex.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HtThongKeMonAn.this, ""+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
        return listBA;

    }

}
