package com.example.eaishwary.myapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Notification;

/**
 * Created by Prajwal on 25-03-2017.
 */

public class NotificationActivity extends Activity {
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_main);

        b1 = (Button) findViewById(R.id.startSetDialog);
        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addNotification();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void addNotification() {

        final  Notification.Builder builder = new Notification.Builder(this);
        builder.setStyle(new Notification.BigTextStyle(builder)
                .bigText("bjb")
                .setBigContentTitle("Big title")
                .setSummaryText("Big summary"))
                .setContentTitle("Title")
                .setContentText("Summary")
                .setSmallIcon(android.R.drawable.sym_def_app_icon);

        Intent notificationIntent = new Intent(this,
                NotificationActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }


//    protected void displayNotification() {
//        Log.i("Start", "notification");
//
//   /* Invoking the default notification service */
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
//
//        mBuilder.setContentTitle("New Message");
//        mBuilder.setContentText("You've received new message.");
//        mBuilder.setTicker("New Message Alert!");
//        //  mBuilder.setSmallIcon(R.drawable.woman);
//
//   /* Increase notification number every time a new notification arrives */
//        // mBuilder.setNumber(++numMessages);
//
//   /* Add Big View Specific Configuration */
//        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
//
//        String[] events = new String[6];
//        events[0] = new String("This is first line....");
//        events[1] = new String("This is second line...");
//        events[2] = new String("This is third line...");
//        events[3] = new String("This is 4th line...");
//        events[4] = new String("This is 5th line...");
//        events[5] = new String("This is 6th line...");
//
//        // Sets a title for the Inbox style big view
//        inboxStyle.setBigContentTitle("Big Title Details:");
//
//        // Moves events into the big view
//        for (int i = 0; i < events.length; i++) {
//            inboxStyle.addLine(events[i]);
//        }
//
//        mBuilder.setStyle(inboxStyle);
//
//   /* Creates an explicit intent for an Activity in your app */
//        Intent resultIntent = new Intent(this, NotificationView.class);
//
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(NotificationView.class);
//
//   /* Adds the Intent that starts the Activity to the top of the stack */
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        mBuilder.setContentIntent(resultPendingIntent);
//        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//   /* notificationID allows you to update the notification later on. */
//        mNotificationManager.notify(0, mBuilder.build());
//    }
}