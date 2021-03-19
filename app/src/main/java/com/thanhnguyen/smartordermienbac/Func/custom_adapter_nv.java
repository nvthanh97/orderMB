package com.thanhnguyen.smartordermienbac.Func;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thanhnguyen.smartordermienbac.DTO.SPNhanVien;
import com.thanhnguyen.smartordermienbac.R;

import java.util.ArrayList;


public class custom_adapter_nv extends ArrayAdapter<SPNhanVien> {

    public custom_adapter_nv(@NonNull Context context, ArrayList<SPNhanVien> customList) {
        super(context, 0, customList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return customView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return customView(position, convertView, parent);
    }

    public View customView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_nhanvien, parent, false);
        }

        SPNhanVien items = getItem(position);
        TextView ivCustomSpinner = convertView.findViewById(R.id.tvCustomSpinner);
        if (items != null) {
            ivCustomSpinner.setText(items.getNhanvien());
        }
        return convertView;
    }
}