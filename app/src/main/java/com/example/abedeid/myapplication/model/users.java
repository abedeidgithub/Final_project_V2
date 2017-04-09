package com.example.abedeid.myapplication.model;

/**
 * Created by abed_eid on 03/12/2016.
 */

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class users extends RealmObject {
    @SerializedName("Page")
    public  int Page;
    @SerializedName("idstudent")
    public String idstudent;
    @SerializedName("year_id")
    public int year_id;
    @SerializedName("section_idsection")
    public String section_idsection;
    @SerializedName("year_name")
    public String year_name;
    @SerializedName("section_id")
    public int section_id;
    @SerializedName("section_name")
    public String section_name;
    @SerializedName("doctor_id")
    public String doctor_id;
    @SerializedName("person_id_person")
    public String person_id_person;
    @SerializedName("site")
    public String site;
    @SerializedName("about_doctor")
    public String about_doctor;
    @SerializedName("depart_id")
    public int depart_id;
    @SerializedName("dept_name")
    public String dept_name;
    @SerializedName("users_id")
    public String users_id;
    @SerializedName("name")
    public String name;
    @SerializedName("image")
    public String image;
    @SerializedName("isAdmin")
    public String isAdmin;
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;
    @SerializedName("verify")
    public String verify;
    @SerializedName("status")
    public Boolean status;
    @SerializedName("message")
    public String message;
}