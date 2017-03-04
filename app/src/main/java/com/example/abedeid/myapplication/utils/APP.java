package com.example.abedeid.myapplication.utils;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by abed_eid on 09/12/2016.
 */

public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
