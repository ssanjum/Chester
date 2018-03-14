package com.anjum.chester.activity;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.anjum.chester.model.SongInfoModel;

import java.io.IOException;

/**
 * Created by sanjum on 3/14/2018.
 */

public class MusicService extends Service {
    private SongInfoModel songInfoModel;
    private final IBinder jabbu = new MyBinder();
    private MediaPlayer player;
    private boolean isPlaying;

    public MusicService() {
        super();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return jabbu;
    }

    public class MyBinder extends Binder {

        MusicService getService() {

            return MusicService.this;
        }
    }


    public void setSong(SongInfoModel songInfoModel) {
        this.songInfoModel = songInfoModel;
    }

    public void startPlayer() {
        if (!isPlaying) {
            player = new MediaPlayer();
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

        isPlaying = false;
        player.stop();
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
