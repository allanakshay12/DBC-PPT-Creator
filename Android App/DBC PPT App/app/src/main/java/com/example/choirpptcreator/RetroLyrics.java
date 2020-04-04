package com.example.choirpptcreator;

import com.google.gson.annotations.SerializedName;

public class RetroLyrics {

    @SerializedName("lyrics")
    private String lyrics;

    public RetroLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }
}
