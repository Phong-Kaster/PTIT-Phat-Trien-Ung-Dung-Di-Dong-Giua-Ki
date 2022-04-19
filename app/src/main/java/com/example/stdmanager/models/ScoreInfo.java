package com.example.stdmanager.models;

import java.io.Serializable;

public class ScoreInfo implements Serializable {
    int studentID, subjectID;
        double score;
    String studentFullName, subjectName;

    public ScoreInfo() {
    }

    public ScoreInfo(int studentID, int subjectID, double score, String studentFullName, String subjectName) {
        this.studentID = studentID;
        this.subjectID = subjectID;
        this.score = score;
        this.studentFullName = studentFullName;
        this.subjectName = subjectName;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getStudentFullName() {
        return studentFullName;
    }

    public void setStudentFullName(String studentFullName) {
        this.studentFullName = studentFullName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
