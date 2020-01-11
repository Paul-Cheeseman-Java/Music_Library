package com.paulc;

import java.util.ArrayList;

public class Song {

    public Song(int song_ID, int album_ID, String title, int track_number, int duration) {
        this.song_ID = song_ID;
        this.album_ID = album_ID;
        this.title = title;
        this.track_number = track_number;
        this.duration = duration;
    }

    //timestamp? getDuration() //Put length into DB in seconds, when calc in method
    //Artist
    private int song_ID;
    private int album_ID;
    private String title;
    private int track_number;
    private int duration;

    public int getSong_ID() {
        return song_ID;
    }

    public void setSong_ID(int song_ID) {
        this.song_ID = song_ID;
    }

    public int getAlbum_ID() {
        return album_ID;
    }

    public void setAlbum_ID(int album_ID) {
        this.album_ID = album_ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTrack_number() {
        return track_number;
    }

    public void setTrack_number(int track_number) {
        this.track_number = track_number;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
