package com.example.eaishwary.myapplication;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

public class Task extends BroadcastReceiver {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context arg0, Intent arg1) {
        Toast.makeText(arg0, "Alarm received!", Toast.LENGTH_LONG).show();

        final  Notification.Builder builder = new Notification.Builder(arg0);
        builder.setStyle(new Notification.BigTextStyle(builder)
                .bigText("Complete work before deadline !!")
                .setBigContentTitle("Task Deadline Reminder"))

                .setSmallIcon(android.R.drawable.sym_def_app_icon);

        Intent notificationIntent = new Intent(arg0, NotificationActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(arg0, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) arg0.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }

}