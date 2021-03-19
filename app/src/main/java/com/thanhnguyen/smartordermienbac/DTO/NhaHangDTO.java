package com.thanhnguyen.smartordermienbac.DTO;

public class NhaHangDTO {
    String manh;
    String tennh;
    String tinh;
    String huyen;
    String xa;
    String mota;
    String loaihinh;
    String hinhanh;
    String trangthai;
    String banonline;
    String ngaydang;
public NhaHangDTO(){}
    public NhaHangDTO(String manh, String tennh, String tinh, String huyen, String xa, String mota, String loaihinh, String hinhanh, String trangthai, String banonline, String ngaydang) {
        this.manh = manh;
        this.tennh = tennh;
        this.tinh = tinh;
        this.huyen = huyen;
        this.xa = xa;
        this.mota = mota;
        this.loaihinh = loaihinh;
        this.hinhanh = hinhanh;
        this.trangthai = trangthai;
        this.banonline = banonline;
        this.ngaydang = ngaydang;
    }

    public String getManh() {
        return manh;
    }

    public void setManh(String manh) {
        this.manh = manh;
    }

    public String getTennh() {
        return tennh;
    }

    public void setTennh(String tennh) {
        this.tennh = tennh;
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

    public String getLoaihinh() {
        return loaihinh;
    }

    public void setLoaihinh(String loaihinh) {
        this.loaihinh = loaihinh;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getBanonline() {
        return banonline;
    }

    public void setBanonline(String banonline) {
        this.banonline = banonline;
    }

    public String getNgaydang() {
        return ngaydang;
    }

    public void setNgaydang(String ngaydang) {
        this.ngaydang = ngaydang;
    }


}
