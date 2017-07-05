package com.example.abedeid.myapplication.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.abedeid.myapplication.ExpandableTextView;
import com.example.abedeid.myapplication.R;
import com.example.abedeid.myapplication.model.news;
import com.example.abedeid.myapplication.webservices.Urls;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
//        Log.d("TAG",CurrentPost.updatedat);

        String dateString = CurrentPost.updatedat;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD hh:mm:ss");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        holder.writer_post_time.setReferenceTime(convertedDate.getTime());
//         holder.writer_post_time.setReferenceTime(new Date().getTime());
//        holder.writer_post_time.setText(CurrentPost.updatedat);

        Glide.with(context).load(Urls.Local_images+CurrentPost.news_img).into(holder.image_post) ;


    }


    @Override
    public int getItemCount() {
        return postList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeTimeTextView  writer_post_time;
        ExpandableTextView post_txt;
        ImageView image_post;

        public MyViewHolder(View itemView) {
            super(itemView);
            writer_post_time = (RelativeTimeTextView ) itemView.findViewById(R.id.news_time);
            post_txt = (ExpandableTextView) itemView.findViewById(R.id.news_txt);
            image_post = (ImageView) itemView.findViewById(R.id.image_news);

        }
    }
}
