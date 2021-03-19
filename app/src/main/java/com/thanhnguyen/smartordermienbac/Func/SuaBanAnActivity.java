package com.thanhnguyen.smartordermienbac.Func;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.thanhnguyen.smartordermienbac.DAO.BanAnDAO;
import com.thanhnguyen.smartordermienbac.R;
import com.thanhnguyen.smartordermienbac.config;

import java.util.HashMap;
import java.util.Map;

import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MANH;
import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MyPREFERENCES;

public class SuaBanAnActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnDongYSua;
    EditText edSuaTenBan;
    BanAnDAO banAnDAO;
    int maban;
    String tenban;
    SharedPreferences sharedpreferences;
    String manh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_suabanan);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        manh= sharedpreferences.getString(MANH, "");


        btnDongYSua = (Button) findViewById(R.id.btnDongYSuaBanAn);
        edSuaTenBan = (EditText) findViewById(R.id.edSuaTenBanAn);

        banAnDAO = new BanAnDAO(this);

        maban = getIntent().getIntExtra("maban",0);

        btnDongYSua.setOnClickListener(this);
    }


    public void suatenban()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.domain +"android/suatenban.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("true")){
                            Intent intent = new Intent();
                            intent.putExtra("kiemtra",true);
                            setResult(Activity.RESULT_OK,intent);
                            finish();

                        }
                        else{

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(SuaBanAnActivity.this, "Kết nối sever thất bại! " + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("tenban",tenban);
                params.put("maban",String.valueOf(maban));
                params.put("manh",manh);

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {

        tenban = edSuaTenBan.getText().toString();
        if(tenban.trim().equals("") || tenban.trim() != null){
            suatenban();
        }else{
            Toast.makeText(SuaBanAnActivity.this, getResources().getString(R.string.vuilongnhapdulieu), Toast.LENGTH_SHORT).show();
        }
    }
}
