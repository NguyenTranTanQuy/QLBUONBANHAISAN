package com.nhom13.model;

public class TAIKHOAN {
    private String MATAIKHOAN;
    private String MATKHAU;
    private String MANV;

    public TAIKHOAN() {
    }

    public TAIKHOAN(String MATAIKHOAN, String MATKHAU, String MANV) {
        this.MATAIKHOAN = MATAIKHOAN;
        this.MATKHAU = MATKHAU;
        this.MANV = MANV;
    }

    public String getMATAIKHOAN() {
        return MATAIKHOAN;
    }

    public void setMATAIKHOAN(String MATAIKHOAN) {
        this.MATAIKHOAN = MATAIKHOAN;
    }

    public String getMATKHAU() {
        return MATKHAU;
    }
    
    public void setMATKHAU(String MATKHAU) {
        this.MATKHAU = MATKHAU;
    }

    public String getMANV() {
        return MANV;
    }

    public void setMANV(String MANV) {
        this.MANV = MANV;
    } 
}
