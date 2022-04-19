package com.example.stdmanager.models;

public class ReportScore {
    int maHS;
    double diem;

    public ReportScore() {

    }

    public ReportScore(int maHS, double diem) {
        this.maHS = maHS;
        this.diem = diem;
    }

    public int getMaHS() {
        return maHS;
    }

    public void setMaHS(int maHS) {
        this.maHS = maHS;
    }

    public double getDiem() {
        return diem;
    }

    public void setDiem(double diem) {
        this.diem = diem;
    }
}
