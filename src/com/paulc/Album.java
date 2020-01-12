package com.paulc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Scanner;

public class Album {

/*
#Album duration from song duration
#sum, then divide by 60 for mins (as the time would be stored in seconds)
#https://www.sqlitetutorial.net/sqlite-sum/

# (should this be app level? "List Library") List Artists, then there albums (aplha), the songs per album (in track No order)

 */




//Album
//String getName()
//void update(String field, Int or String newValue)
//static ArrayList<Album> getAll()
//ArrayList<Song> getAlbumsSongs
    private int artist_ID;      //Required
    private String title;       //Required
    private Blob albumArtwork;
    private String location;    //Required


    //Direct creation from DB read, uses artist_ID
    public Album(int artist_ID, String title, Blob albumArtwork, String location){
        //enforce formats through setters

        //use Artist Name to look up ID, check if exists!
        //Which fields are required.... One for everything, one for just requied fields (set defaults)
        this.title = title;
    }


    //Creation by user inout, using Artist Name and a lookup for ID as user will not know Artist_ID
    public Album(String artistName, String title, Blob albumArtwork, String location){
        //enforce formats through setters

        //use Artist Name to look up ID, check if exists!
        //Which fields are required.... One for everything, one for just requied fields (set defaults)
        this.title = title;
    }

    //Shorter version
    //Creation by user inout, using Artist Name and a lookup for ID as user will not know Artist_ID
    public Album(String artistName, String title, String location){
        //enforce formats through setters

        //use Artist Name to look up ID, check if exists!
        //Which fields are required.... One for everything, one for just requied fields (set defaults)
        this.title = title;
    }



    public String setArtist() {
        return location;
    }



    public static void listAllAlbums(Artist theArtist){
        DataSource ds = new DataSource();
        ArrayList<Album> allTheAlbums = ds.artistsAlbums(theArtist.getName());
        for (Album theAlbum : allTheAlbums){
            System.out.println(theAlbum.getTitle());
        }
    }

    //list all Albums in DB



    public static String promptForExistingAlbum() {
        String albumName = null;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println("Please enter the name of an Album in the library: ");
            albumName = br.readLine();
            while(!Album.albumExist(albumName)){
                System.out.println(albumName + " isn't an Album in the library, please enter the Album in the library");
                albumName = albumName = br.readLine();

            }
        }catch(IOException e) {
            System.out.println("Error prompting for Album name: " +e.getMessage());
        }
        return albumName;
    }



    public static boolean albumExist(String albumName) {
        DataSource ds = new DataSource();
        ArrayList<String> allAlbumNames = ds.listAllAlbumNames();
        boolean albumExists = false;
        for(String albumNameInLibrary: allAlbumNames){
            if(albumNameInLibrary.equals(albumName)){
                albumExists = true;
            }
        }
        return albumExists;
    }


    public static void listAllAlbums(){
        DataSource ds = new DataSource();
        ArrayList<String> albumNamesInLibrary = ds.listAllAlbumNames();
        for (String albumName : albumNamesInLibrary){
            System.out.println(albumName);
        }
    }






    public String getTitle() {
        return title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAlbumArtwork(Blob albumArtwork) {
        this.albumArtwork = albumArtwork;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getLocation() {
        return location;
    }

    public Blob getAlbumArtwork() {
        return albumArtwork;

    }

    public void listAllSongs(){
        DataSource ds = new DataSource();
        ArrayList<Song> allTheSongs = ds.albumSongs(this.title);
        for (Song theSong : allTheSongs){
            System.out.println(theSong.getTitle());
            //System.out.println("    - Song_ID: " + theSong.getSong_ID());
            //System.out.println("    - Album_ID: " + theSong.getAlbum_ID());
            //System.out.println("    - Title: " + theSong.getTitle());
            //System.out.println("    - Track No.: " + theSong.getTrack_number());
            //System.out.println("    - Duration: " + theSong.getDuration());
        }
    }

    public void amountOfSongs(){
        DataSource ds = new DataSource();
        System.out.println("The Album '" + this.title + "' has " + ds.amountOfSongsOnAlbum(this.title) + " songs");
    }

}
