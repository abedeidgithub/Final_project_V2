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
    @BindView(R.id.post_department)
    Spinner _depart;
    @BindView(R.id.post_section)
    Spinner _section;
    @BindView(R.id.pdialog)
    ProgressBar pdBar;
    List<String> section = new ArrayList<String>();
    List<String> depart = new ArrayList<String>();
    @BindView(R.id.btn_w_post)
    Button btn_w_post;
    @BindView(R.id.post_w_t)
    EditText post_w_t;
    TextView addimage;
    ImageView image;
    ImageView Upload_image;

    String mediaPath;
     String[] mediaColumns = { MediaStore.Video.Media._ID };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        imageutils = new Imageutils(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write__post_);
        ButterKnife.bind(this);
        initCustomSpinner();
        image = (ImageView) findViewById(R.id.post_w_i);
        Upload_image = (ImageView) findViewById(R.id.Upload_image);
        final TextView name = (TextView) findViewById(R.id.post_w_n);
        addimage = (TextView) findViewById(R.id.addimage);
        final users users = Session.getInstance().getUser();
        if (users != null) {
            name.setText(users.name);
            Glide.with(getApplicationContext())
                    .load(Urls.Local_images + users.image)
                    .into(image);
        }


        btn_w_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdBar.setIndeterminate(true);



                String txt = post_w_t.getText().toString().trim();
                if (!txt.isEmpty()) {

                    PostUpload post = new PostUpload();
                    post.users_id = users.idstudent;
                    post.text = txt;
                    post.year_id = users.year_id;
                    post.depart_id = users.depart_id;
                    post.section_id = users.section_id;
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


        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addimage.setVisibility(View.GONE);
//                imageutils.imagepicker(1);
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                 startActivityForResult(galleryIntent, 0);

i=1;
                Upload_image.setVisibility(View.VISIBLE);
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
        section.add("section");
        depart.add("depart");


        // Spinner Drop down elements
        final List<String> year = new ArrayList<String>();
        year.add("year");
        WebService.getInstance().getApi().getYear().enqueue(new Callback<List<com.example.abedeid.myapplication.model.year>>() {

            @Override
            public void onResponse(Call<List<year>> call, Response<List<year>> response) {
                for (byte i = 0; i < response.body().size(); i++)
                    year.add(response.body().get(i).year_name + "");
            }

            @Override
            public void onFailure(Call<List<year>> call, Throwable t) {
//                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, year);
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        _year.setAdapter(dataAdapter);
        _year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yearData = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        WebService.getInstance().getApi().getsection().enqueue(new Callback<List<com.example.abedeid.myapplication.model.Section>>() {
            @Override
            public void onResponse(Call<List<Section>> call, Response<List<Section>> response) {
                for (byte i = 0; i < response.body().size(); i++)
                    section.add(response.body().get(i).section_name + "");
            }

            @Override
            public void onFailure(Call<List<Section>> call, Throwable t) {
            }
        });
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, section);
        dataAdapter3.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        _section.setAdapter(dataAdapter3);
        _section.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sectionData = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        WebService.getInstance().getApi().getdepartment().enqueue(new Callback<List<com.example.abedeid.myapplication.model.Department>>() {
            @Override
            public void onResponse(Call<List<Department>> call, Response<List<Department>> response) {
                for (byte i = 0; i < response.body().size(); i++)
                    depart.add(response.body().get(i).dept_name + "");
            }

            @Override
            public void onFailure(Call<List<Department>> call, Throwable t) {
            }
        });
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, depart);
        dataAdapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        _depart.setAdapter(dataAdapter2);
        _depart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                departData = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }



}
