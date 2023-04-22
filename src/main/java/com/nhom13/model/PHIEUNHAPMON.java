package com.nhom13.model;

public class PHIEUNHAPMON {
    private int ID;
    private String tenMonAn;
    private int soLuong;
    private int thanhTien;

    public PHIEUNHAPMON() {
    }

    public PHIEUNHAPMON(int ID, String tenMonAn, int soLuong, int thanhTien) {
        this.ID = ID;
        this.tenMonAn = tenMonAn;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }

    
}
