package com.example.abedeid.myapplication.model;


import com.google.gson.annotations.SerializedName;



/**
 * Created by Abed Eid on 3/2/2017.
 */

public class CommentModel {

    @SerializedName("id")
    public int id;
    @SerializedName("post_id")
    public String post_id;
    @SerializedName("txt")
    public String txt;
    @SerializedName("image")
    public String image;
    @SerializedName("createdat")
    public String createdat;
    @SerializedName("updatedat")
    public String updatedat;
    @SerializedName("user_id")
    public int user_id;
    @SerializedName("name")
    public String name;
    @SerializedName("user_image")
    public String user_image;
    @SerializedName("isAdmin")
    public int isAdmin;

//    public static final Creator<CommentModel> CREATOR = new Creator<CommentModel>() {
//        @Override
//        public CommentModel createFromParcel(Parcel in) {
//            return new CommentModel(in);
//        }
//
//        @Override
//        public CommentModel[] newArray(int size) {
//            return new CommentModel[size];
//        }
//    };



}
