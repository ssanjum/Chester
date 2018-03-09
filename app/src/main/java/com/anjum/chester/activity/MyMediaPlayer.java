package com.anjum.chester.activity;

import android.media.MediaPlayer;
import android.widget.SeekBar;

import com.anjum.chester.model.SongInfoModel;

import java.io.IOException;

/**
 * Created by sanjum on 3/8/2018.
 */

public class MyMediaPlayer {
    private MediaPlayer mediaPlayer;
    private MyPlayerListener playerListener;
    private boolean isPlaying;

    public MyMediaPlayer(MyPlayerListener playerListener) {
        this.playerListener = playerListener;
    }

    public void start(SongInfoModel songInfoModel){
        mediaPlayer=new MediaPlayer();
        try {
            mediaPlayer.setDataSource(songInfoModel.getSongUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                playerListener.onStart();
                mediaPlayer.start();
                isPlaying=true;
            }
        });
    }

    public void stop(){
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer=null;
            playerListener.onStop();
            isPlaying=false;
        }
    }

    public interface MyPlayerListener{
        void onStart();
        void onStop();
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
