package com.thanhnguyen.smartordermienbac.FragmentApp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.thanhnguyen.smartordermienbac.CustomAdapter.AdapterHienThiChiTiet;
import com.thanhnguyen.smartordermienbac.DAO.NhanVienDAO;
import com.thanhnguyen.smartordermienbac.DTO.NhaHangDTO;
import com.thanhnguyen.smartordermienbac.R;
import com.thanhnguyen.smartordermienbac.config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class hienthitungloai extends AppCompatActivity {

    GridView gridView;
    NhanVienDAO nhanVienDAO;
    ImageButton ti1, ti2, ti3, ti4,ti5,ti6;
    private boolean wc=false, dieuhoa=false, chodexe=false,wifi=false,chonauan=false,anninh=false;
    private Spinner customSpinner;
    private  Spinner spinhuyen, spinxa, spinloai;
    List<NhaHangDTO> nhanVienDTOList;
    String mabaidang;
    int maquyen;
    int manhanvien;
    String tendn, ns,sdt;
    String matinh="1",mahuyen="1",maxa="1", maloaihinh="1";
    SharedPreferences sharedPreferences;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.hienthitungloai);
        gridView= findViewById(R.id.gridviewkq);

        Intent intent = getIntent();
        maloaihinh = intent.getStringExtra("maloaihinh");
        //Toast.makeText(this, maloaihinh, Toast.LENGTH_SHORT).show();
        matinh = intent.getStringExtra("matinh");
        mahuyen = intent.getStringExtra("mahuyen");
        maxa = intent.getStringExtra("maxa");
        getbd(String.valueOf(maloaihinh), matinh, mahuyen, maxa);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mabaidang = listBA.get(position).getManh();
                tendn = listBA.get(position).getTennh();
                //Toast.makeText(getContext(), "mabaidang: "+ mabaidang+ "tendn"+ tendn, Toast.LENGTH_SHORT).show();
                try{

                    Intent iDangKy = new Intent(hienthitungloai.this, show_full.class);
                    iDangKy.putExtra("manh",mabaidang);
                    iDangKy.putExtra("tendn",tendn);
                    startActivity(iDangKy);

                }
                catch (Exception ex)
                {
                    Toast.makeText(hienthitungloai.this, "er"+ ex, Toast.LENGTH_SHORT).show();
                }
            }
        });


        super.onCreate(savedInstanceState);

    }

    final List<NhaHangDTO> listBA = new ArrayList<NhaHangDTO>();
    String urlbaidang= config.domain +"android/getbaidangtheoloai.php";
    public List<NhaHangDTO> getbd(String lh, String mt, String mh, String mx){
        listBA.clear();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlbaidang+"?manh="+lh+"&matinh="+mt+"&mahuyen="+mh+"&maxa="+mx ,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(hienthitungloai.this, "tra ve"+ response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            //Toast.makeText(hienthitungloai.this, matinh+ mahuyen+ maxa+ maloaihinh+ response.toString(), Toast.LENGTH_SHORT).show();
                            // Convert json array to jsonobject
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                NhaHangDTO bd = new NhaHangDTO();
                                bd.setManh(item.getString("manh"));
                                bd.setTennh(item.getString("tennh"));
                                bd.setBanonline(item.getString("banonline"));
                                bd.setTinh(item.getString("tinh"));
                                bd.setHuyen(item.getString("huyen"));
                                bd.setXa(item.getString("xa"));
                                bd.setNgaydang(item.getString("thoigiandang"));
                                bd.setMota(item.getString("mota"));
                                bd.setHinhanh(item.getString("hinhanh"));

                                listBA.add(bd);

                            }
                            HienThiDanhSachNhanVien();

                        } catch (Exception ex) {
                            Toast.makeText(hienthitungloai.this, "loi "+ ex.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getActivity(), ""+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(hienthitungloai.this);
        requestQueue.add(request);
        return listBA;

    }


    @Nullable


    private void HienThiDanhSachNhanVien(){
        nhanVienDTOList = listBA;
        AdapterHienThiChiTiet adapterHienThiChiTiet = new AdapterHienThiChiTiet(hienthitungloai.this, R.layout.custom_layout_hienthichitiet,nhanVienDTOList);
        gridView.setAdapter(adapterHienThiChiTiet);
        adapterHienThiChiTiet.notifyDataSetChanged();
    }










}
