
package com.thanhnguyen.smartordermienbac;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.thanhnguyen.smartordermienbac.DAO.LoaiMonAnDAO;

import java.util.HashMap;
import java.util.Map;

import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MANH;
import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MyPREFERENCES;

public class themdonvitinh extends AppCompatActivity implements View.OnClickListener {

    StorageReference mData;
    FirebaseStorage storage= FirebaseStorage.getInstance();
    Button btnDongYThemLoaiThucDon, btnThoatThemMonAn;
    EditText edTenLoai;
    LoaiMonAnDAO loaiMonAnDAO;
    SharedPreferences sharedpreferences;
    String manh;
    String url= config.domain +"android/themdonvitinh.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themdonvitinh);
        mData=FirebaseStorage.getInstance().getReference();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        manh= sharedpreferences.getString(MANH, "");

        //final StorageReference storageReference= storage.getReferenceFromUrl();
        loaiMonAnDAO = new LoaiMonAnDAO(this);
        btnDongYThemLoaiThucDon = (Button) findViewById(R.id.btnDongYThemLoaiThucDon);
        btnThoatThemMonAn = (Button) findViewById(R.id.btnDongYThemLoaiThucDon);
        edTenLoai = (EditText) findViewById(R.id.edThemLoaiThucDon);
        btnDongYThemLoaiThucDon.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        String sTenLoaiThucDon = edTenLoai.getText().toString();
        if(sTenLoaiThucDon != null || sTenLoaiThucDon.equals("")){
            themloai(url, edTenLoai.getText().toString());
            Log.d("thanhnv"," Tendonvi: "+ edTenLoai.toString());
            boolean kiemtra = loaiMonAnDAO.ThemLoaiMonAn(sTenLoaiThucDon);
            Intent iDuLieu = new Intent();
            iDuLieu.putExtra("kiemtraloaithucdon",kiemtra);
            setResult(Activity.RESULT_OK,iDuLieu);
            finish();
        }else{
            Toast.makeText(this,"Vui lòng nhập đầy đủ thông tin!",Toast.LENGTH_SHORT).show();
        }
    }
    public void themloai(String url, String tendonvi) {
        if(edTenLoai.length()!=0){
            RequestQueue requestQueue = Volley.newRequestQueue(themdonvitinh.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("thanhnv", response.toString());
                            if (response.trim().equals("true")){
                            }
                            else{
                                Toast.makeText(themdonvitinh.this, "Đơn vị tính này đã có rồi mà!", Toast.LENGTH_LONG).show();

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("thanhnv", error.toString());
                            Toast.makeText(themdonvitinh.this, "Kết nối máy chủ lỗi!" + error.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("ten",tendonvi);
                    //Toast.makeText(themdonvitinh.this, edTenLoai.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                    params.put("manh",manh);
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        }else {
            Toast.makeText(themdonvitinh.this,"Vui lòng nhập đầy đủ thông tin!",Toast.LENGTH_SHORT).show();
        }

    }
}
