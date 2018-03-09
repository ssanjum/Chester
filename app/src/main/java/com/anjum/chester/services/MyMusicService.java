package com.anjum.chester.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.anjum.chester.R;
import com.anjum.chester.model.SongInfoModel;

import java.io.IOException;

/**
 * Created by sanjum on 3/8/2018.
 */

public class MyMusicService extends Service {
    private MediaPlayer mediaPlayer;
    private boolean isPlaying;
    private SongInfoModel infoModel;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer=new MediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        infoModel = (SongInfoModel) bundle.getSerializable("SER");
        Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show();
        if (!isPlaying) {
            //ivPlay.setImageResource(R.drawable.ic_action_pause);
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(infoModel.getSongUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer = mp;
                    mediaPlayer.start();
                    isPlaying = true;
                }
            });
        } else {
            if (mediaPlayer != null) {
                // ivPlay.setImageResource(R.drawable.ic_action_play);
                mediaPlayer.release();
                mediaPlayer = null;
                isPlaying = false;
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Destroy", Toast.LENGTH_SHORT).show();
    }
}
