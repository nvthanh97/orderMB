package com.thanhnguyen.smartordermienbac.FragmentApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.thanhnguyen.smartordermienbac.CustomAdapter.AdapterHienThiDanhSachMonAn;
import com.thanhnguyen.smartordermienbac.CustomAdapter.AdapterHienThiLoaiMonAnThucDon;
import com.thanhnguyen.smartordermienbac.DAO.MonAnDAO;
import com.thanhnguyen.smartordermienbac.DTO.LoaiMonAnDTO;
import com.thanhnguyen.smartordermienbac.DTO.MonAnDTO;
import com.thanhnguyen.smartordermienbac.Func.SoLuongActivity;
import com.thanhnguyen.smartordermienbac.Func.home;
import com.thanhnguyen.smartordermienbac.R;
import com.thanhnguyen.smartordermienbac.config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MANH;
import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MyPREFERENCES;

public class HienThiDanhSachMonAnFragment extends Fragment {
    GridView gridView, gvloaimon;
    MonAnDAO monAnDAO;
    List<LoaiMonAnDTO> loaiMonAnDTOs;
    SharedPreferences sharedpreferences;
    String manh;
    SharedPreferences sharedPreferences;
    List<MonAnDTO> monAnDTOList;
    List<LoaiMonAnDTO> listBA1 = new ArrayList<LoaiMonAnDTO>();
    AdapterHienThiDanhSachMonAn adapterHienThiDanhSachMonAn;
    int maban;
    int mamon;
    int maloai;
    int x;

    List<MonAnDTO> listBA = new ArrayList<MonAnDTO>();
    String URL_GET_PRODUCT= config.domain +"android/getmonan.php";
    String URL_GET_PRODUCT2= config.domain +"android/getallmonan.php";
    String URL_GET_PRODUCT1= config.domain +"android/getloaimon.php";





    public void LayTatCaLoaiMon(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_GET_PRODUCT1+"?manh="+manh, null,
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
                                listBA1.add(product);
                            }
                            HienThiLoaiMon();

                        } catch (Exception ex) {
//                            Toast.makeText(getActivity(), ""+ ex.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), ""+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

    }
    public void HienThiLoaiMon()
    {
        AdapterHienThiLoaiMonAnThucDon adapdater = new AdapterHienThiLoaiMonAnThucDon(getActivity(),R.layout.custom_dsmonnew,listBA1);
        gvloaimon.setAdapter(adapdater);
        adapdater.notifyDataSetChanged();

    }

    @Nullable

    public void LayTatCaMonAn(String mal){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_GET_PRODUCT+"?maloai="+mal+"&manh="+manh, null,
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
                            Toast.makeText(getActivity(), ""+ ex.toString(), Toast.LENGTH_SHORT).show();
                        }
                        //                  loading.dismiss();

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), ""+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

    }

    public void LayFullMonAn(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_GET_PRODUCT2+"?manh="+manh, null,
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
                            Toast.makeText(getActivity(), ""+ ex.toString(), Toast.LENGTH_SHORT).show();
                        }
                        //                  loading.dismiss();

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), ""+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hienthucdonnew, container, false);

        gridView = (GridView) view.findViewById(R.id.gvHienThiThucDon);
        gvloaimon = (GridView) view.findViewById(R.id.gv_HienThiLoaiMon1);
        sharedpreferences = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        manh= sharedpreferences.getString(MANH, "");
        ((home)getActivity()).getSupportActionBar().setTitle("Danh sách món");
        monAnDAO = new MonAnDAO(getActivity());


        Bundle bundle = getArguments();
        if(bundle !=  null){
            maloai = bundle.getInt("maloai");
            maban = bundle.getInt("maban");
            mamon=bundle.getInt("mamon");
            //Toast.makeText(getActivity(), "mamon1"+ mamon, Toast.LENGTH_LONG).show();

            //LayTatCaMonAn();
            LayTatCaLoaiMon();
            monAnDTOList = listBA;
            try{
                if ( String.valueOf(maloai).equals("0"))
                {
                    LayFullMonAn();
                }
                else {
                    LayTatCaMonAn(String.valueOf(maloai));
                    Log.d("thanhnv", maloai+"");
                }

            }
            catch (Exception ex)
            {

            }


            gvloaimon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int maloai = listBA1.get(position).getMaLoai();
                    listBA.clear();
                    LayTatCaMonAn(String.valueOf(maloai));

//                    HienThiDanhSachMonAnFragment hienThiDanhSachMonAnFragment = new HienThiDanhSachMonAnFragment();
//
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("maloai", maloai);
//                    bundle.putInt("maban",maban);
//                    hienThiDanhSachMonAnFragment.setArguments(bundle);
//
//                    FragmentTransaction transaction = fragmentManager.beginTransaction();
//                    transaction.replace(R.id.content, hienThiDanhSachMonAnFragment).addToBackStack("hienthiloai");
//
//                    transaction.commit();
                }
            });
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(maban !=0 ){
                        Intent iSoLuong = new Intent(getActivity(), SoLuongActivity.class);
                        iSoLuong.putExtra("maban",maban);
                        iSoLuong.putExtra("mamonan",monAnDTOList.get(position).getMaMonAn());

                        startActivity(iSoLuong);
                    }

                }
            });

        }

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    getFragmentManager().popBackStack("hienthiloai", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });
        registerForContextMenu(gridView);
        return view;
    }
    public  void HienThiMonAn(List<MonAnDTO> a)
    {
        adapterHienThiDanhSachMonAn = new AdapterHienThiDanhSachMonAn(getActivity(),R.layout.custom_loaimonnew,a);
        gridView.setAdapter(adapterHienThiDanhSachMonAn);
        adapterHienThiDanhSachMonAn.notifyDataSetChanged();
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.xoamonan, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        mamon = monAnDTOList.get(vitri).getMaMonAn();
        //Toast.makeText(getActivity(), "mamon"+  mamon, Toast.LENGTH_SHORT).show();


        switch (id){

            case R.id.itXoa:
                xoa();
        }
        return super.onContextItemSelected(item);

       /* return super.onContextItemSelected(item);
        if (item.getItemId()==R.id.itXoa)
        {
            boolean kiemtra = monAnDAO.XoaMonAnTheoMa(maban);
            if(kiemtra){
                Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.xoathanhcong),Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.loi) + maban,Toast.LENGTH_SHORT).show();
            }

        }*/

    }

    public void xoa()
    {

        Toast.makeText(getActivity(), "mamon"+ mamon, Toast.LENGTH_SHORT).show();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, config.domain +"android/xoamonan.php?mamon="+mamon,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        listBA.clear();
                        LayTatCaMonAn(String.valueOf(maloai));
                        HienThiMonAn(listBA);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Kết nối sever thất bại! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("mamon",String.valueOf(mamon));
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
