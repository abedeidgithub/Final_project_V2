package com.example.abedeid.myapplication.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.abedeid.myapplication.R;
import com.example.abedeid.myapplication.model.Department;
import com.example.abedeid.myapplication.model.MainResponse;
import com.example.abedeid.myapplication.model.Post;
import com.example.abedeid.myapplication.model.PostUpload;
import com.example.abedeid.myapplication.model.Section;
import com.example.abedeid.myapplication.model.users;
import com.example.abedeid.myapplication.model.year;
import com.example.abedeid.myapplication.utils.Session;
import com.example.abedeid.myapplication.webservices.Urls;
import com.example.abedeid.myapplication.webservices.WebService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.data;

public class Write_Post_Activity extends AppCompatActivity  {
    int i=0;
    File file;
    ProgressDialog progressDialog;
    public static String yearData, departData, sectionData;
    @BindView(R.id.Upload_image)
    ImageView upload;
    @BindView(R.id.post_yaer)
    Spinner _year;
    @BindView(R.id.post_image)
    Spinner  _image;

    @BindView(R.id.btn_w_post)
    Button btn_w_post;
    @BindView(R.id.post_w_t)
    EditText post_w_t;
    TextView addimage;
    ImageView image;
    ImageView Upload_image;
    final users users = Session.getInstance().getUser();

    String mediaPath;
     String[] mediaColumns = { MediaStore.Video.Media._ID };
    Post post = new Post();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write__post_);
        ButterKnife.bind(this);
        initCustomSpinner();
        image = (ImageView) findViewById(R.id.post_w_i);
        Upload_image = (ImageView) findViewById(R.id.Upload_image);
        final TextView name = (TextView) findViewById(R.id.post_w_n);
        if (users != null) {
            name.setText(users.name);
            Glide.with(getApplicationContext())
                    .load(Urls.Local_images + users.image)
                    .into(image);
        }


        btn_w_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String txt = post_w_t.getText().toString().trim();
                if (!txt.isEmpty() || i!=0) {


                    post.users_id = users.users_id;
                    post.text = txt;

                    if(i==1) {
                        uploadFile();
                        post.image=file.getName();
                    }


                    Log.d("ttt",post.toString());

                    WebService.getInstance().getApi().insert_Post(post).enqueue(new Callback<MainResponse>() {
                        @Override
                        public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                            MainResponse mainResponses = response.body();
                            if (mainResponses.getStatus() == true) {

                                Toast.makeText(Write_Post_Activity.this, "Done", Toast.LENGTH_SHORT).show();
                                Intent in = new Intent(Write_Post_Activity.this, HomeActivity.class);
                                startActivity(in);

                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<MainResponse> call, Throwable t) {
                            Toast.makeText(Write_Post_Activity.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    });


                   
                } else {

                    Toast.makeText(Write_Post_Activity.this, "Insert Text .....", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
             if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
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


 ;
        // Map is used to multipart the file using okhttp3.RequestBody
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
                        Toast.makeText(Write_Post_Activity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initCustomSpinner() {



         final List<String> year = new ArrayList<String>();
        year.add("Post");
        year.add("ask");
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, year);
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        _year.setAdapter(dataAdapter);
        _year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position ==1){
                     post.type=1+"";
                    post.depart_id=users.depart_id;
                    post.section_id=null;
                    post.year_id=null;
                }else{
                     post.type=0+"";
                    post.year_id = users.year_id;
                    post.depart_id = users.depart_id;
                    post.section_id = users.section_id;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
         final List<String> post_image = new ArrayList<String>();
        post_image.add("no image");
        post_image.add("with image");
        final ArrayAdapter<String> post_imageAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, post_image);
        post_imageAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        _image.setAdapter(post_imageAdapter);
        _image.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 if(position ==1){
                     Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, 0);

                    i=1;
                    Upload_image.setVisibility(View.VISIBLE);
                }else{
                    Upload_image.setVisibility(View.GONE);
                    i=0;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }



}
