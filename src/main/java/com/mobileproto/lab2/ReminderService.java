package com.mobileproto.lab2;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by doyung on 10/16/13.
 */
public class ReminderService extends IntentService {
    private static final int NOTIF_ID = 1;

    public ReminderService(){
        super("ReminderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        long when = System.currentTimeMillis();         // notification time
        Notification.Builder notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setTicker("Rain Check bitch")
                .setContentTitle("It's aboutta rain foo")
                .setContentText("Check the weather ish.");
        notification.setAutoCancel(true);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setContentIntent(contentIntent);
        nm.notify(0, notification.build());
    }

}