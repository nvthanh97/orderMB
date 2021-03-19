package com.thanhnguyen.smartordermienbac;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.thanhnguyen.smartordermienbac.DTO.goicuocDTO;
import com.thanhnguyen.smartordermienbac.Func.dangnhap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class mua_goi_cuoc extends AppCompatActivity {
    List<goicuocDTO> listNew;
    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";
    EditText edMaNH, edSoThang, edDiachinhanhang, edSDTnhanhang, edsoluongmayin;
    TextView tv_giagoi, tv_lenhchuyentien,txt1,txt2,txt3,txt4,txt5,txt6,txt69, edTongTien, tv_txtdiachinhanhang,txtsdtnhanhang, txtsoluongmayin;
    String tienmotthang;
    RadioButton rdBasic, rdStandar, rdPremium; Button btnTM, btnXacNhanDaChuyen;
    String manh, sdthotro;
    RelativeLayout line69, line_2x1;
    boolean check=false;
    CheckBox cbmuamayin;
    Long soluongmayinBL=1l;
    public String sdtnhanhang=" ";
    public String diachinhanhang="Không mua máy in";
    String ngayThang;
    boolean load=false;
    public String basic, stander, premium, giamayin;
    ImageButton imgCopy, imgCopySTK;
    Date today;
    String ngayHetHan;
    public List<goicuocDTO> listLM = new ArrayList<goicuocDTO>();
    String URL_GET_PRODUCT= config.domain +"android/getgoicuoc.php";
    String url=config.domain +"android/khachmuagoicuoc.php";
    public void updateServer(String url) {
        if (edMaNH.length() != 0 && edSoThang.length() != 0) {
            RequestQueue requestQueue = Volley.newRequestQueue(mua_goi_cuoc.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.trim().equals("true")) {
                                Toast.makeText(mua_goi_cuoc.this, "Hoàn thành! Đã tạo lệnh chờ trên server", Toast.LENGTH_LONG).show();
                                new AlertDialog.Builder(mua_goi_cuoc.this)
                                        .setTitle("Xác nhận thành công!")
                                        .setIcon(R.drawable.complete)
                                        .setMessage("Hãy chuẩn bị số tiền tương ứng với gói đã mua! Nhân viên của chúng tôi sẽ liên hệ với bạn trong thời gian sớm nhất")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(mua_goi_cuoc.this, dangnhap.class);
                                                startActivity(intent);
                                            }
                                        }).show();

                            } else {
                                Toast.makeText(mua_goi_cuoc.this, "Lỗi!" + response, Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(mua_goi_cuoc.this, "Lỗi hệ thống" + error, Toast.LENGTH_LONG).show();

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("manh", edMaNH.getText().toString().trim());
                    params.put("sothang", edSoThang.getText().toString().trim());
                    params.put("sotien", edTongTien.getText().toString());
                    //params.put("machuyentien", txt3.getText().toString());
                    params.put("machuyentien", "LH khách");
                    params.put("ngaymua", ngayThang);
                    params.put("ngayhethan", ngayHetHan);
                    params.put("kichhoat", "false");
                    params.put("sdt", sdtnhanhang);
                    params.put("diachi", diachinhanhang);
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(mua_goi_cuoc.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mua_goi_cuoc);
        Intent intent = getIntent();
        manh = intent.getStringExtra("manh");
        edMaNH= findViewById(R.id.edMaNH);
        edMaNH.setText(manh);
        txt69=findViewById(R.id.txt69);
        edSoThang= findViewById(R.id.edSoThang);
        rdBasic= findViewById(R.id.gc_basic);
        rdStandar= findViewById(R.id.gc_stander);
        imgCopy= findViewById(R.id.btn_copy1);
        imgCopySTK= findViewById(R.id.btn_copy0);
        cbmuamayin=findViewById(R.id.cb_muamayin);
        edDiachinhanhang= findViewById(R.id.tv_diachinhanhang);
        edSDTnhanhang= findViewById(R.id.ed_sdtnhanhang);
        line69= findViewById(R.id.line_269);
        line_2x1=findViewById(R.id.line_2x1);

        txtsdtnhanhang=findViewById(R.id.tv_txtsdtnhanhang);
        tv_txtdiachinhanhang=findViewById(R.id.tv_txtdiachinhanhang);
        txtsoluongmayin=findViewById(R.id.tv_soluongmay);
        edsoluongmayin=findViewById(R.id.ed_soluongmay);

        btnXacNhanDaChuyen= findViewById(R.id.btn_xacnhanchuyentien);
        btnXacNhanDaChuyen.setVisibility(View.GONE);
        imgCopy.setVisibility(View.GONE);
        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);
        txt3=findViewById(R.id.txt3);
        txt4=findViewById(R.id.txt4);
        txt5=findViewById(R.id.txt5);
        txt6=findViewById(R.id.txt6);
        txt1.setVisibility(View.INVISIBLE);
        txt2.setVisibility(View.INVISIBLE);
        txt3.setVisibility(View.INVISIBLE);
        txt4.setVisibility(View.INVISIBLE);
        txt5.setVisibility(View.INVISIBLE);
        txt6.setVisibility(View.INVISIBLE);
        imgCopySTK.setVisibility(View.INVISIBLE);

        tv_lenhchuyentien= findViewById(R.id.lenhchuyentien);
        btnTM=findViewById(R.id.btn_taoma);
        edTongTien= findViewById(R.id.edtongtien);
        rdPremium= findViewById(R.id.gc_premium);
        tv_giagoi=findViewById(R.id.tv_gia);

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        dateFormatter.setLenient(false);
        today= new Date();
        ngayThang= dateFormatter.format(today);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_GET_PRODUCT, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                goicuocDTO product = new goicuocDTO();
                                product.setId(item.getInt("id"));
                                product.setTengoi(item.getString("tengoi"));
                                product.setGiagoi(item.getString("giagoi"));
                                listLM.add(product);
                            }
                            listNew=listLM;
                            basic=listNew.get(0).getGiagoi();
                            stander= listNew.get(1).getGiagoi();
                            premium=listNew.get(2).getGiagoi();
                            giamayin=listNew.get(3).getGiagoi();
                            sdthotro=listNew.get(5).getTengoi();

                            Log.d("thanhnv",listLM.get(0).getGiagoi());
                            Log.d("thanhnv",listLM.get(1).getGiagoi());
                            Log.d("thanhnv",listLM.get(2).getGiagoi());
                            Log.d("thanhnv",listLM.get(3).getGiagoi());
                            load=true;
                        } catch (Exception ex) {
                            Toast.makeText(mua_goi_cuoc.this, ""+ ex.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mua_goi_cuoc.this, ""+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(mua_goi_cuoc.this);
        requestQueue.add(request);
        cbmuamayin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if( !isChecked)
                {
                    cbmuamayin.setText("Mua kèm máy in bluetooth");
                    txtsdtnhanhang.setVisibility(View.GONE);
                    tv_txtdiachinhanhang.setVisibility(View.GONE);
                    edDiachinhanhang.setVisibility(View.GONE);
                    edSDTnhanhang.setVisibility(View.GONE);
                    line69.setVisibility(View.GONE);
                    txtsoluongmayin.setVisibility(View.GONE);
                    edsoluongmayin.setVisibility(View.GONE);
                    line_2x1.setVisibility(View.GONE);
                }
                if (isChecked)
                {
                    Locale localeEN1 = new Locale("en", "EN");
                    NumberFormat en1 = NumberFormat.getInstance(localeEN1);
                    String c= en1.format(Long.parseLong(giamayin)) + " vnd/máy";
                    cbmuamayin.setText("Mua kèm máy in bluetooth (Giá: "+c +")\nMiễn phí vận chuyển toàn quốc");
                    txtsdtnhanhang.setVisibility(View.VISIBLE);
                    tv_txtdiachinhanhang.setVisibility(View.VISIBLE);
                    edDiachinhanhang.setVisibility(View.VISIBLE);
                    edSDTnhanhang.setVisibility(View.VISIBLE);
                    line69.setVisibility(View.VISIBLE);
                    txtsoluongmayin.setVisibility(View.VISIBLE);
                    edsoluongmayin.setVisibility(View.VISIBLE);
                    line_2x1.setVisibility(View.VISIBLE);
                }
            }
        });
        try{
            rdBasic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try{
                        whatRD();
                    } catch (Exception ex)
                    {

                    }
                }
            });
            rdStandar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try{
                        whatRD();
                    } catch (Exception ex)
                    {

                    }
                }
            });

            rdPremium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try{
                        whatRD();
                    } catch (Exception ex)
                    {

                    }
                }
            });
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "Hệ thống cần cập nhật khuyến mãi mới từ server! bạn đã bật 4g/wifi chưa?", Toast.LENGTH_SHORT).show();
        }

        btnXacNhanDaChuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sdtnhanhang=edSDTnhanhang.getText().toString();
                diachinhanhang=edDiachinhanhang.getText().toString();
                updateServer(url);


            }
        });
        imgCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClipboard(mua_goi_cuoc.this, txt3.getText().toString());
            }
        });
        imgCopySTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClipboard(mua_goi_cuoc.this, txt6.getText().toString());
            }
        });
        btnTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    if( edMaNH.getText().toString().length()<1 )
                    {
                        Toast.makeText(mua_goi_cuoc.this, "Chưa nhập mã nhà hàng/quán!", Toast.LENGTH_SHORT).show();
                    }
                    else if (cbmuamayin.isChecked()==false)
                    {
                        Long kq;
                        Long soThang= Long.parseLong(edSoThang.getText().toString());
                        Long tien1thang= Long.parseLong(tienmotthang);
                        if( soThang>6)
                        {
                            kq= soThang*tien1thang -200000;
                        }
                        else
                        {
                            kq= soThang*tien1thang;
                        }
                        Locale localeEN = new Locale("en", "EN");
                        NumberFormat en = NumberFormat.getInstance(localeEN);
                        String sum= en.format(kq) + " vnd";
                        edTongTien.setText(sum);
                        //chuyen tien
                        String random=getRandomString(6);
                        //tv_lenhchuyentien.setText("Chuyển số tiền: "+sum +" tới ");
                       /* txt1.setVisibility(View.VISIBLE);
                        txt2.setVisibility(View.VISIBLE);
                        txt3.setVisibility(View.VISIBLE);
                        txt4.setVisibility(View.VISIBLE);
                        txt5.setVisibility(View.VISIBLE);
                        txt6.setVisibility(View.VISIBLE);
                        imgCopySTK.setVisibility(View.VISIBLE);*/
                        txt4.setVisibility(View.VISIBLE);
                        txt3.setText(edMaNH.getText().toString()+"_"+ random);
                        btnTM.setVisibility(View.GONE);
                        btnXacNhanDaChuyen.setVisibility(View.VISIBLE);
                        edSoThang.setEnabled(false);
                        rdBasic.setEnabled(false);
                        rdPremium.setEnabled(false);
                        rdStandar.setEnabled(false);
                        imgCopy.setVisibility(View.VISIBLE);
                        btnXacNhanDaChuyen.setFocusable(true);
                        cbmuamayin.setEnabled(false);
                        //tinh ngay het han
                        ngayHetHan= TinhNgayHetHan(soThang);
                        txt69.setVisibility(View.VISIBLE);
                        txt69.setText("Hotline hỗ trợ: " + sdthotro);
                        txtsoluongmayin.setEnabled(false);
                    }
                    else if (cbmuamayin.isChecked()==true)
                    {
                        try{
                            soluongmayinBL =Long.parseLong(edsoluongmayin.getText().toString());
                        }
                        catch (Exception ex)
                        {
                            Toast.makeText(mua_goi_cuoc.this, "Số lượng máy in không hợp lệ! Hệ thống đã đặt giá trị mặc định là 1", Toast.LENGTH_SHORT).show();
                        }
                        Long kq = 0l;

                        Long soThang= Long.parseLong(edSoThang.getText().toString());
                        Long tien1thang= Long.parseLong(tienmotthang);

                        if(soThang>6 && soThang<=12)
                        {
                            kq= (Long.parseLong(giamayin)*soluongmayinBL) + (soThang*tien1thang) -200000;
                        }
                        else if( soThang>12)
                        {
                            kq= (Long.parseLong(giamayin)*soluongmayinBL) + (soThang*tien1thang) -500000;
                        }
                        else if( soThang<=6)
                        {
                            kq= (Long.parseLong(giamayin)*soluongmayinBL) + soThang*tien1thang;
                        }

                        Locale localeEN = new Locale("en", "EN");
                        NumberFormat en = NumberFormat.getInstance(localeEN);
                        String sum= en.format(kq) + " vnd";
                        edTongTien.setText(sum);
                        //chuyen tien
                        String random=getRandomString(6);
                        //tv_lenhchuyentien.setText("Chuyển số tiền: "+sum +" tới ");
                        /*txt1.setVisibility(View.VISIBLE);
                        txt2.setVisibility(View.VISIBLE);
                        txt3.setVisibility(View.VISIBLE);
                        txt4.setVisibility(View.VISIBLE);
                        txt5.setVisibility(View.VISIBLE);
                        txt6.setVisibility(View.VISIBLE);
                        imgCopySTK.setVisibility(View.VISIBLE);*/
                        txt4.setVisibility(View.VISIBLE);
                        //txt3.setText(edMaNH.getText().toString()+"_"+ random);
                        btnTM.setVisibility(View.GONE);
                        btnXacNhanDaChuyen.setVisibility(View.VISIBLE);
                        edSoThang.setEnabled(false);
                        rdBasic.setEnabled(false);
                        rdPremium.setEnabled(false);
                        rdStandar.setEnabled(false);
                        imgCopy.setVisibility(View.VISIBLE);
                        btnXacNhanDaChuyen.setFocusable(true);
                        cbmuamayin.setEnabled(false);
                        //tinh ngay het han
                        ngayHetHan= TinhNgayHetHan(soThang);
                        txt69.setVisibility(View.VISIBLE);
                        txt69.setText("Chi tiết liên hệ: " + sdthotro);
                    }



                }
                catch (Exception ex)
                {
                    Toast.makeText(mua_goi_cuoc.this, "Vui lòng mua tròn tháng!", Toast.LENGTH_LONG).show();
                }


            }
        });
    }
    public void whatRD()
    {
        if( rdBasic.isChecked())
        {
            Locale localeEN = new Locale("en", "EN");
            NumberFormat en = NumberFormat.getInstance(localeEN);
            String a= en.format(Long.parseLong(basic)) + " vnd/tháng";
            tv_giagoi.setText(a);
            tienmotthang=basic;
        }
        if (rdStandar.isChecked())
        {
            Locale localeEN = new Locale("en", "EN");
            NumberFormat en = NumberFormat.getInstance(localeEN);
            String b= en.format(Long.parseLong(stander)) + " vnd/tháng";
            tv_giagoi.setText(b);
            tienmotthang=stander;
        }
        if(rdPremium.isChecked())
        {
            Locale localeEN = new Locale("en", "EN");
            NumberFormat en = NumberFormat.getInstance(localeEN);
            String c= en.format(Long.parseLong(premium)) + " vnd/tháng";
            tv_giagoi.setText(c);
            tienmotthang=premium;
        }
    }
    private static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }
    private void setClipboard(Context context, String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Đã copy vào bộ nhớ tạm!", text);
            clipboard.setPrimaryClip(clip);
        }
    }
    public  String TinhNgayHetHan(long soThang) {
        String dt = ngayThang;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, (int)(soThang*30));  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String output = sdf1.format(c.getTime());
        return output;
    }

}
