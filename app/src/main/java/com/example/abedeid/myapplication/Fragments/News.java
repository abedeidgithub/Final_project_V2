package com.example.abedeid.myapplication.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
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
 import com.example.abedeid.myapplication.model.news;
import com.example.abedeid.myapplication.model.users;
import com.example.abedeid.myapplication.webservices.WebService;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class News extends Fragment {






    private final String TAG = "PostOrAskTAG";
    List<news> postList;
    RecyclerView recycler_view;
    LinearLayoutManager linearLayoutManager;
    private int positionIndex;
    private int topView;
    private NewsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view_news);
        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final users s = new users();
        s.Page = 1;
        postList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext());
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        adapter = new NewsAdapter(postList, getContext());
        recycler_view.setAdapter(adapter);
        recycler_view.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                s.Page++;
                getPostOfPages(s);
            }
        });
        getPostOfPages(s);


    }


    @Override
    public void onPause() {
        super.onPause();
        positionIndex = linearLayoutManager.findFirstVisibleItemPosition();

    }


    @Override
    public void onResume() {
        super.onResume();
        linearLayoutManager.scrollToPositionWithOffset(positionIndex, topView);
        linearLayoutManager.scrollToPosition(positionIndex);

    }

    private void getPostOfPages(users s) {


        WebService.getInstance().getApi().getnews(s).enqueue(new Callback<List<news>>() {
            @Override
            public void onResponse(Call<List<news>> call, Response<List<news>> response) {

                postList.addAll(response.body());
                adapter.notifyItemRangeInserted(adapter.getItemCount(), postList.size() - 1);
            }

            @Override
            public void onFailure(Call<List<news>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}
