package com.anjum.chester.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.anjum.chester.model.SongInfoModel;

import java.io.IOException;

/**
 * Created by sanjum on 3/15/2018.
 */

public class MusicPlayerService extends Service {
    private SongInfoModel songInfoModel;
    private final IBinder binder = new MyBinder();
    private MediaPlayer player;
    private boolean isPlaying=false;
    private int duration;
    private boolean isResume;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MyBinder extends Binder {

      public   MusicPlayerService getService() {

            return MusicPlayerService.this;
        }
    }

    public void setSong(SongInfoModel songInfoModel) {
        this.songInfoModel = songInfoModel;
        player = new MediaPlayer();
    }
    public void startPlayer() {
        if (!isPlaying) {
            try {
                player.setDataSource(songInfoModel.getSongUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.prepareAsync();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player = mp;
                    player.start();
                    isPlaying = true;
                }
            });
        } else {
            pausePlayer();
        }
    }

    public void stopPlayer() {
        isPlaying = false;
        player.stop();
        player.release();

    }

    public void pausePlayer() {
        duration = player.getCurrentPosition();
        player.pause();
        isPlaying = false;
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
