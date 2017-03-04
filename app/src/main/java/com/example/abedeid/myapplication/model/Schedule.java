package com.example.abedeid.myapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by abed_eid on 10/12/2016.
 */

public class Schedule {


    @SerializedName("id")
    public String id;
    @SerializedName("Schedule_day")
    public String Schedule_day;
    @SerializedName("year")
    public String year;
    @SerializedName("depart")
    public String depart;
    @SerializedName("place")
    public String place;
    @SerializedName("section")
    public String section;
    @SerializedName("Schedule_from")
    public String Schedule_from;
    @SerializedName("Schedule_to")
    public String Schedule_to;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("upadated_at")
    public String upadated_at;
    @SerializedName("doctor")
    public String doctor;
    @SerializedName("subject")
    public String subject;

    public Schedule(String year, String depart, String section) {
        this.year = year;
        this.depart = depart;
        this.section = section;
    }

    public Schedule(String id, String schedule_day, String year, String depart, String place, String section, String schedule_from, String schedule_to, String created_at, String upadated_at, String doctor, String subject) {
        this.id = id;
        Schedule_day = schedule_day;
        this.year = year;
        this.depart = depart;
        this.place = place;
        this.section = section;
        Schedule_from = schedule_from;
        Schedule_to = schedule_to;
        this.created_at = created_at;
        this.upadated_at = upadated_at;
        this.doctor = doctor;
        this.subject = subject;
    }
}
