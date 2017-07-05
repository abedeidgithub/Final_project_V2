package com.example.abedeid.myapplication.adapters;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.abedeid.myapplication.ExpandableTextView;
import com.example.abedeid.myapplication.R;
import com.example.abedeid.myapplication.model.Matrial;
import com.example.abedeid.myapplication.model.news;
import com.example.abedeid.myapplication.webservices.Urls;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.http.Url;

import static java.security.AccessController.getContext;

/**
 * Created by abed_eid on 10/12/2016.
 */

public class matrialAdapter extends RecyclerView.Adapter<matrialAdapter.MyViewHolder> {


    List<Matrial> postList;
    Context context;

    public matrialAdapter(List<Matrial> postList, Context context) {
        this.postList = postList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.matrial, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Matrial CurrentPost = postList.get(position);
        holder.matrial_time.setText(CurrentPost.updatedat);

//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        Date convertedDate = new Date();
//        try {
//            convertedDate = dateFormat.parse(CurrentPost.updatedat);
//        } catch (ParseException e) {
//             e.printStackTrace();
//        }
        holder.matrial_name.setText(CurrentPost.subject);
        holder.matrial_txt.setText(CurrentPost.desc);
        holder.matrial_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 String url = Urls.Local_URL+ CurrentPost.URL;
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                 request.setTitle("Dowenload SCU\n"+CurrentPost.subject+"\t "+CurrentPost.desc);
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                }
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "name-of-the-file.ext");

                DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                manager.enqueue(request);
            }
        });

 

    }
    public static boolean isDownloadManagerAvailable(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return true;
        }
        return false;
    }


    @Override
    public int getItemCount() {
        return postList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
//        RelativeTimeTextView  matrial_time;
        TextView  matrial_time;
        TextView matrial_txt;
        TextView matrial_name;
        CardView matrial_card;

        public MyViewHolder(View itemView) {
            super(itemView);
            matrial_time = (TextView ) itemView.findViewById(R.id.matrial_time);
//            matrial_time = (RelativeTimeTextView ) itemView.findViewById(R.id.matrial_time);
            matrial_txt = (TextView) itemView.findViewById(R.id.matrial_txt);
            matrial_name = (TextView) itemView.findViewById(R.id.matrial_name);
            matrial_card = (CardView) itemView.findViewById(R.id.matrial_card);

        }
    }
}
