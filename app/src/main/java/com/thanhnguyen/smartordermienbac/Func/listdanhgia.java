package com.thanhnguyen.smartordermienbac.Func;

public class listdanhgia {
    String diem, ten,nd, hinh, tg;

    public listdanhgia(String diem, String ten, String nd, String hinh, String tg) {
        this.diem = diem;
        this.ten = ten;
        this.nd = nd;
        this.hinh = hinh;
        this.tg = tg;
    }

    public String getDiem() {
        return diem;
    }

    public void setDiem(String diem) {
        this.diem = diem;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNd() {
        return nd;
    }

    public void setNd(String nd) {
        this.nd = nd;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getTg() {
        return tg;
    }

    public void setTg(String tg) {
        this.tg = tg;
    }

    public listdanhgia(){}

}
