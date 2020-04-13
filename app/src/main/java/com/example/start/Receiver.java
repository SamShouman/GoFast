package com.example.start;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent i=new Intent(context,Main2Activity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,i,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.mynoti)
                .setContentTitle("GoFast Reminder")
                .setContentText("Daily reminder to add a new friend.")
                .setAutoCancel(true);
        notificationManager.notify(1,builder.build());

    }
}
