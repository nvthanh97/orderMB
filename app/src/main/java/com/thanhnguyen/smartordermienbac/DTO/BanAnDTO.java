package com.thanhnguyen.smartordermienbac.DTO;

import java.io.Serializable;

public class BanAnDTO implements Serializable {
    int MaBan;
    String TenBan;
    boolean  DuocChon;

    public boolean isDuocChon() {
        return DuocChon;
    }

    public void setDuocChon(boolean duocChon) {
        DuocChon = duocChon;
    }



    public int getMaBan() {
        return MaBan;
    }

    public void setMaBan(int maBan) {
        MaBan = maBan;
    }

    public String getTenBan() {
        return TenBan;
    }

    public void setTenBan(String tenBan) {
        TenBan = tenBan;
    }


}
