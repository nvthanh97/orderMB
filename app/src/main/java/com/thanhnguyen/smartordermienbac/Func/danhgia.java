package com.thanhnguyen.smartordermienbac.Func;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.thanhnguyen.smartordermienbac.FragmentApp.HTDanhGia;
import com.thanhnguyen.smartordermienbac.R;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class danhgia extends AppCompatActivity implements View.OnClickListener {
    public static int REQUEST_CODE_MOHINH = 123;
    DatabaseReference mData = FirebaseDatabase.getInstance("https://smartorder-8f077.firebaseio.com/").getReference();
    ListView lvBanAn;
    QLDanhGiaAdapter adapter;
    ArrayList<HTDanhGia> arrayList= new ArrayList<HTDanhGia>();
    String ten,diem,tg,nd,hinh;
    String link="trong";
    String tendnn="Khách";
    ImageView imhinh;
    EditText ednd;
    Button btndanhgia;
    int mark=0;
    boolean st1=false, st2=false, st3=false, st4=false, st5=false;
    ImageView star1, star2, star3, star4,star5;
    SharedPreferences sharedpreferences;
    String manh;
    int flag=0;
    ArrayList<HTDanhGia> list;
    static boolean active = false;

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        manh = intent.getStringExtra("manh").toUpperCase().trim();
        //Toast.makeText(this, manh, Toast.LENGTH_LONG).show();
        setContentView(R.layout.danhgia);
//
        imhinh= findViewById(R.id.imHinhDG);
        ednd=findViewById(R.id.motachitiet);
        btndanhgia=findViewById(R.id.btndanhcmngia);
        btndanhgia.setEnabled(true);
        btndanhgia.setOnClickListener(this);
        imhinh.setOnClickListener(this);
        star1= findViewById(R.id.star1);
        star2= findViewById(R.id.star2);
        star3= findViewById(R.id.star3);
        star4= findViewById(R.id.star4);
        star5= findViewById(R.id.star5);
        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(st1==false )
                {
                    mark=1;
                    star1.setImageResource(R.drawable.favourites);
                    st1=true;
                }
                if(st1==true)
                {
                    mark=1;
                    star2.setImageResource(R.drawable.star);
                    star3.setImageResource(R.drawable.star);
                    star4.setImageResource(R.drawable.star);
                    star5.setImageResource(R.drawable.star);
                    st1=false;
                }

            }
        });
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(st2==false)
                {
                    mark=2;
                    star1.setImageResource(R.drawable.favourites);
                    star2.setImageResource(R.drawable.favourites);
                    st2=true;
                }
                if(st2==true)
                {
                    mark=2;
                    star3.setImageResource(R.drawable.star);
                    star4.setImageResource(R.drawable.star);
                    star5.setImageResource(R.drawable.star);
                    st2=false;
                }
            }
        });
        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(st3==false)
                {
                    mark=3;
                    star1.setImageResource(R.drawable.favourites);
                    star2.setImageResource(R.drawable.favourites);
                    star3.setImageResource(R.drawable.favourites);
                    st3=true;
                }
                if(st3==true)
                {
                    mark=3;
                    star4.setImageResource(R.drawable.star);
                    star5.setImageResource(R.drawable.star);
                    st3=false;
                }
            }
        });
        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(st4==false)
                {
                    mark=4;
                    star1.setImageResource(R.drawable.favourites);
                    star2.setImageResource(R.drawable.favourites);
                    star3.setImageResource(R.drawable.favourites);
                    star4.setImageResource(R.drawable.favourites);
                    st4=true;
                }
                if(st4==true)
                {
                    mark=4;
                    star5.setImageResource(R.drawable.star);
                    st4=false;

                }
            }
        });
        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(st5==false)
                {
                    mark=5;
                    star1.setImageResource(R.drawable.favourites);
                    star2.setImageResource(R.drawable.favourites);
                    star3.setImageResource(R.drawable.favourites);
                    star4.setImageResource(R.drawable.favourites);
                    star5.setImageResource(R.drawable.favourites);
                }

            }
        });

        lvBanAn = (ListView) findViewById(R.id.lvBanAn);
        list = new ArrayList<>();
        try{
            addBan();
        }
        catch (Exception ex)
        {

        }

    }
    public  void HienThi()
    {
        //Toast.makeText(this, "â "+ arrayList.size(), Toast.LENGTH_SHORT).show();
        //ArrayList
        Collections.reverse(arrayList);
        adapter= new QLDanhGiaAdapter(danhgia.this, R.layout.customhtdanhgia, arrayList);
        adapter.notifyDataSetChanged();
        lvBanAn.setAdapter(adapter);
    }

    private void addBan() {
        mData.child("MienBac").child(manh).child("DanhGia").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey().trim();
                //oast.makeText(danhgia.this, key, Toast.LENGTH_SHORT).show();

                    try{
                        diem = dataSnapshot.child("diem").getValue().toString();
                        hinh = dataSnapshot.child("hinh").getValue().toString();
                        nd=dataSnapshot.child("nd").getValue().toString();
                        ten = dataSnapshot.child("ten").getValue().toString();
                        tg=dataSnapshot.child("tg").getValue().toString();
                        arrayList.add(new HTDanhGia(diem,hinh,nd,ten,tg));
                        HienThi();



                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(danhgia.this, ex.toString(), Toast.LENGTH_LONG).show();
                    }



                }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey().trim();
                try{
                    diem = dataSnapshot.child("diem").getValue().toString();
                    hinh = dataSnapshot.child("hinh").getValue().toString();
                    nd=dataSnapshot.child("nd").getValue().toString();
                    ten = dataSnapshot.child("ten").getValue().toString();
                    tg=dataSnapshot.child("tg").getValue().toString();
                    arrayList.add(new HTDanhGia(diem,hinh,nd,ten,tg));
                    HienThi();

                }
                catch (Exception ex)
                {
                    Toast.makeText(danhgia.this, ex.toString(), Toast.LENGTH_LONG).show();
                }




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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FirebaseStorage storage = FirebaseStorage.getInstance();

        if(REQUEST_CODE_MOHINH == requestCode){
            if(resultCode == Activity.RESULT_OK && data !=null){
                imhinh.setImageURI(data.getData());
                StorageReference storageRef = storage.getReferenceFromUrl("gs://smartorder-8f077.appspot.com"); //gs://smartorder-13eb1.appspot.com
                Calendar calendar= Calendar.getInstance();
                StorageReference mountainsRef = storageRef.child("image"+ calendar.getTimeInMillis()+ ".png");

                // Get the data from an ImageView as bytes
                Bitmap bitmap = ((BitmapDrawable) imhinh.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] dataa = baos.toByteArray();

                final UploadTask uploadTask = mountainsRef.putBytes(dataa);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(danhgia.this, "Loi upload hinh", Toast.LENGTH_SHORT).show();
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
                                                link=imageUrl;
                                                //MonAnDTO monAnDTO=new MonAnDTO(1,1,String.valueOf(edGiaTien.toString()), String.valueOf(edGiaTien.getText()),String.valueOf(imageUrl));
                                                //myRef.setValue(imageUrl);
                                                //Toast.makeText(DangBai.this, imageUrl, Toast.LENGTH_SHORT).show();
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


    @Override
    public void onClick(View v) {
        int id= v.getId();
        switch (id) {


            case R.id.imHinhDG:
                Intent iMoHinh = new Intent();
                iMoHinh.setType("image/*");
                iMoHinh.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(iMoHinh, "Chọn hình ảnh"), REQUEST_CODE_MOHINH);
                ;
                break;
            case R.id.btndanhcmngia:
            {
                if( String.valueOf(link)=="trong")
                {
                    Toast.makeText(this, "Hãy thêm một tấm hình để mọi người biết bạn mới ăn tại quán này nào ^^", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DateFormat dateFormatter = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
                    dateFormatter.setLenient(false);
                    Date today = new Date();
                    String ngaygoi = dateFormatter.format(today);
                    listdanhgia x = new listdanhgia(mark+"", tendnn, ednd.getText().toString(), link,ngaygoi);
                    mData.child("MienBac").child(manh).child("DanhGia").push().setValue(x);
                    ednd.setText("");
                    btndanhgia.setEnabled(false);
                }


            }
        }

    }
}
