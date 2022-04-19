package com.example.stdmanager.models;

public class Grade {
    private int id;
    private String name;
    private int teacherId;

    public Grade() {
    }

    public Grade(int id, String name, int teacherId) {
        this.id = id;
        this.name = name;
        this.teacherId = teacherId;
    }

    public Grade(String name, int teacherId) {
        this.name = name;
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
