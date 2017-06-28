package com.example.abedeid.myapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abed Eid on 6/25/2017.
 */

public class CommentUpload {

    @SerializedName("post_id")
    public String post_id;
    @SerializedName("txt")
    public String txt;
    @SerializedName("image")
    public String image;

    @SerializedName("user_id")
    public int user_id;


}
