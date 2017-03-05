package com.example.abedeid.myapplication.Fragments;

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
import com.example.abedeid.myapplication.adapters.PostsAdapter;
import com.example.abedeid.myapplication.model.Post;
import com.example.abedeid.myapplication.model.users;
import com.example.abedeid.myapplication.utils.Session;
import com.example.abedeid.myapplication.webservices.WebService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostOrAsk extends Fragment {
    private final String TAG = "PostOrAskTAG";
    List<Post> postList;
    private PostsAdapter adapter;
    RecyclerView recycler_view;
    users user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_or_ask, container, false);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view_posts);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        user = Session.getInstance().getUser();
        int  pageNumber = 1;
        if (user != null) {

           final users s = new users();
            s.user_flage = user.user_flage;
            s.id = user.id;
            s.year = user.year;
            s.section = user.section;
            s.department = user.department;
            s.email = user.email;
            postList = new ArrayList<>();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recycler_view.setLayoutManager(linearLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            adapter=new PostsAdapter(postList,getContext());
            recycler_view.setAdapter(adapter);
            recycler_view.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                    page++;
                    getPostOfPages(s,page);


                }
            });
            getPostOfPages(s,1);


        } else {
            Toast.makeText(getContext(), "error users is null  ", Toast.LENGTH_SHORT).show();
        }
    }

    private void getPostOfPages(users s, int pageNumber) {
        s.Page=pageNumber;
        WebService.getInstance().getApi().Posts(s).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                postList.addAll(response.body());
                adapter.notifyItemRangeInserted(adapter.getItemCount(),postList.size()-1);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}
