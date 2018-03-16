package com.anjum.chester.activity;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SeekBar;

import com.anjum.chester.R;
import com.anjum.chester.adapter.SongAdapter;
import com.anjum.chester.model.SongInfoModel;

import java.util.ArrayList;
import java.util.List;

public class LinkinPark extends AppCompatActivity {
    private RecyclerView mSongRecycler;
    private SeekBar seekBar;
    private SongAdapter songAdapter;
    private List<SongInfoModel> songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkin_park);
        mSongRecycler = findViewById(R.id.lprecycler);
        songList = new ArrayList<>();
        mSongRecycler.setLayoutManager(new LinearLayoutManager(this));
        mSongRecycler.addItemDecoration(new DividerItemDecoration(this, new LinearLayoutManager(this).getOrientation()));
        loadSong();
        mSongRecycler.setAdapter(songAdapter);
        songAdapter.setOnItemClickListner(new SongAdapter.onItemClickListner() {
            @Override
            public void onRowClick(int pos, SongInfoModel infoModel, View button) {
                Intent intent = new Intent(LinkinPark.this, MyPlayer.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("SER", infoModel);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }

    private void loadSong() {
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        Cursor cursor = getContentResolver().query(songUri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    String songName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String artistName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String songUrl = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    SongInfoModel infoModel = new SongInfoModel(songName, artistName, songUrl);
                    songList.add(infoModel);

                } while (cursor.moveToNext());
                cursor.close();
            }
        }

        songAdapter = new SongAdapter(songList, this);


    }
}
