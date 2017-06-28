package com.example.abedeid.myapplication.model;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

import java.security.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Abed Eid on 4/8/2017.
 */

public class news implements Parcelable {
    public static final Creator<news> CREATOR = new Creator<news>() {
        @Override
        public news createFromParcel(Parcel in) {
            return new news(in);
        }

        @Override
        public news[] newArray(int size) {
            return new news[size];
        }
    };
    @SerializedName("id")
    public String id;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(id);
        dest.writeString(createdat);
        dest.writeString(updatedat);
        dest.writeString(news_txt);
        dest.writeString(news_img);

    }

    public news() {
    }

    protected news(Parcel in) {
        id = in.readString();
        news_txt = in.readString();
        updatedat = in.readString();
        createdat = in.readString();
        news_img = in.readString();

    }
}
