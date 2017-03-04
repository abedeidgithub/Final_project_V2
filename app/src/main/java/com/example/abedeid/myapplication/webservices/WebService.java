package com.example.abedeid.myapplication.webservices;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abed_eid on 08/12/2016.
 */

public class WebService {

    public static WebService instance;
    public API api;

    public WebService() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder().client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Urls.WEB_URL)
                .build();
        api = retrofit.create(API.class);

    }

    public static WebService getInstance() {
        if (instance == null) {
            instance = new WebService();
        }
        return instance;
    }

    public API getApi() {
        return api;
    }
}
