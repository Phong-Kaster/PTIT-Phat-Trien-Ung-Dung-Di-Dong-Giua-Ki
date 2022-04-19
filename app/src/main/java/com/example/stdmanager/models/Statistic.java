package com.example.stdmanager.models;

import java.io.Serializable;

public class Statistic implements Serializable {
    private Integer id;
    private String title;
    private String text;

    public Statistic() {
    }

    public Statistic(Integer id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
