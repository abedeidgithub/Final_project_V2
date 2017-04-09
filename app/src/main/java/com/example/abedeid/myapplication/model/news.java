package com.example.abedeid.myapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abed Eid on 4/8/2017.
 */

public class news {

    @SerializedName("id")
    public int id;
    @SerializedName("news_txt")
    public String news_txt;
    @SerializedName("news_img")
    public String news_img;
    @SerializedName("news_view")
    public String news_view;
    @SerializedName("id_admin")
    public int id_admin;
    @SerializedName("createdat")
    public String createdat;
    @SerializedName("updatedat")
    public String updatedat;
}
