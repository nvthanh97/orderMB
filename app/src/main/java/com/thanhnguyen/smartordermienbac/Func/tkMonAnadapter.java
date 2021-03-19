package com.thanhnguyen.smartordermienbac.Func;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thanhnguyen.smartordermienbac.R;

import java.util.List;

public class tkMonAnadapter extends BaseAdapter {
    Context mycontext;
    int mylayout;
    List<tkmonan>  arraylsgoimon;
    public tkMonAnadapter(Context context, int layout, List<tkmonan> listLsgoimon)
    {
        mycontext=context;
        mylayout= layout;
        arraylsgoimon=listLsgoimon;
    }
    @Override
    public int getCount() {
        return arraylsgoimon.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(mylayout, null);
        TextView tenban= convertView.findViewById(R.id.txttenbanls);
        try
        {
            tenban.setText(arraylsgoimon.get(position).getTenmon());
        }
        catch (Exception ex)
        {

        }

        TextView ngaygoi= convertView.findViewById(R.id.txtngaygoils);
        ngaygoi.setText(arraylsgoimon.get(position).getSolangoi());
        return convertView;
    }
}
