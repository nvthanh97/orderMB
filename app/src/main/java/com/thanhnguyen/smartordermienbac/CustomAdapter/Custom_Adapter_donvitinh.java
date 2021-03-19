
package com.thanhnguyen.smartordermienbac.CustomAdapter;

        import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;
import com.thanhnguyen.smartordermienbac.DTO.DonViDTO;
import com.thanhnguyen.smartordermienbac.R;

import java.util.ArrayList;


public class Custom_Adapter_donvitinh extends ArrayAdapter<DonViDTO> {

    public Custom_Adapter_donvitinh(@NonNull Context context, ArrayList<DonViDTO> customList) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_layout, parent, false);
        }
        DonViDTO items = getItem(position);
        ImageView spinnerImage = convertView.findViewById(R.id.ivCustomSpinner);
        TextView spinnerName = convertView.findViewById(R.id.tvCustomSpinner);
        if (spinnerImage==null || spinnerImage.equals(""))
        {
            spinnerImage.setImageResource(R.drawable.apple);
        }

        {
            spinnerName.setText(items.getTen());
            //loadimageinternet(hha,spinnerImage);
        }
        return convertView;
    }
    private void loadimageinternet(String url ,ImageView x){
        Picasso.get().load(url).placeholder(R.drawable.bin)
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