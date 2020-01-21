package com.paulc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Song {

    private int song_ID;
    private int album_ID;
    private String title;

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public Song(int song_ID, int album_ID, String title) {
        this.song_ID = song_ID;
        this.album_ID = album_ID;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static void addSong(int albumID, String songTitle){
        //if an empty songTitle, don't add
        if (!(songTitle.equals("") || songTitle.equals(" ") || songTitle.equals("  ") || songTitle.contains("   "))){
            DataSource ds = new DataSource();
            ds.insertSong(albumID, songTitle);
        }
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
    public static void removeSong(){
        String songTitle = Song.promptForExistingSong();
        DataSource ds = new DataSource();
        ds.deleteSong(songTitle);
    }


    // Validation for user input (in this class space is OK as its a termination character for an inout loop).
    // Very basic at the moment but extracted to a separate method to make sure it will only need to be updated in one place.
    private static boolean songPromptInputValid(String songTitle){
        return (!(songTitle.contains("@") || songTitle.contains("#")));
    }


    // Each song is not tested to be unique as it is will be tied to an unique album, and its unlikely
    // for an artist to release 2 songs with the same title on the same album.
    // where as there is a chance two different artists may have the same named song:
    //
    public static String promptForNewSong() {
        String songTitle = null;
        try{
            System.out.println("Please enter the name of song (or hit return to exit): ");
            songTitle = br.readLine();
            //if an empty line, abort
            if (songTitle != "" || songTitle != " " || songTitle != "  " || !songTitle.contains("   ")){
                //Get valid input if first album input existed
                while(!Song.songPromptInputValid(songTitle)){
                    System.out.println("Please enter a valid title for the song (or hit return to exit): ");
                    songTitle = br.readLine();
                }
            }
        }catch(IOException e) {
            System.out.println("Error prompting for New Song name: " +e.getMessage());
        }
        return songTitle;
    }


    public static String promptForExistingSong() {
        String songTitle = null;
        try{
            System.out.println("Please enter the name of song: ");
            songTitle = br.readLine();
            while(!Song.songExist(songTitle)){
                System.out.println("'" + songTitle + "' is not in the library, please enter an song in the library:");
                songTitle = br.readLine();
            }
        }catch(IOException e) {
            System.out.println("Error prompting for Existing Song name: " +e.getMessage());
        }
        return songTitle;
    }


    public static boolean songExist(String songTitle) {
        DataSource ds = new DataSource();
        ArrayList<String> allsongTitles = ds.listAllSongTitles();
        boolean songExists = false;
        for(String songTitleInLibrary: allsongTitles){
            if(songTitleInLibrary.equals(songTitle)){
                songExists = true;
            }
        }
        return songExists;
    }








    public static void closeStream(){
        try{
            br.close();
        } catch (IOException e){
            System.out.println("Problem closing Songs's stream: " + e.getMessage());
        }
    }


}
