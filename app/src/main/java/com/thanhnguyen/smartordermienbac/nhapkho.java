package com.thanhnguyen.smartordermienbac;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.thanhnguyen.smartordermienbac.CustomAdapter.AdapterHienThiLoaiMonAn;
import com.thanhnguyen.smartordermienbac.CustomAdapter.Custom_Adapter_NguyenLieuNew;
import com.thanhnguyen.smartordermienbac.DAO.LoaiMonAnDAO;
import com.thanhnguyen.smartordermienbac.DAO.MonAnDAO;
import com.thanhnguyen.smartordermienbac.DTO.LoaiMonAnDTO;
import com.thanhnguyen.smartordermienbac.DTO.NguyenLieuNewDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MANH;
import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MyPREFERENCES;

public class nhapkho extends AppCompatActivity implements View.OnClickListener {
    public static int REQUEST_CODE_THEMLOAITHUCDON = 113;
    public static int REQUEST_CODE_MOHINH = 123;
    ImageButton imThemLoaiThucDon;
    public String calogy="a";
    Spinner spinLoaiThucDon;
    TextView txtTieuDe;
    LoaiMonAnDAO loaiMonAnDAO;
    MonAnDAO monAnDAO;
    public String maloai="0";
    String dongiaold;
    SharedPreferences sharedpreferences;
    String manh;

    public String tenloai="";
    public int n;
    List<LoaiMonAnDTO> loaiMonAnDTOs;
    AdapterHienThiLoaiMonAn adapterHienThiLoaiMonAn;
    ImageView imHinhThucDon;
    Button btnDongYThemMonAn, btnThoatThemMonAn;
    String sDuongDanHinh;
    EditText edTenMonAn,edGiaTien, edDonGia, edDVT,edSoLuong, edTongtien;
    String urlgetml= config.domain +"android/getidmon.php";
    String urlupanh= config.domain + "android/themhinhloaimon.php";
//    final List<String> list = new ArrayList<String>();
//    final List<String> list1 = new ArrayList<String>();
//    final List<String> list3 = new ArrayList<String>();


    public void get(String url) {
        if(tenloai.length()!=0){
            RequestQueue requestQueue = Volley.newRequestQueue(nhapkho.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            maloai="";
                            //Toast.makeText(nhapkho.this, " lay ma ve:"+response, Toast.LENGTH_SHORT).show();
                            maloai=response;

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(nhapkho.this, "Kết nối sever thất bại! " + error.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("tenloai",tenloai);
                    params.put("maloai", maloai);
                    params.put("manh",manh);
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        }else {
            //Toast.makeText(nhapkho.this,"Chưa lấy được mã loại",Toast.LENGTH_SHORT).show();
        }

    }


    public void Themhinhloaimon()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(nhapkho.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlupanh,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(nhapkho.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(nhapkho.this, "er"+ error, Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("maloai",maloai);
                params.put("hinhanh",sDuongDanHinh);
                params.put("manh",manh);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }



    public void Loadloaihinh()
    {

        Custom_Adapter_NguyenLieuNew custom_adapter_lh= new Custom_Adapter_NguyenLieuNew(this, (ArrayList<NguyenLieuNewDTO>) listLM);

        if (spinLoaiThucDon != null) {
            spinLoaiThucDon.setAdapter(custom_adapter_lh);
            spinLoaiThucDon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    tenloai=listLM.get(position).getTen();

                    Locale localeEN = new Locale("en", "EN");
                    NumberFormat en = NumberFormat.getInstance(localeEN);
                    long longNumber = Long.parseLong(listLM.get(position).getDongia());
                    String str1 = en.format(longNumber);
                    edDonGia.setText(str1);
                    edDVT.setText("( "+listLM.get(position).getDonvitinh()+" )");
                    edTongtien.setText("");
                   // Log.d("thanhnv", items.getTen()+ items.getDongia() + items.getDonvitinh());
                    //Log.d("thanhnv", listLM.get(position).getTen()+ listLM.get(position).getDongia() + listLM.get(position).getDonvitinh());


//                    NguyenLieuNewDTO items = (NguyenLieuNewDTO) parent.getSelectedItem();
//                    {
//                        tenloai=items.getTen();
//                        edDonGia.setText(listLM.get(position).getDongia());
//                        edDVT.setText(listLM.get(position).getDonvitinh());
//                        Log.d("thanhnv", items.getTen()+ items.getDongia() + items.getDonvitinh());
//                        Log.d("thanhnv", listLM.get(position).getTen()+ listLM.get(position).getDongia() + listLM.get(position).getDonvitinh());
//                    }



                    //get(urlgetml+"?manh="+manh);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }
    public void show()
    {
        String[] mang= new String[n];
        for( int i=0; i<n; i++)
        {
            //mang[i]=list.get(i);
        }

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, mang);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinLoaiThucDon.setAdapter(aa);
        spinLoaiThucDon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenloai=parent.getItemAtPosition(position).toString();
                edDonGia.setText(listLM.get(position).getDongia());
                dongiaold=listLM.get(position).getDongia();
                edDVT.setText(listLM.get(position).getDonvitinh());
                //thanhnv2
                //get(urlgetml+"?manh="+manh);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
               // Toast.makeText(nhapkho.this, "da vao day", Toast.LENGTH_SHORT).show();
               //show();
            }
        });
    }

    @Override
    public <T extends View> T findViewById(int id) {
        return super.findViewById(id);
    }
    String url=config.domain +"android/nhapkho.php";
    DatabaseReference mData;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    public List<NguyenLieuNewDTO> listLM = new ArrayList<NguyenLieuNewDTO>();
    String URL_GET_PRODUCT=config.domain +"android/gettennguyenlieu.php";
    public List<NguyenLieuNewDTO> LoadLoaiMonSpiner(){
        listLM.clear();
        maloai="";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_GET_PRODUCT+"?manh="+manh, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("thanhnv", response.toString());
                        //Toast.makeText(nhapkho.this, response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            //Toast.makeText(nhapkho.this, response.toString(), Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                NguyenLieuNewDTO product = new NguyenLieuNewDTO();
                                product.setManl(item.getString("manl"));
                                product.setTen(item.getString("ten"));
                                product.setDongia(item.getString("dongia"));
                                product.setDonvitinh(item.getString("donvitinh"));

                                //calogy+=item.getString("tenloai")+ " - ";
                                //Toast.makeText(nhapkho.this, "dmm>>>"+ item.getString("tenloai"), Toast.LENGTH_LONG).show();

                                listLM.add(product);
                            }

//                            list.clear();
//                            for (int i = 0; i < response.length(); i++) {
//                                JSONObject item = response.getJSONObject(i);
//                                list.add(item.getString("ten"));
//                            }
//                            n=list.size();
                            edDonGia.setText(listLM.get(0).getDongia());
                            edDVT.setText(listLM.get(0).getDonvitinh());
                            Loadloaihinh();




//                            list1.clear();
//                            for (int i = 0; i < response.length(); i++) {
//                                JSONObject item = response.getJSONObject(i);
//                                list.add(item.getString("dongia"));
//                            }
//                            list3.clear();
//                            for (int i = 0; i < response.length(); i++) {
//                                JSONObject item = response.getJSONObject(i);
//                                list.add(item.getString("donvitinh"));
//                            }
                            //get(urlgetml+"?manh="+manh);

                            //show();


                        } catch (Exception ex) {
                            Log.d("thanhnv", ex.toString());
                            //Toast.makeText(nhapkho.this, ""+ ex.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(nhapkho.this, ""+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(nhapkho.this);
        requestQueue.add(request);
        return listLM;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhapkho);
        mData= FirebaseDatabase.getInstance().getReference();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        manh= sharedpreferences.getString(MANH, "");
        LoadLoaiMonSpiner();
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);

//        final StorageReference reference=storage.getReferenceFromUrl("https://smartorder-13eb1.firebaseio.com/");

        loaiMonAnDAO = new LoaiMonAnDAO(this);
        monAnDAO = new MonAnDAO(this);

        imThemLoaiThucDon = (ImageButton) findViewById(R.id.imThemLoaiThucDon);
        spinLoaiThucDon = (Spinner) findViewById(R.id.spinLoaiMonAn);
        imHinhThucDon = (ImageView) findViewById(R.id.imHinhThucDon);
        btnDongYThemMonAn = (Button) findViewById(R.id.btnDongYThemMonAn);
        btnThoatThemMonAn = (Button) findViewById(R.id.btnThoatThemMonAn);
        edTenMonAn = (EditText) findViewById(R.id.edThemTenMonAn);
        edGiaTien = (EditText) findViewById(R.id.edThemGiaTien);
        txtTieuDe= findViewById(R.id.txttd);
        edDonGia= findViewById(R.id.edDonGiaNhapKho);
        edDVT= findViewById(R.id.edDVT);
        edSoLuong= findViewById(R.id.edSoLuong);
        edTongtien= findViewById(R.id.edTongtien);
        edSoLuong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                try{
                    Float sum=0.0f;
                    String dg=edDonGia.getText().toString().replaceAll(",", "");
                    //Log.d("thanhnv", dg.toString());
                    long dongia= Long.parseLong(dg);
                    Float soluong= Float.parseFloat(edSoLuong.getText().toString());
                    sum=(float)dongia*soluong;
                    String str1 = en.format(sum);
                    edTongtien.setText(str1);
                }
                catch (Exception e)
                {

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    Float sum=0.0f;
                    String dg=edDonGia.getText().toString().replaceAll(",", "");
                    //Log.d("thanhnv", dg.toString());
                    long dongia= Long.parseLong(dg);
                    Float soluong= Float.parseFloat(edSoLuong.getText().toString());
                    sum=(float)dongia*soluong;
                    String str1 = en.format(sum);
                    edTongtien.setText(str1);
                }
                catch (Exception e)
                {

                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    Float sum=0.0f;
                    String dg=edDonGia.getText().toString().replaceAll(",", "");
                    //Log.d("thanhnv", dg.toString());
                    long dongia= Long.parseLong(dg);
                    Float soluong= Float.parseFloat(edSoLuong.getText().toString());
                    sum=(float)dongia*soluong;
                    String str1 = en.format(sum);
                    edTongtien.setText(str1);
                }
                catch (Exception e)
                {

                }
            }
        });

        btnDongYThemMonAn.setOnClickListener(this);
        btnThoatThemMonAn.setOnClickListener(this);

        spinLoaiThucDon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(nhapkho.this, "Xoa", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        /*spinLoaiThucDon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calogy=parent.getOnItemClickListener().toString();
                ((TextView) view).setTextColor(Color.RED);
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                show();
            }
        });*/

    }




    //spinLoaiThucDon.OnItemClickListener(new )
  /*  private void HienThiSpinnerLoaiMonAn (){
        loaiMonAnDTOs = LoadLoaiMonSpiner();
        adapterHienThiLoaiMonAn = new AdapterHienThiLoaiMonAn(nhapkho.this,R.layout.custom_layout_spinloaithucdon,loaiMonAnDTOs);
        spinLoaiThucDon.setAdapter(adapterHienThiLoaiMonAn);
        spinLoaiThucDon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String xx  = String.valueOf(parent.getAdapter().getItem(position));
                Toast.makeText(nhapkho.this, "Ten loai"+ xx, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }*/

    public void themthucdon(String url, String mal, String dongia, String sl, String tt, String ngaynhap) {
        if(tt.length()!=0 && dongia.length()!=0){
            RequestQueue requestQueue = Volley.newRequestQueue(nhapkho.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.trim().equals("true")){
                                new AlertDialog.Builder(nhapkho.this)
                                        .setTitle("Xong!")
                                        .setMessage("Đã cập nhật nguyên liệu vào kho!")
                                        .setNegativeButton("Xong", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i= new Intent(nhapkho.this, nhapnguyenlieu.class);
                                                startActivity(i);
                                            }
                                        })
                                        .setPositiveButton("Thêm tiếp", new DialogInterface.OnClickListener()
                                        {
                                            public void onClick(DialogInterface dialog, int id)
                                            {
                                                Intent i= new Intent(nhapkho.this, nhapkho.class);
                                                startActivity(i);
                                            }
                                        })
                                        .setIcon(R.drawable.notebook)
                                        .show();
                            //Toast.makeText(nhapkho.this, "Thêm món thành công!", Toast.LENGTH_LONG).show();
                            }
                            else{
                                //Toast.makeText(nhapkho.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(nhapkho.this, "Bạn đã chọn hình cho món ăn chưa? Nhấn lần nữa để xác nhận", Toast.LENGTH_SHORT).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("mal",mal);
                    params.put("dongia",dongia);
                    params.put("soluong",sl);
                    params.put("tongtien",tt);
                    params.put("ngaynhap",ngaynhap);
                    params.put("manh",manh);
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        }else {
            Toast.makeText(nhapkho.this,"Vui lòng nhập đầy đủ thông tin!",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){


            case R.id.btnDongYThemMonAn:
                int vitri = spinLoaiThucDon.getSelectedItemPosition();
                final  String mal = listLM.get(vitri).getManl();
                final  String dongia = listLM.get(vitri).getDongia();
                final  String soluong = edSoLuong.getText().toString();
                final String thanhtien =edTongtien.getText().toString().replaceAll(",", "");


                if(dongia != null && soluong != null && !thanhtien.equals("")){
                    themthucdon(url, mal, dongia, soluong, thanhtien, getDateTime());
                }else{
                    Toast.makeText(this,getResources().getString(R.string.loithemmonan),Toast.LENGTH_SHORT).show();
                }
                ;break;

            case R.id.btnThoatThemMonAn:
                Intent intent = new Intent(nhapkho.this, nhapnguyenlieu.class);
                startActivity(intent);
                break;
        }
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_THEMLOAITHUCDON){
            LoadLoaiMonSpiner();
            show();
            if(resultCode == Activity.RESULT_OK){
                Intent dulieu = data;
                boolean kiemtra = dulieu.getBooleanExtra("kiemtraloaithucdon",false);
                if(kiemtra){
                    Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT).show();
                }
            }
        }else if(REQUEST_CODE_MOHINH == requestCode){
            if(resultCode == Activity.RESULT_OK && data !=null){
                imHinhThucDon.setImageURI(data.getData());
                StorageReference storageRef = storage.getReferenceFromUrl("gs://smartorder-8f077.appspot.com"); //gs://smartorder-13eb1.appspot.com
                Calendar calendar= Calendar.getInstance();
                StorageReference mountainsRef = storageRef.child("image"+ calendar.getTimeInMillis()+ ".png");

                // Get the data from an ImageView as bytes
                Bitmap bitmap = ((BitmapDrawable) imHinhThucDon.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                byte[] dataa = baos.toByteArray();

                final UploadTask uploadTask = mountainsRef.putBytes(dataa);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(nhapkho.this, "Loi upload hinh", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                if (taskSnapshot.getMetadata() != null) {
                                    if (taskSnapshot.getMetadata().getReference() != null) {
                                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String imageUrl = uri.toString();
                                                sDuongDanHinh=imageUrl;
                                                //MonAnDTO monAnDTO=new MonAnDTO(1,1,String.valueOf(edGiaTien.toString()), String.valueOf(edGiaTien.getText()),String.valueOf(imageUrl));
                                                //myRef.setValue(imageUrl);
                                                //Toast.makeText(nhapkho.this, imageUrl, Toast.LENGTH_SHORT).show();
                                                Log.d("cc",imageUrl.toString());
                                            }
                                        });
                                    }
                                }
                            }});
                    }
                });
            }
        }
    }
}
