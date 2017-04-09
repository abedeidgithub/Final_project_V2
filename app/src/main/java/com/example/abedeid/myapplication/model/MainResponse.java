package com.example.abedeid.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainResponse {


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