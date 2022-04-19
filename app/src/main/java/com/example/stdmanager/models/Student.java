package com.example.stdmanager.models;

import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable {
    private int id;
    private String familyName;
    private String firstName;
    private int gender = 0;// male by default
    private String birthday;
    private int gradeId = 1;// grade Id = 1 by default
    private String gradeName;// temporary Solution

    public Student() {
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public Student(String familyName, String firstName, int gender, String birthday, int gradeId) {
        this.familyName = familyName;
        this.firstName = firstName;
        this.gender = gender;
        this.birthday = birthday;
        this.gradeId = gradeId;
    }

    public Student(String familyName, String firstName, String birthday) {
        this.familyName = familyName;
        this.firstName = firstName;
        this.birthday = birthday;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
