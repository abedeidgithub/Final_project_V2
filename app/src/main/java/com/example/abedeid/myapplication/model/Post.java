package com.example.abedeid.myapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abed Eid on 2/28/2017.
 */

public class Post {


    @SerializedName("id")
    public String id;
    @SerializedName("ask_or_post_text")
    public String ask_or_post_text;
    @SerializedName("user_id")
    public String user_id;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("ask_or_post_flag")
    public String ask_or_post_flag;
    @SerializedName("year")
    public String year;
    @SerializedName("department")
    public String department;
    @SerializedName("section")
    public String section;
    @SerializedName("comments")
    public int comments;
    @SerializedName("name")
    public String name;
    @SerializedName("image")
    public String image;
    @SerializedName("success")
    public boolean success;
    @SerializedName("message")
    public String message;
}
