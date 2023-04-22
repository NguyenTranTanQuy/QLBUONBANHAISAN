package com.nhom13.model;

public class MONAN {

    private int ID;
    private String TENMONAN;
    private int GIA;
    private String MAHS;
    private String HINHANH;

    public MONAN() {
    }

    public MONAN(int ID, String TENMONAN, int GIA, String MAHS, String HINHANH) {
        this.ID = ID;
        this.TENMONAN = TENMONAN;
        this.GIA = GIA;
        this.MAHS = MAHS;
        this.HINHANH = HINHANH;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTENMONAN() {
        return TENMONAN;
    }

    public void setTENMONAN(String TENMONAN) {
        this.TENMONAN = TENMONAN;
    }

    public int getGIA() {
        return GIA;
    }

    public void setGIA(int GIA) {
        this.GIA = GIA;
    }

    public String getMAHS() {
        return MAHS;
    }

    public void setMAHS(String MAHS) {
        this.MAHS = MAHS;
    }

    public String getHINHANH() {
        return HINHANH;
    }

    public void setHINHANH(String HINHANH) {
        this.HINHANH = HINHANH;
    }
}
