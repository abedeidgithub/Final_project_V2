package com.example.abedeid.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.abedeid.myapplication.R;
import com.example.abedeid.myapplication.activites.Comment;
import com.example.abedeid.myapplication.imageCircle.CircleTransform;
import com.example.abedeid.myapplication.model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by abed_eid on 10/12/2016.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MyViewHolder> {


    List<Post> postList;
    Context context;

    public PostsAdapter(List<Post> postList, Context context) {
        this.postList = postList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_ask_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Post CurrentPost = postList.get(position);
        holder.post_txt.setText(CurrentPost.ask_or_post_text);
        holder.writer_post_name.setText(CurrentPost.name);
        holder.writer_post_time.setText(CurrentPost.created_at);
        holder.comment_number.setText(CurrentPost.comments+" Comments");
        if(CurrentPost.image!=null){
            Picasso.with(context).load("http://fci-suze.esy.es/Webservices/uploads/Profile_img/FB_IMG_1488042460414.jpg").transform(new CircleTransform()).into(holder.image_post);

        }else {
            Picasso.with(context).load("http://fci-suze.esy.es/Webservices/uploads/Profile_img/profile.png").transform(new CircleTransform()).into(holder.image_post);
        }
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Comment.class);
                intent.putExtra("Post_ID",CurrentPost.id);
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView writer_post_name, writer_post_time, post_txt, comment_number;
        ImageView image_post;
        CardView post_card;
        LinearLayout comment;

        public MyViewHolder(View itemView) {
            super(itemView);
            writer_post_name = (TextView) itemView.findViewById(R.id.writer_post_name);
            writer_post_time = (TextView) itemView.findViewById(R.id.writer_post_time);
            post_txt = (TextView) itemView.findViewById(R.id.post_txt);
            comment_number = (TextView) itemView.findViewById(R.id.comment_number);
            image_post = (ImageView) itemView.findViewById(R.id.image_post);
            post_card = (CardView) itemView.findViewById(R.id.post_card);
            comment = (LinearLayout) itemView.findViewById(R.id.comment);

        }
    }
}
