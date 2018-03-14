package com.anjum.chester.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


import com.anjum.chester.R;
import com.anjum.chester.model.SongInfoModel;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack, ivPlay, ivForward;
    private SeekBar seekBar;
    private SongInfoModel infoModel;
    private TextView tvSongName, tvArtistName;
    private boolean isServiceBound;
    private boolean isPlaying;
    private MusicService musicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ivBack = findViewById(R.id.ivRewind);
        ivPlay = findViewById(R.id.ivPlay);
        ivForward = findViewById(R.id.ivForward);
        seekBar = findViewById(R.id.seekbar);
        tvArtistName = findViewById(R.id.tvArtistName);
        tvSongName = findViewById(R.id.tvSongName);
        Bundle bundle = getIntent().getExtras();
        infoModel = (SongInfoModel) bundle.getSerializable("SER");
        tvArtistName.setText(infoModel.getArtistName());
        tvSongName.setText(infoModel.getSongName());
        ivForward.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        ivBack.setOnClickListener(this);

    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MyBinder myBinder = (MusicService.MyBinder) service;
            musicService = myBinder.getService();
            musicService.setSong(infoModel);
            isServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isServiceBound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MusicService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("SER", infoModel);
        intent.putExtras(bundle);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivRewind:
                break;
            case R.id.ivPlay:
                if (!musicService.isPlaying()) {
                    ivPlay.setImageResource(R.drawable.ic_action_pause);
                } else {
                    ivPlay.setImageResource(R.drawable.ic_action_play);
                }
                musicService.startPlayer();
                break;
            case R.id.ivForward:
                break;
        }
    }

   /* private void playMusic() {
        if (!isPlaying) {
            ivPlay.setImageResource(R.drawable.ic_action_pause);
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
                ivPlay.setImageResource(R.drawable.ic_action_play);
                mediaPlayer.release();
                mediaPlayer = null;
                isPlaying = false;
            }
        }
    }
*/

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
