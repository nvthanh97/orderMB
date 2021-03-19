
package com.thanhnguyen.smartordermienbac.CustomAdapter;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.squareup.picasso.Picasso;
        import com.thanhnguyen.smartordermienbac.DTO.NguyenLieuDTO;
        import com.thanhnguyen.smartordermienbac.R;

        import java.text.NumberFormat;
        import java.util.List;
        import java.util.Locale;

public class Adapter_hienthi_nguyenlieu extends BaseAdapter {

    Context context;
    int layout;
    List<NguyenLieuDTO> baiDangDTOList;
    ViewHolderNhanVien viewHolderNhanVien;

    public Adapter_hienthi_nguyenlieu(Context context, int layout, List<NguyenLieuDTO> nhanVienDTOList){
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
        TextView ten, dongia,soluong,thanhtien, ngaydang, donvi, conlai;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolderNhanVien = new ViewHolderNhanVien();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolderNhanVien.ten = view.findViewById(R.id.ten);
            viewHolderNhanVien.thanhtien =  view.findViewById(R.id.tongtiendanhap);
            viewHolderNhanVien.ngaydang =  view.findViewById(R.id.ngaynhapmoinhat);
            viewHolderNhanVien.donvi =  view.findViewById(R.id.donvi);
            viewHolderNhanVien.conlai =  view.findViewById(R.id.conlai);


            view.setTag(viewHolderNhanVien);
        }else{
            viewHolderNhanVien = (ViewHolderNhanVien) view.getTag();
        }
        try{
            viewHolderNhanVien.ten.setText(baiDangDTOList.get(position).getTen());
            Locale localeEN = new Locale("en", "EN");
            NumberFormat en = NumberFormat.getInstance(localeEN);
            long longNumber = Long.parseLong(baiDangDTOList.get(position).getThanhtien());
            String str1 = en.format(longNumber);
            viewHolderNhanVien.thanhtien.setText(str1);
            viewHolderNhanVien.ngaydang.setText("<"+baiDangDTOList.get(position).getNgaynhap()+">");
            viewHolderNhanVien.donvi.setText(baiDangDTOList.get(position).getDonvi());
            viewHolderNhanVien.conlai.setText("Kho còn: "+ baiDangDTOList.get(position).getConlai());
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
