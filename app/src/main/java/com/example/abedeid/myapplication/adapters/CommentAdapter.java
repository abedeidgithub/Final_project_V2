package com.example.abedeid.myapplication.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.abedeid.myapplication.R;
import com.example.abedeid.myapplication.model.CommentModel;
import com.example.abedeid.myapplication.model.CommentType;
import com.example.abedeid.myapplication.utils.Session;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abed Eid on 3/2/2017.
 */

public class CommentAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<CommentModel> comments;
    private Context context;


    public CommentAdapter(List<CommentModel> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         * check the type of view and return holder
         */
        if (viewType == CommentType.SENT_TXT) {
            return new SentTextHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sent_message_text, parent, false));
        } else if (viewType == CommentType.SENT_IMAGE) {
            return new SentImageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sent_message_img, parent, false));
        } else if (viewType == CommentType.RECEIVE_TXT) {
            return new ReceivedTextHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_received_message_text, parent, false));
        } else if (viewType == CommentType.RECIVE_IMAGE) {
            return new ReceivedImageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_received_message_img, parent, false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder mHolder, int position) {

        int type = getItemViewType(position);
        CommentModel CurrentMessage = comments.get(position);
        /**
         * check message type and init holder to user it and set data in the right place for every view
         */
        if (type == CommentType.SENT_TXT) {
            SentTextHolder holder = (SentTextHolder) mHolder;
            holder.tv_time.setText(CurrentMessage.updated_at);
            holder.tv_message_content.setText(CurrentMessage.answer_or_comment_text);

        } else if (type == CommentType.SENT_IMAGE) {
            SentImageHolder holder = (SentImageHolder) mHolder;
            holder.tv_time.setText(CurrentMessage.updated_at);
            Glide.with(context).load("http://fci-suze.esy.es/Webservices/uploads/Profile_img/FB_IMG_1488042460414.jpg").into(holder.img_msg);


        } else if (type == CommentType.RECEIVE_TXT) {
            ReceivedTextHolder holder = (ReceivedTextHolder) mHolder;
            holder.tv_time.setText(CurrentMessage.updated_at);
            holder.tv_username.setText(CurrentMessage.name);
            holder.tv_message_content.setText(CurrentMessage.answer_or_comment_text);


        } else if (type == CommentType.RECIVE_IMAGE) {
            ReceivedImageHolder holder = (ReceivedImageHolder) mHolder;
            holder.tv_time.setText(CurrentMessage.updated_at);
            holder.tv_username.setText(CurrentMessage.name);
            Glide.with(context).load("http://fci-suze.esy.es/Webservices/uploads/Profile_img/FB_IMG_1488042460414.jpg").into(holder.img_msg);

        }

    }


    @Override
    public int getItemCount() {
        return comments.size();
    }

    @Override
    public int getItemViewType(int position) {
        /**
         * check the user id to detect if message sent or received
         * then check if message is text or img
         */

        String userID = Session.getInstance().getUser().id;
        CommentModel comment = comments.get(position);

        if (userID.equals(comment.user_id)) {
//type is image or txt
            if (comment.type.equals("0")) {
                return CommentType.SENT_TXT;
            } else if (comment.type.equals("1")) {
                return CommentType.SENT_IMAGE;
            }

        } else {
            if (comment.type.equals("0")) {
                return CommentType.RECEIVE_TXT;
            } else if (comment.type.equals("1")) {
                return CommentType.RECIVE_IMAGE;
            }

        }
        return super.getItemViewType(position);
    }


    public class SentTextHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_message_content)
        TextView tv_message_content;

        public SentTextHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this ,itemView);

        }
    }

    public class SentImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.img_msg)
        ImageView img_msg;

        public SentImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this ,itemView);

        }
    }

    public class ReceivedTextHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_message_content)
        TextView tv_message_content;
        @BindView(R.id.tv_username)
        TextView tv_username;

        public ReceivedTextHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this ,itemView);

        }
    }

    public class ReceivedImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.img_msg)
        ImageView img_msg;
        @BindView(R.id.tv_username)
        TextView tv_username;

        public ReceivedImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this ,itemView);

        }
    }


}
