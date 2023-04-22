package com.nhom13.model;

public class HOADON {
    private int ID;
    private int GIAMGIA;
    private int SOBAN;
    private String NGAYLAPHOADON;

    public HOADON() {
    }

    public HOADON(int ID, int GIAMGIA, int SOBAN, String NGAYLAPHOADON) {
        this.ID = ID;
        this.GIAMGIA = GIAMGIA;
        this.SOBAN = SOBAN;
        this.NGAYLAPHOADON = NGAYLAPHOADON;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getGIAMGIA() {
        return GIAMGIA;
    }

    public void setGIAMGIA(int GIAMGIA) {
        this.GIAMGIA = GIAMGIA;
    }

    public int getSOBAN() {
        return SOBAN;
    }

    public void setSOBAN(int SOBAN) {
        this.SOBAN = SOBAN;
    }

    public String getNGAYLAPHOADON() {
        return NGAYLAPHOADON;
    }

    public void setNGAYLAPHOADON(String NGAYLAPHOADON) {
        this.NGAYLAPHOADON = NGAYLAPHOADON;
    }
}
