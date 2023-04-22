package com.nhom13.model;


public class PHIEUNHAP {
    private int ID;
    private String NGAYNHAP;

    public PHIEUNHAP() {
    }

    public PHIEUNHAP(int ID, String NGAYNHAP) {
        this.ID = ID;
        this.NGAYNHAP = NGAYNHAP;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNGAYNHAP() {
        return NGAYNHAP;
    }

    public void setNGAYNHAP(String NGAYNHAP) {
        this.NGAYNHAP = NGAYNHAP;
    }
}
