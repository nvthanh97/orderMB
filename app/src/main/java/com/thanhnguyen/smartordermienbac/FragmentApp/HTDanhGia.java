package com.thanhnguyen.smartordermienbac.FragmentApp;

public class HTDanhGia {
    String diem, hinh, nd, ten, tg;

    public String getDiem() {
        return diem;
    }
public HTDanhGia(){}
    public void setDiem(String diem) {
        this.diem = diem;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getNd() {
        return nd;
    }

    public void setNd(String nd) {
        this.nd = nd;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTg() {
        return tg;
    }

    public void setTg(String tg) {
        this.tg = tg;
    }

    public HTDanhGia(String diem, String hinh, String nd, String ten, String tg) {
        this.diem = diem;
        this.hinh = hinh;
        this.nd = nd;
        this.ten = ten;
        this.tg = tg;
    }
}
