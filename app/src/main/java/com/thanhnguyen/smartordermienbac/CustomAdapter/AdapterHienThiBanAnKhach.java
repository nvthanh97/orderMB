package com.thanhnguyen.smartordermienbac.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.thanhnguyen.smartordermienbac.DAO.BanAnDAO;
import com.thanhnguyen.smartordermienbac.DAO.GoiMonDAO;
import com.thanhnguyen.smartordermienbac.DTO.BanAnDTO;
import com.thanhnguyen.smartordermienbac.Func.HtThongKeMonAn;
import com.thanhnguyen.smartordermienbac.Func.ThanhToanActivityKhach;
import com.thanhnguyen.smartordermienbac.Func.homek;
import com.thanhnguyen.smartordermienbac.R;
import com.thanhnguyen.smartordermienbac.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MANH;
import static com.thanhnguyen.smartordermienbac.Func.dangnhap.MyPREFERENCES;

public class AdapterHienThiBanAnKhach extends BaseAdapter implements RecognitionListener {


    Context context;
    private TextView returnedText;
    private ToggleButton toggleButton;
    private ProgressBar progressBar;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionActivity";
    int layout;
    int i=1;
    List<BanAnDTO> banAnDTOList;
    ViewHolderBanAn viewHolderBanAn;
    BanAnDAO banAnDAO;
    int maban;
    String tendn;
    int a=1;
    String tenban;
    SharedPreferences sharedpreferences;
    String manh;
    GoiMonDAO goiMonDAO;
    FragmentManager fragmentManager;
    String urlgoimon= config.domain + "android/goimon.php";
    String urltinhtrang=config.domain + "android/gettinhtrangban.php";

    public AdapterHienThiBanAnKhach(Context context, int layout, List<BanAnDTO> banAnDTOList){
        this.context = context;
        this.layout = layout;
        this.banAnDTOList = banAnDTOList;

        banAnDAO = new BanAnDAO(context);
        goiMonDAO = new GoiMonDAO(context);
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        manh= sharedpreferences.getString(MANH, "");

    }
    public AdapterHienThiBanAnKhach(){}


    @Override
    public int getCount() {
        return banAnDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return banAnDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return banAnDTOList.get(position).getMaBan();
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
        progressBar.setIndeterminate(false);
        progressBar.setMax(10);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
        progressBar.setIndeterminate(true);
        toggleButton.setChecked(false);
    }

    @Override
    public void onError(int errorCode) {
        /*String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
        viewHolderBanAn.txttroly.setText(errorMessage);
        MediaPlayer mp = MediaPlayer.create(context, R.raw.khongnghe);
        mp.start();
        toggleButton.setChecked(false);*/
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        for (String result : matches)
            text += result +" ";

        viewHolderBanAn.txttroly.setText(text);
        if (text.contains("gọi món")||text.contains("thực đơn")||text.contains("món gì")||text.contains("món ăn")||text.contains("ăn gì")||text.contains("cho gọi") ||text.contains("muốn gọi") ||text.contains("không em") ||text.contains("có gì"))
        {
            MediaPlayer mp = MediaPlayer.create(context, R.raw.goimon);
            mp.start();
            Intent HTTD = new Intent(context, homek.class);
            //Bundle bDuLieuThucDon = new Bundle();
            HTTD.putExtra("maban",maban);
            //String tenbanan = banAnDTOList.get().getTenBan();
            //iGuiBep.putExtra("tenban",tenbanan);
            context.startActivity(HTTD);
        }
        if (text.contains("thanh toán")||text.contains("tính tiền")||text.contains("in hóa đơn")||text.contains("phục vụ")||text.contains("trả tiền")||text.contains("ăn xong rồi"))
        {

            MediaPlayer mp = MediaPlayer.create(context, R.raw.thanhtoan);
            mp.start();
            antroly(true);
            gettinhtrangban(tenban);
            //Intent layITrangChu1 = ((home)context).getIntent();
            //tendn= layITrangChu1.getStringExtra("tendn");
            tendn="Khách_ "+ tenban;
            //Toast.makeText(context, "ma nhan vien"+ tendn, Toast.LENGTH_LONG).show();
            Intent iGuiBep = new Intent(context, ThanhToanActivityKhach.class);
            iGuiBep.putExtra("maban",maban);
            iGuiBep.putExtra("tenban",tenban);
            iGuiBep.putExtra("tendn",tendn);
            //String tenbanan = banAnDTOList.get().getTenBan();
            //iGuiBep.putExtra("tenban",tenbanan);
            context.startActivity(iGuiBep);

        }
        if (text.contains("con gà")||text.contains("quả trứng"))
        {
            MediaPlayer mp = MediaPlayer.create(context, R.raw.ga);
            mp.start();
        }
        if (text.contains("kết quả")||text.contains("chung kết")||text.contains("bóng đá"))
        {
            MediaPlayer mp = MediaPlayer.create(context, R.raw.bong);
            mp.start();
        }
        if (text.contains("là ai")||text.contains("có thể")||text.contains("làm gì")||text.contains("tên gì")||text.contains("nhiêu tuổi")||text.contains("có thể làm gì"))
        {
            MediaPlayer mp = MediaPlayer.create(context, R.raw.lamgi);
            mp.start();
        }
        if (text.contains("món ngon")||text.contains("đặc biệt")||text.contains("gọi nhiều")||text.contains("món đặc biệt"))
        {
            Intent iGuiBep = new Intent(context, HtThongKeMonAn.class);
            iGuiBep.putExtra("maban",maban);
            iGuiBep.putExtra("tenban",tenban);
            iGuiBep.putExtra("tendn",tendn);
            //String tenbanan = banAnDTOList.get().getTenBan();
            //iGuiBep.putExtra("tenban",tenbanan);
            context.startActivity(iGuiBep);
        }
        else {

        }
        //Toast.makeText(context, ""+ text, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
        progressBar.setProgress((int) rmsdB);
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }



    public class ViewHolderBanAn{
        ImageView imBanAn,imGoiMon,imThanhToan,imAnButton, imtroly;
        TextView txtTenBanAn, txttroly;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderBanAn = new ViewHolderBanAn();
            view = inflater.inflate(R.layout.custom_layout_hienthibanankhach,parent,false);
            viewHolderBanAn.imBanAn = (ImageView) view.findViewById(R.id.imBanAn);
            viewHolderBanAn.imGoiMon = (ImageView) view.findViewById(R.id.imGoiMon);
            viewHolderBanAn.imThanhToan = (ImageView) view.findViewById(R.id.imThanhToan);
            viewHolderBanAn.imAnButton = (ImageView) view.findViewById(R.id.imAnButton);
            viewHolderBanAn.txtTenBanAn = (TextView) view.findViewById(R.id.txtTenBanAn);
            viewHolderBanAn.imtroly =  view.findViewById(R.id.troly1);
            viewHolderBanAn.txttroly = view.findViewById(R.id.troly2);

            view.setTag(viewHolderBanAn);
            returnedText = (TextView) view.findViewById(R.id.textView1);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
            toggleButton = (ToggleButton) view.findViewById(R.id.toggleButton1);

            progressBar.setVisibility(View.INVISIBLE);
            speech = SpeechRecognizer.createSpeechRecognizer(context);
            speech.setRecognitionListener(this);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
            recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"en");
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,this.context.getPackageName());
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,this.context.getPackageName());

            recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);

            toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    if (isChecked) {
                        nghe();
                    } else {
                        progressBar.setIndeterminate(false);
                        progressBar.setVisibility(View.INVISIBLE);
                        speech.stopListening();
                    }
                }
            });


        }else{
            viewHolderBanAn = (ViewHolderBanAn) view.getTag();
        }

         HienThiButton();
         HienTroly();

        BanAnDTO banAnDTO = banAnDTOList.get(position);
        tenban=banAnDTO.getTenBan();
        gettinhtrangban(tenban);
        viewHolderBanAn.imBanAn.setImageResource(R.drawable.banankhongkhach);
        viewHolderBanAn.txtTenBanAn.setText(banAnDTO.getTenBan());
        viewHolderBanAn.imBanAn.setTag(position);
        maban = banAnDTOList.get(0).getMaBan();
        final String tenban= banAnDTOList.get(0).getTenBan();

        viewHolderBanAn.imBanAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HienThiButton();
                int vitri = (int) v.getTag();
                banAnDTOList.get(vitri).setDuocChon(false);
                gettinhtrangban(tenban);
            }
        });
        viewHolderBanAn.imGoiMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HTTD = new Intent(context, homek.class);
                //Bundle bDuLieuThucDon = new Bundle();
                HTTD.putExtra("maban",maban);
                //String tenbanan = banAnDTOList.get().getTenBan();
                //iGuiBep.putExtra("tenban",tenbanan);
                context.startActivity(HTTD);
                DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                dateFormatter.setLenient(false);
                Date today = new Date();
                String ngaygoi = dateFormatter.format(today);

                goimon(String.valueOf(maban),"-1",ngaygoi,"false");

            }
        });
        viewHolderBanAn.imThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                antroly(true);
                gettinhtrangban(tenban);
                //Intent layITrangChu1 = ((home)context).getIntent();
                //tendn= layITrangChu1.getStringExtra("tendn");
                tendn="Khách_ "+ tenban;
                //Toast.makeText(context, "ma nhan vien"+ tendn, Toast.LENGTH_LONG).show();


                Intent iGuiBep = new Intent(context, ThanhToanActivityKhach.class);
                iGuiBep.putExtra("maban",maban);
                iGuiBep.putExtra("tenban",tenban);
                iGuiBep.putExtra("tendn",tendn);
                //String tenbanan = banAnDTOList.get().getTenBan();
                //iGuiBep.putExtra("tenban",tenbanan);
                context.startActivity(iGuiBep);
            }
        });
        viewHolderBanAn.imAnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MediaPlayer mp = MediaPlayer.create(context, R.raw.giupgi);
                mp.start();
                //Intent myactivity = new Intent(context.getApplicationContext(), VoiceRecoveryActivity.class);
                //myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
                //context.getApplicationContext().startActivity(myactivity);
                if ( i==1)
                {
                    final int secs = 3;
                    new CountDownTimer((secs +1) * 1000, 1000) // Wait 5 secs, tick every 1 sec
                    {
                        @Override
                        public final void onTick(final long millisUntilFinished)
                        {

                        }
                        @Override
                        public final void onFinish()
                        {

                            toggleButton.setChecked(true);
                        }
                    }.start();

                }
                else  {
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.INVISIBLE);
                    speech.stopListening();
                    i=1;
                    HienTroly();

                }

            }
        });

        return view;
    }
public  void nghe()
{
    progressBar.setVisibility(View.VISIBLE);
    speech.startListening(recognizerIntent);
}
    private void HienTroly(){
        //Toast.makeText(context, "Hien", Toast.LENGTH_SHORT).show();
        viewHolderBanAn.imtroly.setVisibility(View.VISIBLE);
        viewHolderBanAn.txttroly.setVisibility(View.VISIBLE);
        //Toast.makeText(context, "vao day", Toast.LENGTH_SHORT).show();
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.hieuung_hienthi_button_banan);
        viewHolderBanAn.imtroly.startAnimation(animation);

    }

    private void HienThiButton(){
        viewHolderBanAn.imGoiMon.setVisibility(View.VISIBLE);
        viewHolderBanAn.imThanhToan.setVisibility(View.VISIBLE);
        viewHolderBanAn.imAnButton.setVisibility(View.VISIBLE);

        Animation animation = AnimationUtils.loadAnimation(context,R.anim.hieuung_hienthi_button_banan);
        viewHolderBanAn.imGoiMon.startAnimation(animation);
        viewHolderBanAn.imThanhToan.startAnimation(animation);
        viewHolderBanAn.imAnButton.startAnimation(animation);

    }

    private void antroly(boolean hieuung){
        //Toast.makeText(context, "An", Toast.LENGTH_SHORT).show();
        if(hieuung){
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.hieuung_anbutton_banan);
            viewHolderBanAn.imtroly.startAnimation(animation);
        }

        viewHolderBanAn.txttroly.setVisibility(View.INVISIBLE);
    }

    private void AnButton(boolean hieuung){
        if(hieuung){
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.hieuung_anbutton_banan);
            viewHolderBanAn.imGoiMon.startAnimation(animation);
            viewHolderBanAn.imThanhToan.startAnimation(animation);
            viewHolderBanAn.imAnButton.startAnimation(animation);
        }

        viewHolderBanAn.imGoiMon.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imThanhToan.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imAnButton.setVisibility(View.INVISIBLE);
    }


    public void gettinhtrangban(final String mb)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urltinhtrang,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response+ " ", Toast.LENGTH_SHORT).show();
                        if (response.trim().equals("true")){
                            viewHolderBanAn.imBanAn.setImageResource(R.drawable.banancokhach);
                        }
                        else
                        {
                            viewHolderBanAn.imBanAn.setImageResource(R.drawable.banankhongkhach);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context, "Kết nối sever thất bại! " + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("tenban",String.valueOf(mb));
                params.put("manh",manh);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }


    public void goimon(final String mb, final String manv, final String ng, final String tt)
    {
        //Toast.makeText(context, "vao ham goi mon" + mb+ "-"+ manv + "-"+ ng+ "-"+ tt, Toast.LENGTH_LONG).show();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlgoimon,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("true")){
                            Toast.makeText(context, "Danh sách loại món ăn của chúng tôi", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(context, "loi them goi mon"+ response, Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context, "Kết nối sever thất bại! " + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("maban",mb);
                params.put("manv",manv);
                params.put("ngaygoi",ng);
                params.put("tinhtrang",tt);
                params.put("manh",manh);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }



}
