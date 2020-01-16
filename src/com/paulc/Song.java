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


    //Validation method as validation could/should be more in-depth and this would allow easy changes
    private static boolean songPromptInputValid(String songName){
        return (!(songName.equals("") || songName.equals(" ")));
    }



    public static String promptForExistingSong() {
        String songName = null;
        try{
            System.out.println("Please enter the name of an Song in the library: ");
            songName = br.readLine();

            //Get valid input
            while(!Song.songExist(songName)){
                System.out.println(songName + " not in library, please enter the title of a song in the library: ");
                songName = br.readLine();
            }
        }catch(IOException e) {
            System.out.println("Error prompting for existing song name: " +e.getMessage());
        }
        return songName;
    }






    public static String promptForNewSong() {
        String songName = null;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println("Please enter the name of the new Song: ");
            songName = br.readLine();
            //Songs not tested to be unique as it is will be tied to an unique album, and its unlikely
            //for an artist to release 2 songs with the same title on the same album.

            //Get valid input if first album input existed
            while(!Song.songPromptInputValid(songName)){
                System.out.println("Please enter  a valid title for the new/updated Album: ");
                songName = br.readLine();

            }

        }catch(IOException e) {
            System.out.println("Error prompting for Song name: " +e.getMessage());
        }
        return songName;
    }



    public static boolean songExist(String songName) {
        DataSource ds = new DataSource();
        ArrayList<String> allsongNames = ds.listAllSongNames();
        boolean songExists = false;
        for(String songNameInLibrary: allsongNames){
            if(songNameInLibrary.equals(songName)){
                songExists = true;
            }
        }
        return songExists;
    }



}
