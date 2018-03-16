package com.anjum.chester.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.anjum.chester.R;
import com.anjum.chester.activity.MyPlayer;
import com.anjum.chester.constant.Constant;
import com.anjum.chester.model.SongInfoModel;

/**
 * Created by sanjum on 3/16/2018.
 */

public class NewService extends Service {
    private SongInfoModel infoModel;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        infoModel = (SongInfoModel) bundle.getSerializable("SER");
        if (intent.getAction().equals("startforeground")) {
            Intent notificationIntent = new Intent(this, MyPlayer.class);
            notificationIntent.setAction(Constant.ACTION.MAIN_ACTION);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    notificationIntent, 0);

            Intent previousIntent = new Intent(this, NewService.class);
            previousIntent.setAction(Constant.ACTION.PREV_ACTION);
            PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                    previousIntent, 0);

            Intent playIntent = new Intent(this, NewService.class);
            playIntent.setAction(Constant.ACTION.PLAY_ACTION);
            PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                    playIntent, 0);

            Intent nextIntent = new Intent(this, NewService.class);
            nextIntent.setAction(Constant.ACTION.NEXT_ACTION);
            PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                    nextIntent, 0);
            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    R.drawable.chester);

            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle(infoModel.getSongName())
                    .setTicker("Fitoor")
                    .setContentText(infoModel.getArtistName())
                    .setSmallIcon(R.drawable.chester)
                    .setLargeIcon(
                            Bitmap.createScaledBitmap(icon, 128, 128, false))
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .addAction(android.R.drawable.ic_media_previous,
                            "Previous", ppreviousIntent)
                    .addAction(android.R.drawable.ic_media_play, "Play",
                            pplayIntent)
                    .addAction(android.R.drawable.ic_media_next, "Next",
                            pnextIntent).build();
            startForeground(Constant.NOTIFICATION_ID.FOREGROUND_SERVICE,
                    notification);
        }
        return START_STICKY;
    }
}
