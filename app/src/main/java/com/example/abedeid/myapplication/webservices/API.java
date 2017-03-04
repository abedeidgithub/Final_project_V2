package com.example.abedeid.myapplication.webservices;


import com.example.abedeid.myapplication.model.CommentModel;
import com.example.abedeid.myapplication.model.MainResponse;
import com.example.abedeid.myapplication.model.Post;
import com.example.abedeid.myapplication.model.Schedule;
import com.example.abedeid.myapplication.model.users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by abed_eid on 08/12/2016.
 */

public interface API {


    @POST("login.php")
    Call<List<MainResponse>> loginUsers(@Body users users);

    @POST("insertUser.php")
    Call<MainResponse> registerStudent(@Body users users);

    @POST("ask_or_post.php")
    Call<List<Post>> Posts(@Body users users);

    @POST("answer_or_comment.php")
    Call<List<CommentModel>> Commrnts(@Body CommentModel commentModel);


//    @POST("upload_img.php")
//    Call<List<MainResponse>> loginStudent(@Body sentBody body);





    @POST("select-users.php")
    Call<List<users>> getStudent(@Body users users);

    @POST("select-schedule.php")
    Call<List<Schedule>> getSchedule(@Body Schedule schedule);

}
