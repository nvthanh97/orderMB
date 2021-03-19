package com.thanhnguyen.smartordermienbac.Func;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.thanhnguyen.smartordermienbac.CustomAdapter.AdapterHienThiDanhSachMonAn;
import com.thanhnguyen.smartordermienbac.DTO.MonAnDTO;
import com.thanhnguyen.smartordermienbac.R;
import com.thanhnguyen.smartordermienbac.config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KhachXemDSMon extends AppCompatActivity {
    GridView gridView;
    List<MonAnDTO> monAnDTOList;
    AdapterHienThiDanhSachMonAn adapterHienThiDanhSachMonAn;
    int maban;
    int mamon;
    int maloai;
    int x;
    SharedPreferences sharedpreferences;
    String manh;
    List<MonAnDTO> listBA = new ArrayList<MonAnDTO>();
    String URL_GET_PRODUCT= config.domain +"android/getmonan.php";
    public void LayTatCaMonAn(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_GET_PRODUCT+"?maloai="+maloai+"&manh="+manh, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                          //  Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                            // Convert json array to jsonobject
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                MonAnDTO product = new MonAnDTO();
                                product.setMaMonAn(item.getInt("mamon"));
                                product.setTenMonAn(item.getString("tenmon"));
                                product.setMaLoai(item.getInt("maloai"));
                                product.setGiaTien(item.getString("giatien"));
                                product.setHinhAnh(item.getString("hinhanh"));

                                listBA.add(product);
                            }
                            HienThiMonAn(listBA);
                            //HienThiDanhSachBanAn();

                            // productAdapter.notifyDataSetChanged();

                        } catch (Exception ex) {
                            //Toast.makeText(getActivity(), ""+ ex.toString(), Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(KhachXemDSMon.this);
        requestQueue.add(request);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hienthithucdon);
        Intent bundle= getIntent();
            maloai = bundle.getIntExtra("maloai",0);
            maban = bundle.getIntExtra("maban",0);
            mamon=bundle.getIntExtra("mamon",0);
            manh=bundle.getStringExtra("manh");
            //Toast.makeText(getActivity(), "mamon1"+ mamon, Toast.LENGTH_LONG).show();

            LayTatCaMonAn();
            monAnDTOList = listBA;
            gridView=findViewById(R.id.gvHienThiThucDon);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(maban !=0 ){
                        Intent iSoLuong = new Intent(KhachXemDSMon.this, SoLuongActivity.class);
                        iSoLuong.putExtra("maban",maban);
                        iSoLuong.putExtra("mamonan",monAnDTOList.get(position).getMaMonAn());

                        startActivity(iSoLuong);
                    }
                    else {
                        new AlertDialog.Builder(KhachXemDSMon.this)
                                .setTitle("Món ăn không hỗ trợ giao hàng tại nhà")
                                .setMessage("Bạn đến cửa hàng để thưởng thức món ăn nhé <._.>")

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setIcon(R.drawable.friendship)
                                .show();
                    }

                }
            });

    }

    
    public  void HienThiMonAn(List<MonAnDTO> a)
    {
        adapterHienThiDanhSachMonAn = new AdapterHienThiDanhSachMonAn(KhachXemDSMon.this,R.layout.custom_layout_hienthidanhsachmonan,a);
        gridView.setAdapter(adapterHienThiDanhSachMonAn);
        adapterHienThiDanhSachMonAn.notifyDataSetChanged();
    }
    
}
