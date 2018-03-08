package com.anjum.chester.model;

import java.io.Serializable;

/**
 * Created by ANJUM on 3/7/2018.
 */

public class SongInfoModel implements Serializable {
    private String songName;
    private String artistName;
    private String songUrl;

    public SongInfoModel(String songName, String artistName, String songUrl) {
        this.songName = songName;
        this.artistName = artistName;
        this.songUrl = songUrl;
    }



    public String getSongName() {
        return songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getSongUrl() {
        return songUrl;
    }
}
