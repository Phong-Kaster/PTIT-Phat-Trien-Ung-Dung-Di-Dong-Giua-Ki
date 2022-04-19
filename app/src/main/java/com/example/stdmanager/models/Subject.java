package com.example.stdmanager.models;

import java.io.Serializable;

public class Subject implements Serializable {

    int MaMH,HocKy,HeSo;
    String TenMH,NamHoc;


    public Subject(int maMH, String tenMH, int hocKy,int heSo,String namHoc) {
        this.MaMH = maMH;
        this.HocKy = hocKy;
        this.HeSo = heSo;
        this.TenMH = tenMH;
        this.NamHoc = namHoc;

    }
    public Subject() {

    }

    public String getNamHoc() {
        return NamHoc;
    }

    public void setNamHoc(String namHoc) {
        NamHoc = namHoc;
    }

    public String getTenMH() {
        return TenMH;
    }

    public void setTenMH(String tenMH) {
        TenMH = tenMH;
    }

    public int getHeSo() {
        return HeSo;
    }

    public void setHeSo(int heSo) {
        HeSo = heSo;
    }

    public int getHocKy() {
        return HocKy;
    }

    public void setHocKy(int hocKy) {
        HocKy = hocKy;
    }

    public int getMaMH() {
        return MaMH;
    }

    public void setMaMH(int maMH) {
        MaMH = maMH;
    }

}
