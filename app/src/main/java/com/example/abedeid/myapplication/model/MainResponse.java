package com.example.abedeid.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainResponse {

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
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("message")
    @Expose
    private String message;


    public Boolean getStatus() {
        return success;
    }


    public void setStatus(Boolean status) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }




}