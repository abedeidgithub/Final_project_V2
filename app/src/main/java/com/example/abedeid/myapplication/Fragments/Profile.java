package com.example.abedeid.myapplication.Fragments;

import android.app.Dialog;
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
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.abedeid.myapplication.R;
import com.example.abedeid.myapplication.model.MainResponse;
import com.example.abedeid.myapplication.model.users;
import com.example.abedeid.myapplication.utils.Session;
import com.example.abedeid.myapplication.webservices.Urls;
import com.example.abedeid.myapplication.webservices.WebService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.example.abedeid.myapplication.R.id.user_profile_photo;


public class Profile extends Fragment {
    Button update;
    users s = new users();
    ImageView user_profile_photo;
    int i=0;
    File file;
    String mediaPath;
    String[] mediaColumns = { MediaStore.Video.Media._ID };

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        user_profile_photo = (ImageView) view.findViewById(R.id.user_profile_photo);
        ImageView header_cover_image = (ImageView) view.findViewById(R.id.header_cover_image);
        TextView user_profile_name = (TextView) view.findViewById(R.id.user_profile_name);
        TextView user_profile_email = (TextView) view.findViewById(R.id.user_profile_email);
        TextView year_Profil = (TextView) view.findViewById(R.id.year_Profil);
        TextView section_Profil = (TextView) view.findViewById(R.id.section_Profil);
        TextView depart_Profil = (TextView) view.findViewById(R.id.depart_Profil);
        final EditText email = (EditText) view.findViewById(R.id.email);
        final EditText name = (EditText) view.findViewById(R.id.name);
        final EditText password = (EditText) view.findViewById(R.id.password);
        update = (Button) view.findViewById(R.id.update);


        final users users = Session.getInstance().getUser();
        if (users != null) {
            year_Profil.setText("year : " + users.year_name);
            section_Profil.setText("section : " + users.section_name);
            depart_Profil.setText("deptarment : " + users.dept_name);
            user_profile_name.setText(users.name);
            user_profile_email.setText(users.email.toString());
            name.setText(users.name);
            email.setText(users.email);
            password.setText(users.password);

            password.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    password.setFocusableInTouchMode(true);
                    update.setEnabled(true);

                }
            });
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    name.setFocusableInTouchMode(true);
                    update.setEnabled(true);


                }
            });
            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    email.setFocusableInTouchMode(true);
                    update.setEnabled(true);
                }
            });


            Glide.with(getContext()).load(Urls.Local_images + users.image).asBitmap().centerCrop().into(new BitmapImageViewTarget(user_profile_photo) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    user_profile_photo.setImageDrawable(circularBitmapDrawable);
                }
            });
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    s.password = password.getText().toString();
                    s.email = email.getText().toString();
                    s.name = name.getText().toString();
                    s.site = users.site;
                    s.about_doctor = users.about_doctor;
                    s.depart_id = users.depart_id;
                    s.idstudent = users.idstudent;
                    s.year_id = users.year_id;
                    s.section_idsection = users.section_idsection;
                    s.year_name = users.year_name;
                    s.section_id = users.section_id;
                    s.section_name = users.section_name;
                    s.person_id_person = users.person_id_person;
                    s.dept_name = users.dept_name;
                    s.users_id = users.users_id;
                    s.image = users.image;
                    s.isAdmin = users.isAdmin;
                    s.verify = users.verify;
                    s.status = users.status;
                    s.message = users.message;
                    if(i==1) {
                        uploadFile();
                        s.image=file.getName();
                    }

                    WebService.getInstance().getApi().updateUser(s).enqueue(new Callback<MainResponse>() {
                        @Override
                        public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                            if (response.body().getStatus() == true) {
                                Session.getInstance().logout();
                                Session.getInstance().loginUser(s);
                                Toast.makeText(getContext(), "done !!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MainResponse> call, Throwable t) {
                            Toast.makeText(getContext(), "not erorr", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            user_profile_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, 0);

                    i=1;

                    update.setEnabled(true);
                }
            });


        } else {
            Toast.makeText(getContext(), "Null", Toast.LENGTH_SHORT).show();


        }


        return view;
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                user_profile_photo.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();

            } else {
                Toast.makeText(getContext(), "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(getContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            assert serverResponse != null;
                            Log.v("Response", serverResponse.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<MainResponse> call, Throwable t) {
                         Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
