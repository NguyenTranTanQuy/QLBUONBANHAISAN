//@Author: NGUYỄN TRẦN TẤN QUY PTITHCM
package com.nhom13.model;

public class BANDAT {

    private int ID;
    private String TENKHACHHANG;
    private String SDT;
    private String NGAYDAT;
    private String THOIGIANDAT;
    private int SOLUONGNGUOI;
    private String BANSO;
    private String TRANGTHAI;
    private String GHICHU;

    public BANDAT() {
    }

    public BANDAT(int ID, String TENKHACHHANG, String SDT, String NGAYDAT, String THOIGIANDAT, int SOLUONGNGUOI, String BANSO, String TRANGTHAI, String GHICHU) {
        this.ID = ID;
        this.TENKHACHHANG = TENKHACHHANG;
        this.SDT = SDT;
        this.NGAYDAT = NGAYDAT;
        this.THOIGIANDAT = THOIGIANDAT;
        this.SOLUONGNGUOI = SOLUONGNGUOI;
        this.BANSO = BANSO;
        this.TRANGTHAI = TRANGTHAI;
        this.GHICHU = GHICHU;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTENKHACHHANG() {
        return TENKHACHHANG;
    }

    public void setTENKHACHHANG(String TENKHACHHANG) {
        this.TENKHACHHANG = TENKHACHHANG;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getNGAYDAT() {
        return NGAYDAT;
    }

    public void setNGAYDAT(String NGAYDAT) {
        this.NGAYDAT = NGAYDAT;
    }

    public String getTHOIGIANDAT() {
        return THOIGIANDAT;
    }

    public void setTHOIGIANDAT(String THOIGIANDAT) {
        this.THOIGIANDAT = THOIGIANDAT;
    }

    public int getSOLUONGNGUOI() {
        return SOLUONGNGUOI;
    }

    public void setSOLUONGNGUOI(int SOLUONGNGUOI) {
        this.SOLUONGNGUOI = SOLUONGNGUOI;
    }

    public String getBANSO() {
        return BANSO;
    }

    public void setBANSO(String BANSO) {
        this.BANSO = BANSO;
    }

    public String getTRANGTHAI() {
        return TRANGTHAI;
    }

    public void setTRANGTHAI(String TRANGTHAI) {
        this.TRANGTHAI = TRANGTHAI;
    }

    public String getGHICHU() {
        return GHICHU;
    }

    public void setGHICHU(String GHICHU) {
        this.GHICHU = GHICHU;
    }

}
