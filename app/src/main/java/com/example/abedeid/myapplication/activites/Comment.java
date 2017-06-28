package com.example.abedeid.myapplication.activites;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.abedeid.myapplication.Fragments.PostOrAsk;
import com.example.abedeid.myapplication.R;
import com.example.abedeid.myapplication.adapters.CommentAdapter;
import com.example.abedeid.myapplication.fcm.FirebaseService;
import com.example.abedeid.myapplication.model.CommentModel;
import com.example.abedeid.myapplication.model.CommentUpload;
import com.example.abedeid.myapplication.model.MainResponse;
import com.example.abedeid.myapplication.model.Section;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Comment extends Activity {

    ProgressDialog progressDialog;
    private CommentAdapter adapter;
    RecyclerView recycler_view;
    EditText comment_txt;
    ImageButton insertComment;
    List<CommentModel> commentModels;
    ImageView Upload_image;
    int i=0;
    File file;
    String mediaPath;
    String[] mediaColumns = { MediaStore.Video.Media._ID };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CommentModel Comment = new CommentModel();
        Comment.post_id = getIntent().getStringExtra("Post_ID");
        FirebaseMessaging.getInstance().subscribeToTopic(Session.getInstance().getUser().users_id+Comment.post_id);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view_comments);
        insertComment = (ImageButton) findViewById(R.id.insertComment);
        comment_txt = (EditText) findViewById(R.id.comment_txt);
        Upload_image=(ImageView)findViewById(R.id.imageView2);
        Upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);

                i=1;
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Comment.this);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());

        Toast.makeText(this, Comment.post_id, Toast.LENGTH_SHORT).show();
         getCommentsOfPages(Comment);
        final users user = Session.getInstance().getUser();


        insertComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentUpload comment = new CommentUpload();
                comment.txt = comment_txt.getText().toString();
                comment.post_id = getIntent().getStringExtra("Post_ID");
                comment.user_id = Integer.parseInt(user.users_id);
                if(i==1) {
                    uploadFile();
                    comment.image=file.getName();
                }
                WebService.getInstance().getApi().insert_Comment(comment).enqueue(new Callback<MainResponse>() {
                    @Override
                    public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                        finish();
                        startActivity(getIntent());
                    }

                    @Override
                    public void onFailure(Call<MainResponse> call, Throwable t) {
                        Toast.makeText(Comment.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                Upload_image.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();

            } else {
                Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    private void uploadFile() {



        file = new File(mediaPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        WebService.getInstance().getApi().uploadFile(fileToUpload, filename).
                enqueue(new Callback<MainResponse>() {
                    @Override
                    public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                        MainResponse serverResponse = response.body();
                        if (serverResponse != null) {
                            if (serverResponse.getStatus()) {
                                Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            assert serverResponse != null;
                            Log.v("Response", serverResponse.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<MainResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getBaseContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_right);


    }

    private void getCommentsOfPages(CommentModel post) {
        WebService.getInstance().getApi().getComments(post).enqueue(new Callback<List<CommentModel>>() {
            @Override
            public void onResponse(Call<List<CommentModel>> call, Response<List<CommentModel>> response) {
                List<CommentModel> commentModels = response.body();

                adapter = new CommentAdapter(commentModels, Comment.this);

                recycler_view.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CommentModel>> call, Throwable t) {
                Toast.makeText(Comment.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
