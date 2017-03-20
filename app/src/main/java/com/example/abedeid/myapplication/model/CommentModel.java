package com.example.abedeid.myapplication.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;



/**
 * Created by Abed Eid on 3/2/2017.
 */

public class CommentModel {

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
    @SerializedName("id")
    public String id;
    @SerializedName("user_id")
    public String user_id;
    @SerializedName("answer_or_comment_text")
    public String answer_or_comment_text;
    @SerializedName("ask_or_post_id")
    public String ask_or_post_id;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("name")
    public String name;
    @SerializedName("image")
    public String image;
    @SerializedName("success")
    public boolean success;
    @SerializedName("message")
    public String message;
    @SerializedName("type")
    public String type;


}
