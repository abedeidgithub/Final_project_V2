package com.example.abedeid.myapplication.Fragments;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import  android.support.v4.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.abedeid.myapplication.R;
import com.example.abedeid.myapplication.activites.HomeActivity;
import com.example.abedeid.myapplication.adapters.PostsAdapter;
import com.example.abedeid.myapplication.model.MainResponse;
import com.example.abedeid.myapplication.model.Post;
import com.example.abedeid.myapplication.utils.Session;
import com.example.abedeid.myapplication.webservices.WebService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class write_post extends DialogFragment {

    @BindView(R.id.rllt_body)
    RelativeLayout rlltBody;
    @BindView(R.id.prgs_loading)
    ProgressBar prgsLoading;

    @BindView(R.id.rllt_loading)
    RelativeLayout rlltLoading;

    @BindView(R.id.et_post_txt)
    EditText Post_txt;

    @BindView(R.id.flag)
    Spinner flag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_write_post, container, false);
        ButterKnife.bind(this, view);
        initCustomSpinner();
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;

    }

    @OnClick(R.id.btn_post)
    public void onClick() {
        setLoadingMode();
        Post post=new Post();
        post.users_id= Session.getInstance().getUser().users_id;
        post.text=Post_txt.getText().toString();
        post.depart_id="1";
        post.section_id="2";
        post.year_id="1";
        post.user_image="immg";

        WebService.getInstance().getApi().insert_Post(post).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if(response.body().getStatus()==true){
                    setNormalMode();
                    Toast.makeText(getActivity(), "posted", Toast.LENGTH_SHORT).show();
//                     Intent intent=new Intent();
//                    startActivityForResult(intent,2);
                    Intent i = new Intent(getActivity(), HomeActivity.class);
                    startActivity(i);
                     getDialog().dismiss();




                }else{
                   setNormalMode();
                    Toast.makeText(getActivity(), "error "+ response.body().getStatus(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), "error "+ response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                setNormalMode();
                Toast.makeText(getActivity(), "error "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    // set loading layout visible and hide body layout
    public void setLoadingMode() {
        rlltLoading.setVisibility(View.VISIBLE);
        rlltBody.setVisibility(View.GONE);
    }

    // set body layout visible and hide loading layout
    public void setNormalMode() {
        rlltLoading.setVisibility(View.GONE);
        rlltBody.setVisibility(View.VISIBLE);
    }

    private void initCustomSpinner() {


        // Spinner Drop down elements
        final List<String> year = new ArrayList<String>();
        year.add("section");
        year.add("department");
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, year);
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        flag.setAdapter(dataAdapter);
    }
}

