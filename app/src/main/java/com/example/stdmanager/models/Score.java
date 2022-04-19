package com.example.stdmanager.models;

public class Score {
    int maHS, maMH;
    double diem;

    public Score() {

    }

    public Score(int maHS, int maMH, double diem) {
        this.maHS = maHS;
        this.maMH = maMH;
        this.diem = diem;
    }

    public int getMaHS() {
        return maHS;
    }

    public void setMaHS(int maHS) {
        this.maHS = maHS;
    }

    public int getMaMH() {
        return maMH;
    }

    public void setMaMH(int maMH) {
        this.maMH = maMH;
    }

    public double getDiem() {
        return diem;
    }

    public void setDiem(double diem) {
        this.diem = diem;
    }
}
