package com.thanhnguyen.smartordermienbac.CustomAdapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.thanhnguyen.smartordermienbac.DTO.DiaDiemDTO;
import com.thanhnguyen.smartordermienbac.R;

import java.util.ArrayList;


public class DiaDiemAdapter extends ArrayAdapter<DiaDiemDTO> {

    public DiaDiemAdapter(@NonNull Context context, ArrayList<DiaDiemDTO> customList) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spindiadiem, parent, false);
        }
        DiaDiemDTO items = getItem(position);
        ImageView spinnerImage = convertView.findViewById(R.id.ivCustomSpinner);
        TextView spinnerName = convertView.findViewById(R.id.tvCustomSpinner);
        if (spinnerImage==null || spinnerImage.equals(""))
        {
            spinnerImage.setImageResource(R.drawable.icon);
        }
        if (items != null) {
            spinnerName.setText(items.getTendiadiem());
        }
        return convertView;
    }
}