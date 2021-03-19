package com.thanhnguyen.smartordermienbac.Func;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thanhnguyen.smartordermienbac.R;

import java.util.ArrayList;

import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MANH;
import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MyPREFERENCES;

public class NhaBepHienMon extends AppCompatActivity {

    DatabaseReference mData = FirebaseDatabase.getInstance("https://smartorder-8f077.firebaseio.com/").getReference();
    ArrayList<QLMonAnBep> arrayList;
    ListView listViewdsMon;
    QLmonAnBepAdapter qLmonAnBepAdapter;
    ArrayAdapter adapter;
    TextView txtghichu,edchat,txtchat2,txtXacNhan;
    String tendn ="";
    SharedPreferences sharedpreferences;
    String manh;
    String tm,sl;
    LinearLayout btnBack,btnXacnhan, btnhoanthanh;
    ImageButton btngoi;
    boolean check = false;
    // SharedPreferences
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nha_bep_hien_mon);
        listViewdsMon = (ListView) findViewById(R.id.lvMonAn);
        btnBack= findViewById(R.id.goback);
        btnXacnhan=findViewById(R.id.btnxacnhan);
        txtXacNhan=findViewById(R.id.txtXacNhan);
        txtchat2=findViewById(R.id.txtchat2);
        btnhoanthanh= findViewById(R.id.btnhoanthanh);
        btngoi= findViewById(R.id.btngoii);
        txtghichu= findViewById(R.id.txthienghichu);
        edchat=findViewById(R.id.edchat);
        arrayList = new ArrayList<QLMonAnBep>();
        btnhoanthanh.setEnabled(true);
        Intent intent = getIntent();
        tendn = intent.getStringExtra("tenmon");

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        manh= sharedpreferences.getString(MANH, "");

        HienMon();
        qLmonAnBepAdapter= new QLmonAnBepAdapter(NhaBepHienMon.this, R.layout.nhabephienmon, arrayList);
        qLmonAnBepAdapter.notifyDataSetChanged();
        listViewdsMon.setAdapter(qLmonAnBepAdapter);
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.child("MienBac").child(manh).child("BanAn").child(tendn).child("TinhTrang").setValue("cho");
                Toast.makeText(NhaBepHienMon.this, "Đã xác nhận!", Toast.LENGTH_SHORT).show();
                txtXacNhan.setTextColor(Color.parseColor("#5DAEA4"));
                btnXacnhan.setEnabled(false);
                btnhoanthanh.setEnabled(true);
            }
        });
        btnhoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.child("MienBac").child(manh).child("BanAn").child(tendn).child("TinhTrang").setValue("hoanthanh");
                Intent intent= new Intent(NhaBepHienMon.this, NhaBepNhanDL.class);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();

            }
        });
        btngoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //arrayList.clear();
                mData.child("MienBac").child(manh).child("BanAn").child(tendn).child("Chat1").setValue(edchat.getText().toString());
                edchat.setText("");
                Toast.makeText(NhaBepHienMon.this, "Đã gửi...", Toast.LENGTH_SHORT).show();
            }
        });



    }
    public void back()
    {
        Intent intent = new Intent(NhaBepHienMon.this, NhaBepNhanDL.class);
        intent.putExtra("b1","lol");
        startActivity(intent);
    }

    private void HienMon() {
        try {
            mData.child("MienBac").child(manh).child("BanAn").child(tendn.trim()).child("Chat").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Toast.makeText(NhaBepHienMon.this, "vao", Toast.LENGTH_SHORT).show();
                    try {
                        if(!dataSnapshot.getValue().toString().trim().equals("")) {
                            txtchat2.setText("NV: "+ dataSnapshot.getValue().toString());
                        }
                    }


                    catch (Exception ex)
                    {

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception ex)
        {

        }


        mData.child("MienBac").child(manh).child("BanAn").child(tendn.trim()).child("DSMon").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey().trim();

                if(!key.equals("TinhTrang")){


                    try{
                        tm = dataSnapshot.child("tenmon").getValue().toString();
                        sl = dataSnapshot.child("soluong").getValue().toString();
                        String a=dataSnapshot.child("ghichu").getValue().toString();
                        if( a.length()<2)
                        {
                            txtghichu.setText("Không có yêu cầu đặc biệt!");

                        }
                        else
                        {
                            txtghichu.setText(a);
                        }


                    }
                    catch (Exception ex)
                    {

                    }

                    arrayList.add(new QLMonAnBep(tm, sl));
                    //Toast.makeText(NhaBepHienMon.this, "sl+"+ sl+" -"+ tm, Toast.LENGTH_SHORT).show();
                    qLmonAnBepAdapter.notifyDataSetChanged();
                }

                else {
                    if(dataSnapshot.getValue().toString().trim().equals("true")){
                        btnhoanthanh.setEnabled(false);
                        //btngoi.setEnabled(false);
                    }
                    else if(dataSnapshot.getValue().toString().trim().equals("cho")){
                        btnXacnhan.setEnabled(false);
                        btnhoanthanh.setEnabled(true);
                        //btngoi.setEnabled(true);
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


}
