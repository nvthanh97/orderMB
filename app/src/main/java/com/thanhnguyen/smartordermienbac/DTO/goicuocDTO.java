package com.thanhnguyen.smartordermienbac.DTO;

public class goicuocDTO {
    int id;
    String tengoi;
    String giagoi;

    public goicuocDTO() {
    }

    public goicuocDTO(int id, String tengoi, String giagoi) {
        this.id = id;
        this.tengoi = tengoi;
        this.giagoi = giagoi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTengoi() {
        return tengoi;
    }

    public void setTengoi(String tengoi) {
        this.tengoi = tengoi;
    }

    public String getGiagoi() {
        return giagoi;
    }

    public void setGiagoi(String giagoi) {
        this.giagoi = giagoi;
    }
}
