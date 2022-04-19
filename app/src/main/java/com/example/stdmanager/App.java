package com.example.stdmanager;

import android.app.Application;

import com.example.stdmanager.models.Teacher;

public class App  extends Application {

    private Teacher gv = null;

    public Teacher getTeacher() {
        return gv;
    }

    public void setTeacher(Teacher gv) {
        this.gv = gv;
    }
}
