package com.example.abedeid.myapplication.activites;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import com.example.abedeid.myapplication.R;
import com.example.abedeid.myapplication.adapters.CommentAdapter;
import com.example.abedeid.myapplication.model.CommentModel;
import com.example.abedeid.myapplication.webservices.WebService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Comment extends Activity {

    private CommentAdapter adapter;
    RecyclerView recycler_view;

    List<CommentModel> commentModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view_comments);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Comment.this);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        CommentModel post=new CommentModel();
        post.ask_or_post_id=getIntent().getStringExtra("Post_ID");
        getCommentsOfPages(post);


    }

    private void getCommentsOfPages(CommentModel post) {
        WebService.getInstance().getApi().comments(post).enqueue(new Callback<List<CommentModel>>() {
            @Override
            public void onResponse(Call<List<CommentModel>> call, Response<List<CommentModel>> response) {
                List<CommentModel> commentModels =response.body();

                adapter=new CommentAdapter(commentModels,Comment.this);

                recycler_view.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CommentModel>> call, Throwable t) {
                Toast.makeText(Comment.this, "error users is null  ", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
