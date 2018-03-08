package com.anjum.chester.activity;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.anjum.chester.R;
import com.anjum.chester.model.SongInfoModel;

import java.io.IOException;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack, ivPlay, ivForward;
    private SeekBar seekBar;
    private SongInfoModel infoModel;
    private MyMediaPlayer myMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ivBack = findViewById(R.id.ivRewind);
        ivPlay = findViewById(R.id.ivPlay);
        ivForward = findViewById(R.id.ivForward);
        seekBar=findViewById(R.id.seekbar);
        Bundle bundle = getIntent().getExtras();
        infoModel = (SongInfoModel) bundle.getSerializable("SER");
        ivForward.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        myMediaPlayer=new MyMediaPlayer(myPlayerListener);
    }

    MyMediaPlayer.MyPlayerListener myPlayerListener=new MyMediaPlayer.MyPlayerListener() {
        @Override
        public void onStart() {
            ivPlay.setImageResource(R.drawable.ic_action_pause);
        }

        @Override
        public void onStop() {
            ivPlay.setImageResource(R.drawable.ic_action_play);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivRewind:
                break;
            case R.id.ivPlay:
                playMusic();
                break;
            case R.id.ivForward:
                break;
        }
    }

    private void playMusic() {
        if(myMediaPlayer.isPlaying()){
            myMediaPlayer.stop();
        }else{
            myMediaPlayer.start(infoModel);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(myMediaPlayer.isPlaying()){
            myMediaPlayer.stop();
        }
    }
}
