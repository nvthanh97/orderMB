package com.thanhnguyen.smartordermienbac.Func;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thanhnguyen.smartordermienbac.DAO.NhanVienDAO;
import com.thanhnguyen.smartordermienbac.FragmentApp.kichhoat;
import com.thanhnguyen.smartordermienbac.FragmentApp.quenmk;
import com.thanhnguyen.smartordermienbac.R;
import com.thanhnguyen.smartordermienbac.config;
import com.thanhnguyen.smartordermienbac.mua_goi_cuoc;
import com.thanhnguyen.smartordermienbac.read_webview;

import java.util.HashMap;
import java.util.Map;

public class dangnhap extends AppCompatActivity implements OnClickListener {

    Button btnDongYDN,btnDangKyDN;
    EditText edTenDangNhapDN, edMatKhauDN, edMaNH;
    NhanVienDAO nhanVienDAO;
    Button btnquenmk;
    int maquyen;
    boolean isShowPass;
    ImageView imgeye;
    LinearLayout btn_huongdan, btn_phienbankhach, btn_goicuoc, btn_kiemtra;
    RelativeLayout line1, line2, line3;
    CheckBox cbRemember;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USERNAME = "userNameKey";
    public static final String PASS = "passKey";
    public static final String QUYEN = "Quyen";
    public static final String REMEMBER = "remember";
    public static final String MANH = "manh";
    SharedPreferences sharedpreferences;
    FloatingActionButton floatingActionButton;
    String url= config.domain + "android/login.php";
    String urlmaquyen=config.domain + "android/getmaquyen.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhapnew);
        setupUI();
        btnDangKyDN = findViewById(R.id.btndk_dn);
        btnDongYDN =  findViewById(R.id.btndn_dn);
        edTenDangNhapDN =findViewById(R.id.edTendn_dn);
        edMatKhauDN =  findViewById(R.id.edmk_dn);
        line1= findViewById(R.id.line_1);
        line2= findViewById(R.id.line_2);
        line3= findViewById(R.id.line_3);
        btn_goicuoc= findViewById(R.id.btn_goicuoc);
       // btn_huongdan= findViewById(R.id.btn_hdsdold);
        btn_kiemtra=findViewById(R.id.btn_kiemtra);
        btn_phienbankhach= findViewById(R.id.btn_pbkhach);
        cbRemember= findViewById(R.id.ckremember);
        edMaNH= findViewById(R.id.edMaNH);
        btnquenmk= findViewById(R.id.btnQuenmk);
        imgeye= findViewById(R.id.iv_eye);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        edMaNH.setText(sharedpreferences.getString(MANH, ""));
        nhanVienDAO = new NhanVienDAO(this);
        btnDongYDN.setOnClickListener(this);
        btnDangKyDN.setOnClickListener(this);
        btnquenmk.setOnClickListener(this);
        //thanhnv
        btn_phienbankhach.setOnClickListener(this);
        btn_kiemtra.setOnClickListener(this);
        btn_goicuoc.setOnClickListener(this);
        //btn_huongdan.setOnClickListener(this);
        edMaNH.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                line3.setBackgroundColor(getResources().getColor(R.color.blue));
                line2.setBackgroundColor(getResources().getColor(R.color.blue));
                line1.setBackgroundColor(getResources().getColor(R.color.yellow));
            }
            return false;
        });

        edTenDangNhapDN.setOnFocusChangeListener((v, hasFocus) -> {
            line1.setBackgroundColor(getResources().getColor(R.color.blue));
            line2.setBackgroundColor(getResources().getColor(R.color.yellow));
            line3.setBackgroundColor(getResources().getColor(R.color.blue));
        });
        edTenDangNhapDN.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                line2.setBackgroundColor(getResources().getColor(R.color.blue));
                line1.setBackgroundColor(getResources().getColor(R.color.blue));
                line3.setBackgroundColor(getResources().getColor(R.color.yellow));
            }
            return false;
        });
        edMatKhauDN.setOnFocusChangeListener((v, hasFocus) -> {
            line3.setBackgroundColor(getResources().getColor(R.color.yellow));
            line1.setBackgroundColor(getResources().getColor(R.color.blue));
            line2.setBackgroundColor(getResources().getColor(R.color.blue));
        });
        edMatKhauDN.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                line3.setBackgroundColor(getResources().getColor(R.color.yellow));
                line1.setBackgroundColor(getResources().getColor(R.color.blue));
                line2.setBackgroundColor(getResources().getColor(R.color.blue));
            }
            return false;
        });

        imgeye.setOnClickListener(v -> {
            if (!isShowPass) {
                edMatKhauDN.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

            } else {
                edMatKhauDN.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            int pos = edMatKhauDN.getText().length();
            edMatKhauDN.setSelection(pos);
            isShowPass = !isShowPass;
        });
        loadData();
    }

    private void clearData() {
        String a=sharedpreferences.getString(MANH, "");
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        loadData();
    }

    private void setupUI() {

    }

    private void saveData(String username, String Pass,int Quyen, String mnhg) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(USERNAME, username);
        editor.putString(PASS, Pass);
        editor.putInt(QUYEN,Quyen);
        editor.putString(MANH, mnhg);
        editor.putBoolean(REMEMBER,cbRemember.isChecked());
        editor.commit();
    }

    private void loadData() {
        if(sharedpreferences.getBoolean(REMEMBER,false)) {
            edTenDangNhapDN.setText(sharedpreferences.getString(USERNAME, ""));
            edMatKhauDN.setText(sharedpreferences.getString(PASS, ""));
            cbRemember.setChecked(true);
            //login(url);
        }
        else
            cbRemember.setChecked(false);

    }




    private void btnDongY(){
        login(url);
        if (cbRemember.isChecked())
            saveData(edTenDangNhapDN.getText().toString().trim(), edMatKhauDN.getText().toString().trim(),maquyen, edMaNH.getText().toString().trim().toUpperCase());
        else if(!cbRemember.isChecked())
        {
            clearData();
        }

    }

    public void laymaquyen(String url) {
      {
            RequestQueue requestQueue = Volley.newRequestQueue(dangnhap.this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url+"?tendn="+edTenDangNhapDN.getText().toString().trim()+"&manh="+edMaNH.getText().toString().trim().toUpperCase(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            maquyen=Integer.parseInt(response);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(dangnhap.this, "Bạn cần bật 3G/4G hoặc wifi để ứng dụng kết nối tới server!", Toast.LENGTH_LONG).show();

                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("tendn",edTenDangNhapDN.getText().toString().trim());
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        }

    }

    private void btnDangKy(){
        Intent iDangKy = new Intent(dangnhap.this, dangky.class);
        iDangKy.putExtra("landautien",1);
        startActivity(iDangKy);
    }
    public void luumanh(int mq)
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(QUYEN, String.valueOf(mq));
        editor.putString(MANH, edMaNH.getText().toString().trim().toUpperCase());
        editor.commit();
    }
    public void login(String url) {
        final String user=edTenDangNhapDN.getText().toString().trim();
        final String pass=edMatKhauDN.getText().toString().trim();
        final String mnh=edMaNH.getText().toString().trim();
        if (user.contains("'") || user.contains("`") || pass.contains("'") || pass.contains("`")|| mnh.contains("'") || mnh.contains("`"))
        {
            new AlertDialog.Builder(this)
                    .setTitle("Định hack app tui à")
                    .setMessage("Thôi mà ^^")

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
        else
        {
            if(user.length()!=0 && pass.length()!=0 || mnh.length()!=0){
                RequestQueue requestQueue = Volley.newRequestQueue(dangnhap.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    maquyen=Integer.parseInt(response.toString().trim());
                                    //Toast.makeText(dangnhap.this, "maquyen" +maquyen, Toast.LENGTH_SHORT).show();
                                    if(maquyen==1 || maquyen==2)
                                    {
                                        luumanh(maquyen);
                                        Intent iTrangChu = new Intent(dangnhap.this, home.class);
                                        iTrangChu.putExtra("tendn",edTenDangNhapDN.getText().toString());
                                        iTrangChu.putExtra("maquyen",maquyen);
                                        startActivity(iTrangChu);
                                    }
                                    if(maquyen==3)
                                    {
                                        luumanh(maquyen);
                                        Intent iTrangChu = new Intent(dangnhap.this, NhaBepNhanDL.class);
                                        iTrangChu.putExtra("tendn",edTenDangNhapDN.getText().toString());
                                        iTrangChu.putExtra("maquyen",maquyen);
                                        startActivity(iTrangChu);
                                    }
                                    else if(maquyen==5){
                                        Toast.makeText(dangnhap.this, "Tên đăng nhập hoặc mật khẩu không đúng!" , Toast.LENGTH_SHORT).show();
                                        edMatKhauDN.setText("");
                                    }
                                    else if(maquyen==9){
                                        Toast.makeText(dangnhap.this, "Mã nhà hàng này không tồn tại!", Toast.LENGTH_SHORT).show();
                                        edMatKhauDN.setText("");
                                        edMaNH.setText("");
                                        new AlertDialog.Builder(dangnhap.this)
                                                .setTitle("Mã NH/Quán không tồn tại hoặc đã hết hạn dùng thử!")
                                                .setMessage("Nếu bạn là nhân viên liên hệ quản lý để lấy mã. Nếu bạn là QUẢN LÝ nhấn vào nút [Hướng dẫn] hoặc liên hệ cskh@appviet.store để kích hoạt tài khoản")

                                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                                // The dialog is automatically dismissed when a dialog button is clicked.
                                                .setPositiveButton("Nhập lại", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        return;
                                                    }
                                                })
                                                .setNegativeButton("Hướng dẫn", new DialogInterface.OnClickListener()
                                                {
                                                    public void onClick(DialogInterface dialog, int id)
                                                    {
                                                        Intent intent= new Intent(dangnhap.this, kichhoat.class);
                                                        startActivity(intent);
                                                        dialog.cancel();
                                                    }
                                                })

                                                // A null listener allows the button to dismiss the dialog and take no further action.
                                                .setIcon(R.drawable.doicho)
                                                .show();
                                    }
                                    else if(maquyen==6){
                                        Toast.makeText(dangnhap.this, "Nhà hàng/quán của bạn chưa được kích hoạt!", Toast.LENGTH_SHORT).show();
                                        edMatKhauDN.setText("");
                                        new AlertDialog.Builder(dangnhap.this)
                                                .setTitle("Yêu cầu kích hoạt!")
                                                .setMessage("Nhà hàng/quán của bạn chưa mua gói hoặc đã hết thời hạn sử dụng!")

                                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                                // The dialog is automatically dismissed when a dialog button is clicked.
                                                .setNegativeButton("Hướng dẫn", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Intent i= new Intent(dangnhap.this, read_webview.class);
                                                        i.putExtra("url",config.domainmienbac);
                                                        i.putExtra("name","Hướng dẫn sử dụng");
                                                        startActivity(i);
                                                    }
                                                })
                                                .setPositiveButton("Mua/gia hạn gói", new DialogInterface.OnClickListener()
                                                {
                                                    public void onClick(DialogInterface dialog, int id)
                                                    {
                                                        Intent i= new Intent(dangnhap.this, mua_goi_cuoc.class);
                                                        i.putExtra("url",config.domain+"goi-cuoc");
                                                        i.putExtra("name","Gói cước");
                                                        i.putExtra("manh",edMaNH.getText().toString());
                                                        startActivity(i);
                                                    }
                                                })

                                                // A null listener allows the button to dismiss the dialog and take no further action.
                                                .setIcon(R.drawable.notebook)
                                                .show();
                                    }

                                }
                                catch (Exception ex)
                                {
                                    Toast.makeText(dangnhap.this, "Tên đăng nhập hoặc mật khẩu không đúng!"
                                            + ex.toString(), Toast.LENGTH_SHORT).show();
                                    edMatKhauDN.setText("");
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(dangnhap.this, "Bạn cần bật 3G/4G hoặc Wifi để ứng dụng truy xuất dữ liệu từ server!", Toast.LENGTH_SHORT).show();

                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("tendn",user.trim());
                        params.put("mk",pass.trim());
                        params.put("manh", edMaNH.getText().toString().trim());
                        return params;
                    }
                };

                requestQueue.add(stringRequest);
            }else {
                Toast.makeText(dangnhap.this,"Chưa nhập đầy đủ thông tin!",Toast.LENGTH_SHORT).show();
            }
        }



    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){

            case R.id.btndn_dn:
                btnDongY();
                ;break;

            case R.id.btndk_dn:
                btnDangKy();
                ;break;
            case R.id.btnQuenmk:
            {
                Intent iTrangChu = new Intent(dangnhap.this, quenmk.class);
                startActivity(iTrangChu);
                ;break;
            }
            case  R.id.btn_goicuoc:
            {
                Intent i= new Intent(dangnhap.this, mua_goi_cuoc.class);
                i.putExtra("url",config.domain+"goi-cuoc");
                i.putExtra("name","Gói cước");
                i.putExtra("manh",edMaNH.getText().toString());
                startActivity(i);
                ;break;
            }
            /*case  R.id.btn_hdsd:
            {
                Intent i= new Intent(dangnhap.this, read_webview.class);
                i.putExtra("url",config.domain);
                i.putExtra("name","Hướng dẫn sử dụng");
                startActivity(i);
                ;break;

            }*/
            case  R.id.btn_kiemtra:
            {
                Intent i= new Intent(dangnhap.this, read_webview.class);
                i.putExtra("url",config.domain+"goi-cuoc");
                i.putExtra("name","Gói cước");
                i.putExtra("manh",edMaNH.getText().toString());
                startActivity(i);
                ;break;
            }
            case  R.id.btn_pbkhach:
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(config.appchokhach));
                startActivity(browserIntent);
                ;break;
            }
        }
    }
}
