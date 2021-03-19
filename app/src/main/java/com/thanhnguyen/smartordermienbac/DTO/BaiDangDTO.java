package com.thanhnguyen.smartordermienbac.DTO;

public class BaiDangDTO {
    String mabaidang;
    String tendn;
    String tieude;
    String giatien;
    String tinh;
    String huyen;
    String xa;
    String trangthai;

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public BaiDangDTO(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getMabaidang() {
        return mabaidang;
    }

    public void setMabaidang(String mabaidang) {
        this.mabaidang = mabaidang;
    }

    public String getTendn() {
        return tendn;
    }

    public void setTendn(String tendn) {
        this.tendn = tendn;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getGiatien() {
        return giatien;
    }

    public void setGiatien(String giatien) {
        this.giatien = giatien;
    }

    public String getTinh() {
        return tinh;
    }

    public void setTinh(String tinh) {
        this.tinh = tinh;
    }

    public String getHuyen() {
        return huyen;
    }

    public void setHuyen(String huyen) {
        this.huyen = huyen;
    }

    public String getXa() {
        return xa;
    }

    public void setXa(String xa) {
        this.xa = xa;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getThoigiandang() {
        return thoigiandang;
    }

    public void setThoigiandang(String thoigiandang) {
        this.thoigiandang = thoigiandang;
    }

    public String getNgaydang() {
        return ngaydang;
    }

    public void setNgaydang(String ngaydang) {
        this.ngaydang = ngaydang;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    String mota;
    String hinhanh;
    String thoigiandang;
    String ngaydang;
    String sdt;
    String hoten;


    public BaiDangDTO(String mabaidang, String tendn, String tieude, String giatien, String tinh, String huyen, String xa, String mota, String hinhanh, String thoigiandang, String ngaydang, String sdt, String hoten, String tt) {
        this.mabaidang = mabaidang;
        this.tendn = tendn;
        this.tieude = tieude;
        this.giatien = giatien;
        this.tinh = tinh;
        this.huyen = huyen;
        this.xa = xa;
        this.mota = mota;
        this.hinhanh = hinhanh;
        this.thoigiandang = thoigiandang;
        this.ngaydang = ngaydang;
        this.sdt = sdt;
        this.hoten = hoten;
        this.trangthai=tt;
    }

    public BaiDangDTO(){}


}
