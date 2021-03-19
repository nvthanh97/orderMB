package com.thanhnguyen.smartordermienbac.FragmentApp;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.thanhnguyen.smartordermienbac.CustomAdapter.AdapterHienThiChiTiet;
import com.thanhnguyen.smartordermienbac.CustomAdapter.DiaDiemAdapter;
import com.thanhnguyen.smartordermienbac.DAO.NhanVienDAO;
import com.thanhnguyen.smartordermienbac.DTO.BaiDangDTO;
import com.thanhnguyen.smartordermienbac.DTO.DiaDiemDTO;
import com.thanhnguyen.smartordermienbac.DTO.NhaHangDTO;
import com.thanhnguyen.smartordermienbac.R;
import com.thanhnguyen.smartordermienbac.config;
import com.thanhnguyen.smartordermienbac.Func.dangky;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Vitri extends Fragment {

    GridView gridView;
    NhanVienDAO nhanVienDAO;
    List<NhaHangDTO> banAnDTOList;
    AdapterHienThiChiTiet adapterHienThiChiTiet;
    private boolean wc=false, dieuhoa=false, chodexe=false,wifi=false,chonauan=false,anninh=false;
    private Spinner customSpinner;
    List<BaiDangDTO> nhanVienDTOList;
    int maquyen;
    int manhanvien;
    private PublisherAdView adView;
    String mabaidang, tendn;
    String ns,sdt;
    String matinh="1",mahuyen="1",maxa="1";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    final List<NhaHangDTO> listNew = new ArrayList<NhaHangDTO>();
    String urlbaidang= config.domain +"android/getbaidangtudiadiem.php";
    public List<NhaHangDTO> getbd(String tinh, String huyen){
        listNew.clear();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlbaidang+"?matinh="+matinh+"&mahuyen="+mahuyen ,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(getContext(), "loi"+response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                NhaHangDTO bd = new NhaHangDTO();
                                bd.setManh(item.getString("manh"));
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

                                listNew.add(bd);

                            }
                            HienThiDanhSachNhanVien();

                        } catch (Exception ex) {
                            Toast.makeText(getActivity(), "Đang cập nhật khu vực này!" +ex.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"ee" +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
        return listNew;

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vitri,container,false);
        customSpinner = view.findViewById(R.id.spindiadiem);
        LoadLoaiMonSpiner();
        gridView = view.findViewById(R.id.gvKQ);
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        adView = view.findViewById(R.id.ad_view);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        adView.loadAd(adRequest);

        nhanVienDAO = new NhanVienDAO(getActivity());
        //GetNV(String.valueOf(wc), String.valueOf(chodexe),String.valueOf(wifi),String.valueOf(dieuhoa),String.valueOf(chonauan),String.valueOf(anninh), matinh, mahuyen, maxa);

        HienThiDanhSachNhanVien();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mabaidang = listNew.get(position).getManh();
                //tendn = listBA.get(position).getTendn();
                try{

                    Intent iDangKy = new Intent(getContext(), show_full.class);
                    iDangKy.putExtra("manh",mabaidang);
                    //iDangKy.putExtra("tendn",tendn);
                    startActivity(iDangKy);

                }
                catch (Exception ex)
                {
                    Toast.makeText(getContext(), "er"+ ex, Toast.LENGTH_SHORT).show();
                }
            }
        });




        return view;
    }


    private void HienThiDanhSachNhanVien(){
        banAnDTOList = listNew;
        //Toast.makeText(getContext(), "da vao"+ banAnDTOList.size(), Toast.LENGTH_SHORT).show();
        adapterHienThiChiTiet = new AdapterHienThiChiTiet(getContext(), R.layout.custom_layout_hienthichitiet,banAnDTOList);
        gridView.setAdapter(adapterHienThiChiTiet);
        adapterHienThiChiTiet.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_nv,menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Toast.makeText(getContext(), "Ma nhan vien"+ manhanvien, Toast.LENGTH_SHORT).show();

        switch (id){
            case R.id.itSua:
                Intent iDangKy = new Intent(getActivity(), dangky.class);
                iDangKy.putExtra("manv",manhanvien);
                iDangKy.putExtra("landautien","1");
                iDangKy.putExtra("tendn",tendn);
                iDangKy.putExtra("ns",ns);
                iDangKy.putExtra("sdt", sdt);
                startActivity(iDangKy);
                ;break;

            case R.id.itXoa:
                HienThiDanhSachNhanVien();
                 boolean kiemtra = nhanVienDAO.XoaNhanVien(manhanvien);
                 if (kiemtra){
                     HienThiDanhSachNhanVien();
                 }else{

                 }
                ;break;
        }
        return true;
    }



    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);


            MenuItem itThemBanAn = menu.add(1,R.id.itThemNhanVien,1,"Đăng bài ngay");
            itThemBanAn.setIcon(R.drawable.contract);
            itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itThemNhanVien:
                Intent iDangKy = new Intent(getActivity(), DangBai.class);
                iDangKy.putExtra("tendn",tendn);
                startActivity(iDangKy);
                ;break;
        }
        return true;
    }*/




    public List<DiaDiemDTO> listLM = new ArrayList<DiaDiemDTO>();
    String urltinh= config.domain +"android/getdiadiem.php";
    public List<DiaDiemDTO> LoadLoaiMonSpiner(){
        listLM.clear();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urltinh, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                       try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                DiaDiemDTO product = new DiaDiemDTO();
                                product.setMadiadiem(item.getString("madiadiem"));
                                product.setTendiadiem(item.getString("tendiadiem"));
                                product.setMatinh(item.getString("matinh"));
                                product.setMahuyen(item.getString("mahuyen"));
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
        DiaDiemAdapter diaDiemAdapter = new DiaDiemAdapter(getContext(), (ArrayList<DiaDiemDTO>) listLM);

        if (customSpinner != null) {
            customSpinner.setAdapter(diaDiemAdapter);
            customSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    DiaDiemDTO items = (DiaDiemDTO) parent.getSelectedItem();
                    matinh=items.getMatinh();
                    mahuyen=items.getMahuyen();
                    try{
                        getbd(matinh,mahuyen);
                    }
                    catch (Exception ex)
                    {

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }




}
