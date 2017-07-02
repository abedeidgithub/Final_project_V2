package com.example.abedeid.myapplication.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.abedeid.myapplication.R;
import com.example.abedeid.myapplication.adapters.AskAdapter;
import com.example.abedeid.myapplication.adapters.Endless.EndlessRecyclerViewScrollListener;
import com.example.abedeid.myapplication.adapters.NewsAdapter;
import com.example.abedeid.myapplication.adapters.PostsAdapter;
import com.example.abedeid.myapplication.model.Post;
import com.example.abedeid.myapplication.model.news;
import com.example.abedeid.myapplication.model.users;
import com.example.abedeid.myapplication.utils.Session;
import com.example.abedeid.myapplication.webservices.WebService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ask extends Fragment {



    private final String TAG = "PostOrAskTAG";
    List<Post> postList;
    RecyclerView recycler_view;
    LinearLayoutManager linearLayoutManager;
    private int positionIndex;
    private int topView;
    private AskAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ask, container, false);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view_asks);
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
        adapter = new AskAdapter(postList, getContext());
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


        WebService.getInstance().getApi().Asks(s).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                postList.addAll(response.body());
                adapter.notifyItemRangeInserted(adapter.getItemCount(), postList.size() - 1);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}
