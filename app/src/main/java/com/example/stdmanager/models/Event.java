package com.example.stdmanager.models;

import java.io.Serializable;

public class Event implements Serializable {

    private int idEvent;
    private String nameEvent;
    private String startTime;
    private String endTime;
    private String day;
    private String place;

    public Event(int idEvent, String nameEvent, String startTime, String endTime, String day, String place) {
        this.idEvent=idEvent;
        this.nameEvent=nameEvent;
        this.startTime=startTime;
        this.endTime=endTime;
        this.day=day;
        this.place=place;
    }
    public Event(){

    }
    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
