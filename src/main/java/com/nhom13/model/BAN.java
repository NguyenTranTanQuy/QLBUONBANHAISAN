package com.nhom13.model;

public class BAN {
    private int SOBAN;
    private String TENBAN;
    private String TRANGTHAI;

    public BAN() {
    }

    public BAN(int SOBAN, String TENBAN, String TRANGTHAI) {
        this.SOBAN = SOBAN;
        this.TENBAN = TENBAN;
        this.TRANGTHAI = TRANGTHAI;
    }

    public int getSOBAN() {
        return SOBAN;
    }

    public void setSOBAN(int SOBAN) {
        this.SOBAN = SOBAN;
    }

    public String getTENBAN() {
        return TENBAN;
    }

    public void setTENBAN(String TENBAN) {
        this.TENBAN = TENBAN;
    }

    public String getTRANGTHAI() {
        return TRANGTHAI;
    }

    public void setTRANGTHAI(String TRANGTHAI) {
        this.TRANGTHAI = TRANGTHAI;
    }  
}
