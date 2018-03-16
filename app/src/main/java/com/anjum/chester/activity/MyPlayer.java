package com.anjum.chester.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.anjum.chester.R;
import com.anjum.chester.model.SongInfoModel;
import com.anjum.chester.services.MusicPlayerService;
import com.anjum.chester.services.NewService;

public class MyPlayer extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack, ivPlay, ivForward;
    private SeekBar seekBar;
    private SongInfoModel infoModel;
    private TextView tvSongName, tvArtistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_player);
        ivBack = findViewById(R.id.ivRewind1);
        ivPlay = findViewById(R.id.ivPlay1);
        ivForward = findViewById(R.id.ivForward1);
        seekBar = findViewById(R.id.seekbar1);
        tvArtistName = findViewById(R.id.tvArtistName1);
        tvSongName = findViewById(R.id.tvSongName1);
        Bundle bundle = getIntent().getExtras();
        infoModel = (SongInfoModel) bundle.getSerializable("SER");
        tvArtistName.setText(infoModel.getArtistName());
        tvSongName.setText(infoModel.getSongName());
        ivForward.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivRewind1:
                Intent intent = new Intent(this, DemoActivity.class);
                startActivity(intent);
                break;
            case R.id.ivPlay1:
                Intent intent1 = new Intent(MyPlayer.this, NewService.class);
                intent1.setAction("startforeground");
                Bundle bundle = new Bundle();
                bundle.putSerializable("SER", infoModel);
                intent1.putExtras(bundle);
                startService(intent1);
                break;
            case R.id.ivForward1:
                break;
        }
    }
}
