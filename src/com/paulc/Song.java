package com.paulc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    //close this when app closes??
    //Stream Reader for class
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


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

    public static void removeSong(){
        String songName = Song.promptForExistingSong();
        DataSource ds = new DataSource();
        ds.deleteSong(songName);
    }

    //Validation method as validation could/should be more in-depth and this would allow easy changes
    //Just example tests
    private static boolean songPromptInputValid(String songName){
        return (!(songName.contains("@") || songName.contains("#")));
    }


    //Songs not tested to be unique as it is will be tied to an unique album, and its unlikely
    //for an artist to release 2 songs with the same title on the same album.
    public static String promptForNewSong() {
        String songName = null;
        try{
            System.out.println("Please enter the name of song (or hit return to exit): ");
            songName = br.readLine();
            //if an empty line, abort
            if (songName != "" || songName != " " || songName != "  " || !songName.contains("   ")){
                //Get valid input if first album input existed
                while(!Song.songPromptInputValid(songName)){
                    System.out.println("Please enter a valid title for the song (or hit return to exit): ");
                    songName = br.readLine();
                }
            }
        }catch(IOException e) {
            System.out.println("Error prompting for Song name: " +e.getMessage());
        }
        return songName;
    }



    public static boolean songExist(String songName) {
        DataSource ds = new DataSource();
        ArrayList<String> allSongNames = ds.listAllSongNames();
        boolean songExists = false;
        for(String songNameInLibrary: allSongNames){
            if(songNameInLibrary.equals(songName)){
                songExists = true;
            }
        }
        return songExists;
    }

    public static String promptForExistingSong() {
        String songName = null;
        try{
            System.out.println("Please enter the name of song: ");
            songName = br.readLine();
            while(!Song.songExist(songName)){
                System.out.println("'" + songName + "' is not in the library, please enter an song in the library:");
                songName = br.readLine();
            }
        }catch(IOException e) {
            System.out.println("Error prompting for Song name: " +e.getMessage());
        }
        return songName;
    }


    public static void updateSong(){
        String oldTitle = Song.promptForExistingSong();
        String newTitle = Song.promptForNewSong();
        if ((newTitle.equals("") || newTitle.equals(" ") || newTitle.equals("  ") || newTitle.contains("   "))){
            newTitle = oldTitle;
        }
        DataSource ds = new DataSource();
        ds.updateSong(oldTitle, newTitle);
    }



    public static void addSong(int albumID){
        String songTitle = Song.promptForNewSong();
        DataSource ds = new DataSource();
        ds.insertSong(albumID, songTitle);
    }


}
