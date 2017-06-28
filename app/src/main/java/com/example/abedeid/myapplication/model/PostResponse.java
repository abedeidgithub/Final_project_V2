package com.example.abedeid.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Abed Eid on 6/22/2017.
 */

public class PostResponse {

    @SerializedName("success")
    @Expose
    private int success;
    @SerializedName("message")
    @Expose
    private String message;


    public int  getStatus() {
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
