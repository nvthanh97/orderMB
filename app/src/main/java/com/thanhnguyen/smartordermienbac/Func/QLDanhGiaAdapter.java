package com.thanhnguyen.smartordermienbac.Func;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thanhnguyen.smartordermienbac.FragmentApp.HTDanhGia;
import com.thanhnguyen.smartordermienbac.R;

import java.util.List;

public class QLDanhGiaAdapter extends BaseAdapter
{
    ViewHolder holder;
    Context context;
    int layout;

    List<HTDanhGia> QLBanAnx;

    public QLDanhGiaAdapter(Context context, int layout, List<HTDanhGia> QLBanAnx) {
        this.context = context;
        this.layout = layout;
        this.QLBanAnx = QLBanAnx;
    }

    @Override
    public int getCount() {

        return QLBanAnx.size();
    }

    public class ViewHolder{

        TextView txtten, txttg, txtnoidung;
        ImageView hinhdg, diem;
    }
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.txtten =  view.findViewById(R.id.tennguoidang);
            holder.diem = view.findViewById(R.id.diem);
            holder.txtnoidung = view.findViewById(R.id.txtnoidung);
            holder.hinhdg =  view.findViewById(R.id.imHinhDang);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        HTDanhGia x = QLBanAnx.get(i);
        holder.txtten.setText(x.getTen());
        if( x.getNd()=="")
        {
            holder.txtnoidung.setText(" ");
        }
        else
        {
            holder.txtnoidung.setText(x.getNd().toString());
        }
        int d=Integer.parseInt(x.getDiem().trim());
        //Toast.makeText(context, d, Toast.LENGTH_LONG).show();
        if(d==1)
        {
            holder.diem.setImageResource(R.drawable.st1);
        }
        if(d==2)
        {
            holder.diem.setImageResource(R.drawable.st2);
        }
        if(d==3)
        {
            holder.diem.setImageResource(R.drawable.st3);
        }
        if(d==4)
        {
            holder.diem.setImageResource(R.drawable.st4);
        }
        if(d==5)
        {
            holder.diem.setImageResource(R.drawable.st5);
        }
        String ha=x.getHinh().trim();
        if(String.valueOf(ha).equals("trong"))
        {
            holder.hinhdg.setVisibility(View.GONE);
        }
        else  if (!String.valueOf(ha).equals("trong")){
            loadimageinternet(ha,holder.hinhdg);
            holder.hinhdg.setVisibility(View.VISIBLE);
        }
        return view;

    }
    private void loadimageinternet(String url ,ImageView x){
        Picasso.get().load(url).placeholder(R.color.transparent)
                .fit()
                .into(x, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }
}
