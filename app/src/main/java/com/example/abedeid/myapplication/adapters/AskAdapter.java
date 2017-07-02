package com.example.abedeid.myapplication.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.abedeid.myapplication.ExpandableTextView;
import com.example.abedeid.myapplication.R;
import com.example.abedeid.myapplication.activites.Comment;
import com.example.abedeid.myapplication.activites.Write_Post_Activity;
import com.example.abedeid.myapplication.model.CommentType;
import com.example.abedeid.myapplication.model.Post;
import com.example.abedeid.myapplication.webservices.Urls;

import java.util.List;


/**
 * Created by abed_eid on 10/12/2016.
 */

public class AskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Post> postList;
    Context context;

    public AskAdapter(List<Post> postList, Context context) {
        this.postList = postList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == CommentType.SENT_TXT) {

            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.post_ask_item, parent, false));

        } else {
            return new MyViewImageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.post_ask_item_img, parent, false));


        }




    }

    @Override
    public int getItemViewType(int position) {


        Post post = postList.get(position);

        if (post.post_image.isEmpty()) {
             return CommentType.SENT_TXT;
        } else if (!post.post_image.isEmpty()) {
             return CommentType.SENT_IMAGE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder mholder, int position) {
        int type = getItemViewType(position);
        final Post CurrentPost = postList.get(position);

        if (type == CommentType.SENT_TXT) {
            final AskAdapter.MyViewHolder holder = (AskAdapter.MyViewHolder) mholder;
            holder.post_txt.setText(CurrentPost.text);
            holder.writer_post_name.setText(CurrentPost.name);
            holder.writer_post_time.setText((CurrentPost.createdat));
            holder.comment_number.setText(CurrentPost.comments + " Comments");
            if(!CurrentPost.type.equals("0")){
                holder.type.setVisibility(View.VISIBLE);
                holder.type.setBackgroundResource(R.drawable.ask);

            }
//
            holder.comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Comment.class);
                    intent.putExtra("Post_ID", CurrentPost.id_post);
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
                }
            });



        } else if (type == CommentType.SENT_IMAGE) {

            final AskAdapter.MyViewImageHolder holder = (AskAdapter.MyViewImageHolder) mholder;
             holder.post_txt.setText(CurrentPost.text);
            holder.writer_post_name.setText(CurrentPost.name);
            holder.writer_post_time.setText((CurrentPost.createdat));
            holder.comment_number.setText(CurrentPost.comments + "  Answers");
            if(!CurrentPost.type.equals("0")){
                holder.type.setVisibility(View.VISIBLE);
                holder.type.setBackgroundResource(R.drawable.ask);

            }
            if (!CurrentPost.post_image.isEmpty()) {
                Glide.with(context).load(Urls.Local_images + CurrentPost.user_image).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.image_post) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        holder.image_post.setImageDrawable(circularBitmapDrawable);
                    }
                });
            } else {
                Glide.with(context).load(Urls.Local_images + CurrentPost.user_image).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.image_post) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        holder.image_post.setImageDrawable(circularBitmapDrawable);
                    }
                });
            }
            holder.comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Comment.class);
                    intent.putExtra("Post_ID", CurrentPost.id_post);
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
                }
            });


            Glide.with(context)
                    .load(Urls.Local_images + CurrentPost.post_image)
                    .into(holder.post_imge);

        }
    }


    @Override
    public int getItemCount() {
        return postList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView writer_post_name, writer_post_time, comment_number;
        ExpandableTextView post_txt;
        ImageView image_post, type;
        CardView post_card;
        LinearLayout comment;

        public MyViewHolder(View itemView) {
            super(itemView);
            writer_post_name = (TextView) itemView.findViewById(R.id.writer_post_name);
            writer_post_time = (TextView) itemView.findViewById(R.id.writer_post_time);
            post_txt = (ExpandableTextView) itemView.findViewById(R.id.post_txt);
            comment_number = (TextView) itemView.findViewById(R.id.comment_number);
            image_post = (ImageView) itemView.findViewById(R.id.image_post);
            type = (ImageView) itemView.findViewById(R.id.type);
            post_card = (CardView) itemView.findViewById(R.id.post_card);
            comment = (LinearLayout) itemView.findViewById(R.id.comment);

        }
    }



    public class MyViewImageHolder extends RecyclerView.ViewHolder {
        TextView writer_post_name, writer_post_time, comment_number;
        ExpandableTextView post_txt;
        ImageView image_post, post_imge ,type;
        CardView post_card;
        LinearLayout comment;

        public MyViewImageHolder(View itemView) {
            super(itemView);
            writer_post_name = (TextView) itemView.findViewById(R.id.writer_post_name);
            writer_post_time = (TextView) itemView.findViewById(R.id.writer_post_time);
            post_txt = (ExpandableTextView) itemView.findViewById(R.id.post_txt);
            comment_number = (TextView) itemView.findViewById(R.id.comment_number);
            image_post = (ImageView) itemView.findViewById(R.id.image_post);
            post_imge = (ImageView) itemView.findViewById(R.id.post_imge);
            type = (ImageView) itemView.findViewById(R.id.type);
            post_card = (CardView) itemView.findViewById(R.id.post_card);
            comment = (LinearLayout) itemView.findViewById(R.id.comment);

        }
    }
}
