package com.thanhnguyen.smartordermienbac.FragmentApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;
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
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.thanhnguyen.smartordermienbac.CustomAdapter.AdapterHienThiLoaiMonAnThucDon;
import com.thanhnguyen.smartordermienbac.DAO.LoaiMonAnDAO;
import com.thanhnguyen.smartordermienbac.DTO.HuyenDTO;
import com.thanhnguyen.smartordermienbac.DTO.LoaiHinhDTO;
import com.thanhnguyen.smartordermienbac.DTO.LoaiMonAnDTO;
import com.thanhnguyen.smartordermienbac.DTO.TinhDTO;
import com.thanhnguyen.smartordermienbac.DTO.XaDTO;
import com.thanhnguyen.smartordermienbac.Func.DangBai;
import com.thanhnguyen.smartordermienbac.R;
import com.thanhnguyen.smartordermienbac.Func.CustomAdapter;
import com.thanhnguyen.smartordermienbac.Func.Custom_Adapter_Huyen;
import com.thanhnguyen.smartordermienbac.Func.Custom_Adapter_LH;
import com.thanhnguyen.smartordermienbac.Func.Custom_Adapter_Xa;
import com.thanhnguyen.smartordermienbac.config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimKiemTheoLoai extends Fragment {

    String matinh,mahuyen,maxa, maloaihinh;
    private Spinner customSpinner;
    private  Spinner spinhuyen, spinxa, spinloai;
    GridView gridView;
    List<LoaiMonAnDTO> loaiMonAnDTOs;
    LoaiMonAnDAO loaiMonAnDAO;
    FragmentManager fragmentManager;
    int maLoai;
    int maban;
    int maquyen;
    private PublisherAdView adView;

    SharedPreferences sharedPreferences;
    List<LoaiMonAnDTO> listBA = new ArrayList<LoaiMonAnDTO>();
    String URL_GET_PRODUCT= config.domain +"android/getloaihinh.php";
    public void LayTatCaLoaiMon(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_GET_PRODUCT, null,
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
                                product.setMaLoai(item.getInt("maloaihinh"));
                                product.setTenLoai(item.getString("tenloaihinh"));
                                product.setHinhAnh(item.getString("hinhanhlh"));
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
                        Toast.makeText(getActivity(), ""+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

    }
    public void HienThiLoaiMon()
    {
        AdapterHienThiLoaiMonAnThucDon adapdater = new AdapterHienThiLoaiMonAnThucDon(getActivity(), R.layout.custom_layout_hienloaimonan,loaiMonAnDTOs);
        gridView.setAdapter(adapdater);
        adapdater.notifyDataSetChanged();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LoadLoaiMonSpiner();
        //LoadLoaiHinh();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.timkiemtheoloai,container,false);
        setHasOptionsMenu(true);
        listBA.clear();
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        gridView = (GridView) view.findViewById(R.id.gvHienThiThucDon);

        fragmentManager = getActivity().getSupportFragmentManager();
        LayTatCaLoaiMon();
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        adView = view.findViewById(R.id.ad_view);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        adView.loadAd(adRequest);
        loaiMonAnDTOs = listBA;
        loaiMonAnDAO = new LoaiMonAnDAO(getActivity());
        customSpinner = view.findViewById(R.id.spinTinh);
        spinhuyen= view.findViewById(R.id.spinHuyen);
        spinxa= view.findViewById(R.id.spinXa);
        spinloai= view.findViewById(R.id.loaiHinh);


        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen",0);



        Bundle bDuLieuThucDon = getArguments();
        if(bDuLieuThucDon != null){
            maban = bDuLieuThucDon.getInt("maban");
        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int x = loaiMonAnDTOs.get(position).getMaLoai();
                //Toast.makeText(getContext(), "maloai: "+ x, Toast.LENGTH_SHORT).show();
                try{

                    Intent iDangKy = new Intent(getContext(), hienthitungloai.class);
                    iDangKy.putExtra("matinh",matinh);
                    iDangKy.putExtra("mahuyen",mahuyen);
                    iDangKy.putExtra("maxa",maxa);
                    iDangKy.putExtra("maloaihinh",String.valueOf(x));
                    startActivity(iDangKy);

                }
                catch (Exception ex)
                {
                    Toast.makeText(getContext(), "er"+ ex, Toast.LENGTH_SHORT).show();
                }

                /*HienThiDanhSachMonAnFragment hienThiDanhSachMonAnFragment = new HienThiDanhSachMonAnFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("maloai", maloai);
                bundle.putInt("maban",maban);
                hienThiDanhSachMonAnFragment.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content, hienThiDanhSachMonAnFragment).addToBackStack("hienthiloai");

                transaction.commit();*/
            }
        });


        registerForContextMenu(gridView);
        return view;
    }


    public List<TinhDTO> listLM = new ArrayList<TinhDTO>();
    String urltinh= config.domain +"android/gettinhtp.php";
    public List<TinhDTO> LoadLoaiMonSpiner(){
        listLM.clear();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urltinh, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(DangBai.this, response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            //Toast.makeText(DangBai.this, response.toString(), Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                TinhDTO product = new TinhDTO();
                                product.setMatinh(item.getString("matinh"));
                                product.setTentinh(item.getString("tentinh"));
                                listLM.add(product);
                            }
                            Loadtinh();


                        } catch (Exception ex) {
                            Toast.makeText(getContext(), ""+ ex.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), ""+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
        return listLM;

    }


    public void Loadtinh()
    {
        CustomAdapter customAdapter = new CustomAdapter(getContext(), (ArrayList<TinhDTO>) listLM);

        if (customSpinner != null) {
            customSpinner.setAdapter(customAdapter);
            customSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    TinhDTO items = (TinhDTO) parent.getSelectedItem();
                    matinh=items.getMatinh();
                    //Toast.makeText(getContext(), "matinh"+ matinh, Toast.LENGTH_SHORT).show();
                    LoadHuyen(matinh);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    public List<HuyenDTO> listHuyen = new ArrayList<HuyenDTO>();
    public List<HuyenDTO> LoadHuyen(String mt){
        String URL_GET_HUYEN= config.domain +"android/gethuyen.php?matinh="+mt;
        listHuyen.clear();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_GET_HUYEN, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(DangBai.this, response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            //Toast.makeText(DangBai.this, response.toString(), Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                HuyenDTO product = new HuyenDTO();
                                product.setMahuyen(item.getString("mahuyen"));
                                product.setTenhuyen(item.getString("tenhuyen"));
                                //calogy+=item.getString("tenloai")+ " - ";
                                //Toast.makeText(DangBai.this, "dmm>>>"+ item.getString("tenloai"), Toast.LENGTH_LONG).show();

                                listHuyen.add(product);
                            }
                            Loadhuyen();


                        } catch (Exception ex) {
                            Toast.makeText(getContext(), ""+ ex.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), ""+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
        return listHuyen;

    }

    public void Loadhuyen()
    {

        Custom_Adapter_Huyen custom_adapter_huyen= new Custom_Adapter_Huyen(getContext(), (ArrayList<HuyenDTO>) listHuyen);

        if (spinhuyen != null) {
            spinhuyen.setAdapter(custom_adapter_huyen);
            spinhuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    HuyenDTO items = (HuyenDTO) parent.getSelectedItem();
                    mahuyen = items.getMahuyen();
                    LoadXa(mahuyen);
                    //Toast.makeText(DangBai.this, "ma huyen"+ mahuyen, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }




    public List<XaDTO> listxa = new ArrayList<XaDTO>();
    public List<XaDTO> LoadXa(String mh){
        listxa.clear();
        //Toast.makeText(this, "mahuyenxxx"+ mahuyen, Toast.LENGTH_SHORT).show();
        String URL_GET_HUYEN= config.domain +"android/getxa.php?mahuyen="+mh;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_GET_HUYEN, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listxa.clear();
                        //Toast.makeText(DangBai.this, response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            //Toast.makeText(DangBai.this, response.toString(), Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                XaDTO product = new XaDTO();
                                product.setMaxa(item.getString("maxa"));
                                product.setTenxa(item.getString("tenxa"));
                                listxa.add(product);
                            }
                            Loadxa();


                        } catch (Exception ex) {
                            Toast.makeText(getContext(), ""+ ex.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), ""+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
        return listxa;

    }

    public void Loadxa()
    {

        Custom_Adapter_Xa custom_adapter_xa= new Custom_Adapter_Xa(getContext(), (ArrayList<XaDTO>) listxa);

        if (spinxa != null) {
            spinxa.setAdapter(custom_adapter_xa);
            spinxa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    XaDTO items = (XaDTO) parent.getSelectedItem();
                    maxa = items.getMaxa();
                    //Toast.makeText(getContext(), "ma xa"+ maxa, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }



    public List<LoaiHinhDTO> listLoai = new ArrayList<LoaiHinhDTO>();


    public void Loadloaihinh()
    {

        Custom_Adapter_LH custom_adapter_lh= new Custom_Adapter_LH(getContext(), (ArrayList<LoaiHinhDTO>) listLoai);

        if (spinloai != null) {
            spinloai.setAdapter(custom_adapter_lh);
            spinloai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    LoaiHinhDTO items = (LoaiHinhDTO) parent.getSelectedItem();
                    maloaihinh = items.getMaloaihinh();
                    //Toast.makeText(getContext(), "ma loai hinh"+ maloaihinh, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }








    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itThemThucDon:
                Intent iThemThucDon = new Intent(getActivity(), DangBai.class);
                startActivity(iThemThucDon);
                getActivity().overridePendingTransition(R.anim.hieuung_activity_vao, R.anim.hieuung_activity_ra);
                ;break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        maLoai = loaiMonAnDTOs.get(vitri).getMaLoai();

        switch (id){

            case R.id.itXoa:
                xoa();
        }
        return super.onContextItemSelected(item);

    }
    public void xoa()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, config.domain +"android/xoaloaimon.php?maloai="+maLoai,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if ( response.equals("true"))
                        {
                            listBA.clear();
                            LayTatCaLoaiMon();
                            HienThiLoaiMon();
                        }
                        else if ( response.equals("con"))
                        {
                            Toast.makeText(getActivity(), "Vẫn còn món ăn thuộc loại này. Vui lòng xóa món ăn trước ^^", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Loi"+ response, Toast.LENGTH_SHORT).show();


                        }

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
                params.put("maloai",String.valueOf(maLoai));
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
