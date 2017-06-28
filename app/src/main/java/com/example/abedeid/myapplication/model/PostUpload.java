package com.example.abedeid.myapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abed Eid on 6/25/2017.
 */

public class PostUpload {


    @SerializedName("depart_id")
    public String depart_id;
    @SerializedName("section_id")
    public String section_id;
    @SerializedName("year_id")
    public String year_id;
    @SerializedName("users_id")
    public String users_id;
    @SerializedName("text")
    public String text;
    @SerializedName("image")
    public String image;
    @SerializedName("subject_id")
    public String subject_id;


}
