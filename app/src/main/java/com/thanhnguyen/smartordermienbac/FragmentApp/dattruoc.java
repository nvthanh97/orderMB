package com.thanhnguyen.smartordermienbac.FragmentApp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.thanhnguyen.smartordermienbac.R;
import com.thanhnguyen.smartordermienbac.config;

import java.util.HashMap;
import java.util.Map;

public class dattruoc extends AppCompatActivity implements View.OnClickListener {
    String sdt;
    Button btngoi, btnnhan;
    String manh,url;
    TextView ttsdt;
    EditText ednd;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.dattruoc);
        btngoi=findViewById(R.id.btngoidt);
        btnnhan=findViewById(R.id.bntnhannd);
        ednd=findViewById(R.id.edndg);
        ttsdt=findViewById(R.id.ttsdt);
        url= config.domain+"android/getsdt.php";
        super.onCreate(savedInstanceState);
        Intent intent= getIntent();
        manh=intent.getStringExtra("manh");
        getsdt(url+"?manh="+manh);
        btngoi.setOnClickListener(this);
        btnnhan.setOnClickListener(this);
    }
    public void getsdt(String url) {
            RequestQueue requestQueue = Volley.newRequestQueue(dattruoc.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Toast.makeText(dattruoc.this, response.toString(), Toast.LENGTH_LONG).show();
                            sdt=response.trim().toString();
                            ttsdt.setText(sdt);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(dattruoc.this, "Kết nối máy chủ thất bại!", Toast.LENGTH_SHORT).show();

                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("manh",manh);
                    return params;
                }
            };

            requestQueue.add(stringRequest);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = null;
        switch (id) {

            case R.id.btngoidt:
                //Toast.makeText(this, "goi dien"+ sdt, Toast.LENGTH_SHORT).show();
                String phoneNo = sdt;
                if (!TextUtils.isEmpty(phoneNo)) {
                    String dial = "tel:" + phoneNo;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                } else {

                }
                break;

            case R.id.bntnhannd:
                String phoneNo1 = ttsdt.getText().toString();
                String message = "Tôi muốn đặt chỗ trước: " + ednd.getText().toString();
                if (!TextUtils.isEmpty(message) && !TextUtils.isEmpty(phoneNo1)) {
                    Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNo1));
                    smsIntent.putExtra("sms_body", message);
                    startActivity(smsIntent);
                }

                break;
        }

    }
}
