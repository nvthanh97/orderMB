package com.thanhnguyen.smartordermienbac.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thanhnguyen.smartordermienbac.DTO.NhaHangDTO;
import com.thanhnguyen.smartordermienbac.R;

import java.util.List;

public class AdapterShowFull extends BaseAdapter {

    Context context;
    int layout;
    List<NhaHangDTO> baiDangDTOList;
    ViewHolderNhanVien viewHolderNhanVien;

    public AdapterShowFull(Context context, int layout, List<NhaHangDTO> nhanVienDTOList){
        this.context = context;
        this.layout = layout;
        this.baiDangDTOList = nhanVienDTOList;
    }

    @Override
    public int getCount() {
        return baiDangDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return baiDangDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // return nhanVienDTOList.get(position).getXa();
        return 1;
    }

    public class ViewHolderNhanVien{
        ImageView imLoadve;
        TextView tieude, giatien,noidung,xa, huyen, tinh, thoigian, hoten, sdt;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolderNhanVien = new ViewHolderNhanVien();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);
            viewHolderNhanVien.imLoadve = (ImageView) view.findViewById(R.id.hinhloadve);
            viewHolderNhanVien.tieude = (TextView) view.findViewById(R.id.tieude);
            viewHolderNhanVien.noidung = (TextView) view.findViewById(R.id.mota);
            viewHolderNhanVien.tinh =  view.findViewById(R.id.tinh);
            viewHolderNhanVien.huyen = (TextView) view.findViewById(R.id.huyen);
            viewHolderNhanVien.xa = (TextView) view.findViewById(R.id.xa);
            viewHolderNhanVien.giatien = (TextView) view.findViewById(R.id.giatien);
            viewHolderNhanVien.thoigian= view.findViewById(R.id.ngaydang);

            view.setTag(viewHolderNhanVien);
        }else{
            viewHolderNhanVien = (ViewHolderNhanVien) view.getTag();
        }
        try{
            viewHolderNhanVien.tieude.setText(baiDangDTOList.get(position).getTennh());
            String a=baiDangDTOList.get(position).getBanonline();
            if(String.valueOf(a)=="false")
            {
                viewHolderNhanVien.giatien.setText("Gọi món truyền thống");
            }
            else {
                viewHolderNhanVien.giatien.setText("Có gọi món bằng QR- code");
            }

            viewHolderNhanVien.noidung.setText(baiDangDTOList.get(position).getMota());
            viewHolderNhanVien.tinh.setText(baiDangDTOList.get(position).getTinh());
            viewHolderNhanVien.huyen.setText(" -    "+baiDangDTOList.get(position).getHuyen());
            viewHolderNhanVien.xa.setText( baiDangDTOList.get(position).getXa());
            String hinh=baiDangDTOList.get(position).getHinhanh();
            loadimageinternet(hinh,viewHolderNhanVien.imLoadve);
            viewHolderNhanVien.thoigian.setText("Hoạt động từ: " +baiDangDTOList.get(position).getNgaydang());

        }
        catch (Exception ex)
        {

        }

        return view;
    }
    private void loadimageinternet(String url ,ImageView x){
        Picasso.get().load(url).placeholder(R.drawable.icon)
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
