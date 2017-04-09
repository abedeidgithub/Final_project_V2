package com.example.abedeid.myapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abed Eid on 2/28/2017.
 */

public class Post {

    @SerializedName("id_post")
    public int id_post;
    @SerializedName("text")
    public String text;
    @SerializedName("post_image")
    public String post_image;
    @SerializedName("createdat")
    public String createdat;
    @SerializedName("updatedat")
    public String updatedat;
    @SerializedName("section_id")
    public int section_id;
    @SerializedName("depart_id")
    public int depart_id;
    @SerializedName("year_id")
    public int year_id;
    @SerializedName("name")
    public String name;
    @SerializedName("user_image")
    public String user_image;
    @SerializedName("user_id")
    public int user_id;
    @SerializedName("isAdmin")
    public int isAdmin;
}
