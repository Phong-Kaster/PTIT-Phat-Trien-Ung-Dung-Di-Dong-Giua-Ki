package com.example.stdmanager.models;

public class ReportTotal {
    private String name;
    private Double value;

    public ReportTotal(){

    }

    public ReportTotal(String name, Double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
