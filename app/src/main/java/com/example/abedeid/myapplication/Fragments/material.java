package com.example.abedeid.myapplication.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.abedeid.myapplication.R;
import com.example.abedeid.myapplication.adapters.Endless.EndlessRecyclerViewScrollListener;
import com.example.abedeid.myapplication.adapters.NewsAdapter;
import com.example.abedeid.myapplication.adapters.matrialAdapter;
import com.example.abedeid.myapplication.model.Matrial;
import com.example.abedeid.myapplication.model.Section;
import com.example.abedeid.myapplication.model.news;
import com.example.abedeid.myapplication.model.users;
import com.example.abedeid.myapplication.utils.Session;
import com.example.abedeid.myapplication.webservices.WebService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class material extends Fragment {
    private int positionIndex;
    private int topView;
    private final String TAG = "PostOrAskTAG";
    List<Matrial> postList;
    private matrialAdapter adapter;
     LinearLayoutManager linearLayoutManager;
    RecyclerView recycler_view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_material, container, false);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view_material);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final users s=new users();
         final users users = Session.getInstance().getUser();
        s.site=users.site;
        s.about_doctor=users.about_doctor;
        s.depart_id=users.depart_id;
        s.idstudent=users.idstudent;
        s.year_id=users.year_id;
        s.section_idsection=users.section_idsection;
        s.year_name=users.year_name;
        s.section_id=users.section_id;
        s.section_name=users.section_name;
        s.person_id_person=users.person_id_person;
        s.dept_name=users.dept_name;
        s.users_id=users.users_id;
        s.image=users.image;
        s.isAdmin=users.isAdmin;
        s.verify=users.verify;
        s.status=users.status;
        s.message=users.message;
        s.doctor_id=users.doctor_id;
        postList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext());
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        adapter=new matrialAdapter(postList,getContext());
        recycler_view.setAdapter(adapter);
        Toast.makeText(getContext(), users.doctor_id, Toast.LENGTH_SHORT).show();
        getPostOfPages(s);
    }

    private void getPostOfPages(users s) {
        WebService.getInstance().getApi().getmatrial(s).enqueue(new Callback<List<Matrial>>() {
            @Override
            public void onResponse(Call<List<Matrial>> call, Response<List<Matrial>> response) {
               postList.addAll(response.body());
                adapter.notifyItemRangeInserted(adapter.getItemCount(),postList.size()-1);
            }
            @Override
            public void onFailure(Call<List<Matrial>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}