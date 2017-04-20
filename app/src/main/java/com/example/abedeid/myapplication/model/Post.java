package com.example.abedeid.myapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abed Eid on 2/28/2017.
 */

public class Post {

    @SerializedName("id_post")
    public String id_post;
    @SerializedName("text")
    public String text;
    @SerializedName("post_image")
    public String post_image;
    @SerializedName("createdat")
    public String createdat;
    @SerializedName("updatedat")
    public String updatedat;
    @SerializedName("section_id")
    public String section_id;
    @SerializedName("depart_id")
    public String depart_id;
    @SerializedName("year_id")
    public String year_id;
    @SerializedName("name")
    public String name;
    @SerializedName("user_image")
    public String user_image;
    @SerializedName("users_id")
    public String users_id;
    @SerializedName("isAdmin")
    public String isAdmin;
}
