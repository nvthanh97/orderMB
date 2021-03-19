package com.thanhnguyen.smartordermienbac.DTO;

public class DiaDiemDTO {
    String madiadiem, tendiadiem,matinh,mahuyen;
    public DiaDiemDTO(){}

    public String getMadiadiem() {
        return madiadiem;
    }

    public void setMadiadiem(String madiadiem) {
        this.madiadiem = madiadiem;
    }

    public String getTendiadiem() {
        return tendiadiem;
    }

    public void setTendiadiem(String tendiadiem) {
        this.tendiadiem = tendiadiem;
    }

    public String getMatinh() {
        return matinh;
    }

    public void setMatinh(String matinh) {
        this.matinh = matinh;
    }

    public String getMahuyen() {
        return mahuyen;
    }

    public void setMahuyen(String mahuyen) {
        this.mahuyen = mahuyen;
    }

    public DiaDiemDTO(String madiadiem, String tendiadiem, String matinh, String mahuyen) {
        this.madiadiem = madiadiem;
        this.tendiadiem = tendiadiem;
        this.matinh = matinh;
        this.mahuyen = mahuyen;
    }
}
