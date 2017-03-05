package com.example.abedeid.myapplication.model;

/**
 * Created by abed_eid on 03/12/2016.
 */

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class users extends RealmObject {


    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;
    @SerializedName("user_flage")
    public String user_flage;
    @SerializedName("password")
    public String password;
    @SerializedName("image")
    public String image;
    @SerializedName("department")
    public String department;
    @SerializedName("section")
    public String section;
    @SerializedName("year")
    public String year;
    @SerializedName("remeber_token")
    public String remeber_token;
    @SerializedName("user_token")
    public String user_token;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("user_site")
    public String user_site;
    @SerializedName("verify")
    public String verify;

public  int Page;

}