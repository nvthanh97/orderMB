package com.thanhnguyen.smartordermienbac.Func;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
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
import com.thanhnguyen.smartordermienbac.CustomAdapter.Custom_Adapter_donvitinh;
import com.thanhnguyen.smartordermienbac.DAO.LoaiMonAnDAO;
import com.thanhnguyen.smartordermienbac.DAO.MonAnDAO;
import com.thanhnguyen.smartordermienbac.DTO.DonViDTO;
import com.thanhnguyen.smartordermienbac.DTO.LoaiMonAnDTO;
import com.thanhnguyen.smartordermienbac.R;
import com.thanhnguyen.smartordermienbac.config;
import com.thanhnguyen.smartordermienbac.nhapnguyenlieu;
import com.thanhnguyen.smartordermienbac.themdonvitinh;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MANH;
import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MyPREFERENCES;

public class themnguyenlieu extends AppCompatActivity implements View.OnClickListener {
    public static int REQUEST_CODE_THEMLOAITHUCDON = 113;
    public static int REQUEST_CODE_MOHINH = 123;
    ImageButton imThemLoaiThucDon;
    public String calogy="a";
    Spinner spinLoaiThucDon;
    String donvitinh;
    TextView txtTieuDe;
    LoaiMonAnDAO loaiMonAnDAO;
    MonAnDAO monAnDAO;
    public String maloai="0";
    SharedPreferences sharedpreferences;
    String manh;
    public String tenloai="";
    public int n;
    List<LoaiMonAnDTO> loaiMonAnDTOs;
    AdapterHienThiLoaiMonAn adapterHienThiLoaiMonAn;
    ImageView imHinhThucDon;
    Button btnDongYThemMonAn, btnThoatThemMonAn;
    String sDuongDanHinh;
    EditText edTenMonAn,edGiaTien;
    String urlgetml= config.domain +"android/gettendonvi.php";
    String urlupanh= config.domain + "android/themhinhloaimon.php";
    String URL_GET_PRODUCT=config.domain +"android/gettendonvi.php";
    final List<String> list = new ArrayList<String>();

    public void get(String url) {
        if(tenloai.length()!=0){
            RequestQueue requestQueue = Volley.newRequestQueue(themnguyenlieu.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            maloai="";
                            //Toast.makeText(themnguyenlieu.this, " lay ma ve:"+response, Toast.LENGTH_SHORT).show();
                            maloai=response;
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(themnguyenlieu.this, "Kết nối sever thất bại! " + error.toString(), Toast.LENGTH_SHORT).show();
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
            //Toast.makeText(themnguyenlieu.this,"Chưa lấy được mã loại",Toast.LENGTH_SHORT).show();
        }

    }


    public void Themhinhloaimon()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(themnguyenlieu.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlupanh,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(themnguyenlieu.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(themnguyenlieu.this, "er"+ error, Toast.LENGTH_SHORT).show();

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

        Custom_Adapter_donvitinh custom_adapter_lh= new Custom_Adapter_donvitinh(this, (ArrayList<DonViDTO>) listLM);

        if (spinLoaiThucDon != null) {
            spinLoaiThucDon.setAdapter(custom_adapter_lh);
            spinLoaiThucDon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    DonViDTO items = (DonViDTO) parent.getSelectedItem();
                    tenloai=items.getTen();
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
            mang[i]=list.get(i);
        }

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, mang);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinLoaiThucDon.setAdapter(aa);
        spinLoaiThucDon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenloai=parent.getItemAtPosition(position).toString();
                donvitinh=listLM.get(position).toString();

                get(urlgetml+"?manh="+manh);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Toast.makeText(themnguyenlieu.this, "da vao day", Toast.LENGTH_SHORT).show();
                show();

            }
        });
    }
    @Override
    public <T extends View> T findViewById(int id) {
        return super.findViewById(id);
    }
    String url=config.domain +"android/themnguyenlieu.php";
    DatabaseReference mData;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    public List<DonViDTO> listLM = new ArrayList<DonViDTO>();

    public List<DonViDTO> LoadLoaiMonSpiner(){
        listLM.clear();
        maloai="";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_GET_PRODUCT+"?manh="+manh, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("thanhnv", response.toString());
                        //Toast.makeText(themnguyenlieu.this, response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            //Toast.makeText(themnguyenlieu.this, response.toString(), Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                DonViDTO product = new DonViDTO();
                                product.setId(item.getInt("id"));
                                product.setTen(item.getString("ten"));
                                //calogy+=item.getString("tenloai")+ " - ";
                                //Toast.makeText(themnguyenlieu.this, "dmm>>>"+ item.getString("tenloai"), Toast.LENGTH_LONG).show();

                                listLM.add(product);
                            }
                            list.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                list.add(item.getString("ten"));
                            }
                           // get(urlgetml+"?manh="+manh);
                            n=list.size();
                            Loadloaihinh();
                            //show();


                        } catch (Exception ex) {
                            Log.d("thanhnv", ex.toString());
                            //Toast.makeText(themnguyenlieu.this, ""+ ex.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("thanhnv", error.toString());
                        //Toast.makeText(themnguyenlieu.this, ""+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(themnguyenlieu.this);
        requestQueue.add(request);
        return listLM;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themnguyenlieu);
        mData= FirebaseDatabase.getInstance().getReference();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        manh= sharedpreferences.getString(MANH, "");
        LoadLoaiMonSpiner();

//        final StorageReference reference=storage.getReferenceFromUrl("https://smartorder-13eb1.firebaseio.com/");

        loaiMonAnDAO = new LoaiMonAnDAO(this);
        monAnDAO = new MonAnDAO(this);

        imThemLoaiThucDon = (ImageButton) findViewById(R.id.imThemLoaiThucDon);
        spinLoaiThucDon = (Spinner) findViewById(R.id.spinDVT);
        imHinhThucDon = (ImageView) findViewById(R.id.imHinhThucDon);
        btnDongYThemMonAn = (Button) findViewById(R.id.btnDongYThemMonAn);
        btnThoatThemMonAn = (Button) findViewById(R.id.btnThoatThemMonAn);
        edTenMonAn = (EditText) findViewById(R.id.edThemTenMonAn);
        edGiaTien = (EditText) findViewById(R.id.edThemGiaTien);
        txtTieuDe= findViewById(R.id.txttd);
        imThemLoaiThucDon.setOnClickListener(this);
        btnDongYThemMonAn.setOnClickListener(this);
        btnThoatThemMonAn.setOnClickListener(this);

        spinLoaiThucDon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(themnguyenlieu.this, "Xoa", Toast.LENGTH_SHORT).show();
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
        adapterHienThiLoaiMonAn = new AdapterHienThiLoaiMonAn(themnguyenlieu.this,R.layout.custom_layout_spinloaithucdon,loaiMonAnDTOs);
        spinLoaiThucDon.setAdapter(adapterHienThiLoaiMonAn);
        spinLoaiThucDon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String xx  = String.valueOf(parent.getAdapter().getItem(position));
                Toast.makeText(themnguyenlieu.this, "Ten loai"+ xx, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }*/

    public void themthucdon(String url, String donvitinh) {
        if(edTenMonAn.length()!=0 && edGiaTien.length()!=0){
            RequestQueue requestQueue = Volley.newRequestQueue(themnguyenlieu.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("thanhnv", response.toString());
                            if (response.trim().equals("true")){
                                new AlertDialog.Builder(themnguyenlieu.this)
                                        .setTitle("Xong!")
                                        .setMessage("Đã thêm")
                                        .setNegativeButton("Xong", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i= new Intent(themnguyenlieu.this, nhapnguyenlieu.class);
                                                i.putExtra("url",config.domainmienbac);
                                                i.putExtra("name","Hướng dẫn sử dụng");
                                                startActivity(i);
                                            }
                                        })
                                        .setPositiveButton("Thêm tiếp", new DialogInterface.OnClickListener()
                                        {
                                            public void onClick(DialogInterface dialog, int id)
                                            {
                                                Intent i= new Intent(themnguyenlieu.this, themnguyenlieu.class);
                                                startActivity(i);
                                            }
                                        })

                                        // A null listener allows the button to dismiss the dialog and take no further action.
                                        .setIcon(R.drawable.notebook)
                                        .show();
                                //Toast.makeText(themnguyenlieu.this, "Thêm món thành công!", Toast.LENGTH_LONG).show();
                            }
                            else if(response.trim().equals("already"))
                            {
                                Toast.makeText(themnguyenlieu.this, "Đã có món ăn này rồi! Vui lòng chọn tên khác", Toast.LENGTH_LONG).show();

                            }
                            else{
                                //Toast.makeText(themnguyenlieu.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("thanhnv", error.toString());
                            Toast.makeText(themnguyenlieu.this, "Lỗi "+ error, Toast.LENGTH_SHORT).show();

                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("ten",edTenMonAn.getText().toString().trim());
                    params.put("dongia",edGiaTien.getText().toString().trim());
                    params.put("donvitinh",donvitinh);
                    params.put("manh",manh);
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        }else {
            Toast.makeText(themnguyenlieu.this,"Vui lòng nhập đầy đủ thông tin!",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.imThemLoaiThucDon:
                Intent iThemLoaiMonAn = new Intent(themnguyenlieu.this, themdonvitinh.class);
                startActivityForResult(iThemLoaiMonAn,REQUEST_CODE_THEMLOAITHUCDON);
                break;



            case R.id.btnDongYThemMonAn:
                int vitri = spinLoaiThucDon.getSelectedItemPosition();
                final  String tenmonan = edTenMonAn.getText().toString();
                final String giatien = edGiaTien.getText().toString();


                final String donvitinh1 = spinLoaiThucDon.getSelectedItem().toString();


                if (spinLoaiThucDon.getSelectedItem() == null) {

                    Log.d("thanhnv", " dvtnull: " + donvitinh1);
                }

                Log.d("thanhnv", " dvt: " + donvitinh1);
                Log.d("thanhnv", " dvt0: " + tenloai);

                if(tenmonan != null && giatien != null && !tenmonan.equals("") && !giatien.equals("") ){


                        themthucdon(url, tenloai);
                        //Themhinhloaimon();
                        //Toast.makeText(this,getResources().getString(R.string.themthanhcong),Toast.LENGTH_SHORT).show();


                }else{
                    Toast.makeText(this,getResources().getString(R.string.loithemmonan),Toast.LENGTH_SHORT).show();
                }


                ;break;

            case R.id.btnThoatThemMonAn:
                Intent intent = new Intent(themnguyenlieu.this, nhapnguyenlieu.class);
                startActivity(intent);
                break;
        }
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
                        Toast.makeText(themnguyenlieu.this, "Loi upload hinh", Toast.LENGTH_SHORT).show();
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
                                                //Toast.makeText(themnguyenlieu.this, imageUrl, Toast.LENGTH_SHORT).show();
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

