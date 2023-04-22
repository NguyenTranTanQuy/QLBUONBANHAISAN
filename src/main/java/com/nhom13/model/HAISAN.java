package com.nhom13.model;

public class HAISAN {
    private String MAHS;
    private String TENHS;
    private int GIA;
    private String HINHANH;

    public HAISAN() {
    }

    public HAISAN(String MAHS, String TENHS, int GIA, String HINHANH) {
        this.MAHS = MAHS;
        this.TENHS = TENHS;
        this.GIA = GIA;
        this.HINHANH = HINHANH;
    }

    public String getMAHS() {
        return MAHS;
    }

    public void setMAHS(String MAHS) {
        this.MAHS = MAHS;
    }

    public String getTENHS() {
        return TENHS;
    }

    public void setTENHS(String TENHS) {
        this.TENHS = TENHS;
    }

    public int getGIA() {
        return GIA;
    }

    public void setGIA(int GIA) {
        this.GIA = GIA;
    }

    public String getHINHANH() {
        return HINHANH;
    }

    public void setHINHANH(String HINHANH) {
        this.HINHANH = HINHANH;
    }

    
}
