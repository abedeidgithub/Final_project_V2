package com.example.abedeid.myapplication.webservices;


import com.example.abedeid.myapplication.Fragments.PostOrAsk;
import com.example.abedeid.myapplication.activites.Comment;
import com.example.abedeid.myapplication.model.CommentModel;
import com.example.abedeid.myapplication.model.Days;
import com.example.abedeid.myapplication.model.Department;
import com.example.abedeid.myapplication.model.MainResponse;
import com.example.abedeid.myapplication.model.Post;
import com.example.abedeid.myapplication.model.Schedule;
import com.example.abedeid.myapplication.model.Section;
import com.example.abedeid.myapplication.model.news;
import com.example.abedeid.myapplication.model.users;
import com.example.abedeid.myapplication.model.year;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by abed_eid on 08/12/2016.
 */

public interface API {


    @POST("selects/selct_user.php")
    Call<List<users>> loginUsers(@Body users users);

    @POST("selects/select_year.php")
    Call<List<year>> getYear();

    @POST(" selects/select_day.php")
    Call<List<Days>> getDay();

    @POST(" selects/select_dept.php")
    Call<List<Department>> getdepartment();

    @POST("selects/select_section.php")
    Call<List<Section>> getsection();

    @POST("insert/insert_user.php")
    Call<MainResponse> registerStudent(@Body users users);

    @POST("selects/select_posts.php")
    Call<List<Post>> Posts(@Body users users);

    @POST("selects/select_comments.php")
    Call<List<CommentModel>> comments(@Body CommentModel commentModel);

    @POST("selects/select_news.php")
    Call<List<news>> getnews(@Body users s);

    @POST("select-users.php")
    Call<List<users>> getStudent(@Body users users);

    @POST("select-schedule.php")
    Call<List<Schedule>> getSchedule(@Body Schedule schedule);


    @POST("insertComment.php")
    Call<CommentModel> insert_Comment(@Body CommentModel commentModel);

    @POST("/Webservices/updateUser.php")
    Call<List<users>> updateUser(@Body users userModel);

}
