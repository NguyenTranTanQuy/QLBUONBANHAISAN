package com.nhom13.model;

public class CTPHIEUNHAP {
    private String MAHS;
    private int IDPN;
    private int SOLUONG;
    private int DONGIA;
    private String DONVI;

    public CTPHIEUNHAP() {
    }

    public CTPHIEUNHAP(String MAHS, int IDPN, int SOLUONG, int DONGIA, String DONVI) {
        this.MAHS = MAHS;
        this.IDPN = IDPN;
        this.SOLUONG = SOLUONG;
        this.DONGIA = DONGIA;
        this.DONVI = DONVI;
    }

    public String getMAHS() {
        return MAHS;
    }

    public void setMAHS(String MAHS) {
        this.MAHS = MAHS;
    }

    public int getIDPN() {
        return IDPN;
    }

    public void setIDPN(int IDPN) {
        this.IDPN = IDPN;
    }

    public int getSOLUONG() {
        return SOLUONG;
    }

    public void setSOLUONG(int SOLUONG) {
        this.SOLUONG = SOLUONG;
    }

    public int getDONGIA() {
        return DONGIA;
    }

    public void setDONGIA(int DONGIA) {
        this.DONGIA = DONGIA;
    }

    public String getDONVI() {
        return DONVI;
    }

    public void setDONVI(String DONVI) {
        this.DONVI = DONVI;
    }
}
