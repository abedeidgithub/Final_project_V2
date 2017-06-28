package com.example.abedeid.myapplication.fcm;


import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.abedeid.myapplication.R;
import com.example.abedeid.myapplication.activites.HomeActivity;
import com.example.abedeid.myapplication.model.news;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import java.util.List;

/**
 * Created by abed_eid on 23/12/2016.
 */


public class FirebaseService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        /**
         * be sure that data size > 0
         */
        if (remoteMessage.getData().size() > 0) {
            // get values from data that sent from php by fcm
            Log.e("message content", remoteMessage.getData().get("message"));


//            sendNotification(message);

            // check if the sender of message is current user or not
//            if (!(userId.trim().equals(Session.newInstance().getUser().users_id.trim()))) {

                // check if app in background or not

                if (isAppIsInBackground(this)) {
                    // app is in background show notification to user
                    sendNotification(remoteMessage.getData().get("message"));
                } else {
                    // app is forground and user see it now send broadcast to update chat
                    // you can send broadcast to do anything if you want !
                    Intent myIntent = new Intent("News");
                    myIntent.putExtra("msg", remoteMessage.getData().get("message"));
                    sendBroadcast(myIntent);
                    sendNotification(remoteMessage.getData().get("message"));
                }
            }


//        }

    }

    /**
     * Method check if app is in background or in foreground
     *
     * @param context this contentx
     * @return true if app is in background or false if app in foreground
     */

    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    /**
     * Method send notification
     *
     * @param message message object
     */
    private void sendNotification(String message) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("msg", message);
//        intent.putExtra("room_id", Integer.parseInt(message.getRoomId()));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Hendienger")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}