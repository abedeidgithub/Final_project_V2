package com.example.abedeid.myapplication.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.abedeid.myapplication.Fragments.News;
import com.example.abedeid.myapplication.R;
import com.example.abedeid.myapplication.activites.Comment;
import com.example.abedeid.myapplication.model.Post;
import com.example.abedeid.myapplication.model.news;
import com.example.abedeid.myapplication.webservices.Urls;

import java.util.List;

/**
 * Created by abed_eid on 10/12/2016.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {


    List<news> postList;
    Context context;

    public NewsAdapter(List<news> postList, Context context) {
        this.postList = postList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final news CurrentPost = postList.get(position);
        holder.post_txt.setText(CurrentPost.news_txt);
//        holder.writer_post_time.setText(CurrentPost.updatedat);
        Glide.with(context).load(Urls.Local_images+CurrentPost.news_img).into(holder.image_post) ;


    }


    @Override
    public int getItemCount() {
        return postList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView writer_post_time, post_txt;
        ImageView image_post;

        public MyViewHolder(View itemView) {
            super(itemView);
//            writer_post_time = (TextView) itemView.findViewById(R.id.news_time);
            post_txt = (TextView) itemView.findViewById(R.id.news_txt);
            image_post = (ImageView) itemView.findViewById(R.id.image_news);

        }
    }
}
