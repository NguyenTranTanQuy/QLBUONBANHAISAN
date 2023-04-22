package com.nhom13.model;

public class CTHOADON {
    private int IDHD;
    private int IDMA;
    private int SOLUONG;

    public CTHOADON() {
    }

    public CTHOADON(int IDHD, int IDMA, int SOLUONG) {
        this.IDHD = IDHD;
        this.IDMA = IDMA;
        this.SOLUONG = SOLUONG;
    }

    public int getIDHD() {
        return IDHD;
    }

    public void setIDHD(int IDHD) {
        this.IDHD = IDHD;
    }

    public int getIDMA() {
        return IDMA;
    }

    public void setIDMA(int IDMA) {
        this.IDMA = IDMA;
    }

    public int getSOLUONG() {
        return SOLUONG;
    }

    public void setSOLUONG(int SOLUONG) {
        this.SOLUONG = SOLUONG;
    }
}
