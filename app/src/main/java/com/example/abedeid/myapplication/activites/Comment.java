package com.example.abedeid.myapplication.activites;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.abedeid.myapplication.Fragments.PostOrAsk;
import com.example.abedeid.myapplication.R;
import com.example.abedeid.myapplication.adapters.CommentAdapter;
import com.example.abedeid.myapplication.fcm.FirebaseService;
import com.example.abedeid.myapplication.model.CommentModel;
import com.example.abedeid.myapplication.model.users;
import com.example.abedeid.myapplication.utils.Session;
import com.example.abedeid.myapplication.webservices.WebService;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Comment extends Activity {

    private CommentAdapter adapter;
    RecyclerView recycler_view;
    EditText comment_txt;
    ImageButton insertComment;
    List<CommentModel> commentModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view_comments);
        insertComment = (ImageButton) findViewById(R.id.insertComment);
        comment_txt = (EditText) findViewById(R.id.comment_txt);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Comment.this);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        CommentModel Comment = new CommentModel();
        Comment.post_id = getIntent().getStringExtra("Post_ID");
        getCommentsOfPages(Comment);
        final users user = Session.getInstance().getUser();
       // DownloadFromUrl("http://fci-suze.esy.es/Webservices/uploads/FB_IMG_1457572882947.jpg","FB_IMG.jpg");

        insertComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentModel comment = new CommentModel();
                comment.txt = comment_txt.getText().toString();
                comment.post_id = getIntent().getStringExtra("Post_ID");
//                comment.user_id = user.id;
                WebService.getInstance().getApi().insert_Comment(comment).enqueue(new Callback<CommentModel>() {
                    @Override
                    public void onResponse(Call<CommentModel> call, Response<CommentModel> response) {
                        finish();
                        startActivity(getIntent());
                    }

                    @Override
                    public void onFailure(Call<CommentModel> call, Throwable t) {
                        Toast.makeText(Comment.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });



        Log.e("TAAAAAG", FirebaseInstanceId.getInstance().getToken());
        FirebaseMessaging.getInstance().subscribeToTopic(Comment.post_id);
        Log.e("TAAAAAG",Comment.txt);
    }





    ///

    public void DownloadFromUrl(String DownloadUrl, String fileName) {

        try {
            File root = android.os.Environment.getExternalStorageDirectory();

            File dir = new File (root.getAbsolutePath() + "/xmls");
            if(dir.exists()==false) {
                dir.mkdirs();
            }

            URL url = new URL(DownloadUrl); //you can write here any link
            File file = new File(dir, fileName);

            long startTime = System.currentTimeMillis();
            Log.d("DownloadManager", "download begining");
            Log.d("DownloadManager", "download url:" + url);
            Log.d("DownloadManager", "downloaded file name:" + fileName);

           /* Open a connection to that URL. */
            URLConnection ucon = url.openConnection();


            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            FileOutputStream fos = new FileOutputStream(file);

            int current = 0;
            while ((current = bis.read()) != -1) {
                fos.write(current);
            }

            fos.close();
            Log.d("DownloadManager", "download ready in" + ((System.currentTimeMillis() - startTime) / 1000) + " sec");

        } catch (IOException e) {
            Log.d("DownloadManager", "Error: " + e);
        }

    }










    //
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_right);


    }

    private void getCommentsOfPages(CommentModel post) {
        WebService.getInstance().getApi().comments(post).enqueue(new Callback<List<CommentModel>>() {
            @Override
            public void onResponse(Call<List<CommentModel>> call, Response<List<CommentModel>> response) {
                List<CommentModel> commentModels = response.body();

                adapter = new CommentAdapter(commentModels, Comment.this);

                recycler_view.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CommentModel>> call, Throwable t) {
                Toast.makeText(Comment.this, "error users is null  ", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
