package com.example.abedeid.myapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by abed_eid on 10/12/2016.
 */

public class Schedule {


    @SerializedName("id")
    public int id;
    @SerializedName("from_time")
    public String from_time;
    @SerializedName("to_time")
    public String to_time;
    @SerializedName("day_name")
    public String day_name;
    @SerializedName("place")
    public String place;
    @SerializedName("subject_name")
    public String subject_name;
    @SerializedName("Doctor name ")
    public String Doctor_name ;


    @SerializedName("year_name")
    public String year_name;
    @SerializedName("section_name")
    public String section_name;
    @SerializedName("dept_name")
    public String dept_name;
}
