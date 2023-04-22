
package com.nhom13.model;

public class NHANVIEN {
   private String MANV;
   private String TENNV;
   private String NGAYSINH;
   private String SDT;
   private String GIOITINH;
   private String CHUCVU;
   private String HINHANH;

    public NHANVIEN() {
    }

    public NHANVIEN(String MANV, String TENNV, String NGAYSINH, String SDT, String GIOITINH, String CHUCVU, String HINHANH) {
        this.MANV = MANV;
        this.TENNV = TENNV;
        this.NGAYSINH = NGAYSINH;
        this.SDT = SDT;
        this.GIOITINH = GIOITINH;
        this.CHUCVU = CHUCVU;
        this.HINHANH = HINHANH;
    }

    public String getMANV() {
        return MANV;
    }

    public void setMANV(String MANV) {
        this.MANV = MANV;
    }

    public String getTENNV() {
        return TENNV;
    }

    public void setTENNV(String TENNV) {
        this.TENNV = TENNV;
    }

    public String getNGAYSINH() {
        return NGAYSINH;
    }

    public void setNGAYSINH(String NGAYSINH) {
        this.NGAYSINH = NGAYSINH;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getGIOITINH() {
        return GIOITINH;
    }

    public void setGIOITINH(String GIOITINH) {
        this.GIOITINH = GIOITINH;
    }

    public String getCHUCVU() {
        return CHUCVU;
    }

    public void setCHUCVU(String CHUCVU) {
        this.CHUCVU = CHUCVU;
    }

    public String getHINHANH() {
        return HINHANH;
    }

    public void setHINHANH(String HINHANH) {
        this.HINHANH = HINHANH;
    }         
}
