package com.paulc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Album {

    private int artist_ID;      // Kept in as part of original stored object, possibly less confusing for future development
    private String title;
    private String location;

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public Album(int artist_ID, String title, String location){
        this.artist_ID = artist_ID;
        this.title = title;
        this.location = location;
    }


    // Creation by user input, using the String Artist Name and a lookup for ID as user will not know Artist_ID
    public Album(String artistName, String title, String location){
        this.artist_ID = Artist.artistNameToArtistID(artistName);
        this.title = title;
        this.location = location;
    }


    public String getTitle() {
        return title;
    }


    public static int albumNameToAlbumID(String albumName){
        DataSource ds = new DataSource();
        return ds.albumNameToAlbumID(albumName);
    }


    public static Album returnAlbum(int albumID){
        DataSource ds = new DataSource();
        return ds.returnAlbum(albumID);
    }

    public static void deleteAlbum(){
        int artistID = Artist.returnExistingArtistID();
        String albumName = Album.promptForExistingAlbumTitle(artistID);
        DataSource ds = new DataSource();
        ds.deleteAlbum(artistID, albumName);
    }


    public static int addAlbum(int artistID){
        String newAlbumName = Album.promptForNewAlbumTitle(artistID);
        String newLocation = Album.promptForNewLocation();
        DataSource ds = new DataSource();
        return ds.insertAlbum(artistID, newAlbumName, newLocation);
    }


    public static void updateAlbum(){
        int artistID = Artist.returnExistingArtistID();
        String artistName = Artist.returnArtist(artistID).getName();
        if (Artist.artistHasNoAlbums(artistName)){
            System.out.println("'" +artistName + "' doesn't have any albums in the library to update.");
        }
        else{
            String oldTitle = Album.promptForExistingAlbumTitle(artistID);
            String newTitle = Album.promptForNewAlbumTitle(artistID);
            String newLocation = Album.promptForExistingLocation();
            DataSource ds = new DataSource();
            if(newLocation.isEmpty()){
                ds.updateAlbum(artistID, oldTitle, newTitle);
            }
            else {
                ds.updateAlbum(artistID, oldTitle, newTitle, newLocation);
            }
        }
    }


    // Very basic at the moment but extracted to a separate method to make sure it can be changed easily.
    private static boolean albumPromptInputInvalid(String albumName){
        return (albumName.equals("") || albumName.contains("   ") || (albumName.contains("@") || albumName.contains("#")));
    }


    // Very basic at the moment but extracted to a separate method to make sure it can be changed easily.
    private static boolean locationPromptInputInvalid(String location){
        return (location.equals("") || location.contains("   ") || (location.contains("@") || location.contains("#")));
    }



    public static String promptForNewAlbumTitle(int artistID) {
        boolean albumExist;  //assuming album exists
        String albumArtist = Artist.returnArtist(artistID).getName();
        String albumName = null;

        try{
            System.out.println("Please enter the new Album title: ");
            albumName = br.readLine().trim();
            while(Album.albumPromptInputInvalid(albumName)){
                System.out.println("Please enter a valid new title for the Album: ");
                albumName = br.readLine().trim();
            }
            albumExist = Album.albumExist(albumName.toLowerCase(), albumArtist.toLowerCase());
            while(albumExist){
                System.out.println("'" + albumArtist + "' already has an album " + "'" + albumName +"' in the library");
                System.out.println("Please enter a new album for '" + albumArtist + "': ");
                albumName = br.readLine().trim();
                while(Album.albumPromptInputInvalid(albumName)){
                    System.out.println("Please enter a valid new title for the Album: ");
                    albumName = br.readLine().trim();
                }
                albumExist = Album.albumExist(albumName.toLowerCase(), albumArtist.toLowerCase());
            }
        }catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return albumName;
    }


    public static String promptForExistingAlbumTitle(int artistID) {
        boolean existingAlbum;
        String albumName = null;
        try{
            String albumArtist = Artist.returnArtist(artistID).getName();
            System.out.println("Please enter the title of an album in the library: ");
            albumName = br.readLine();
            existingAlbum = Album.albumExist(albumName.toLowerCase(), albumArtist.toLowerCase());
            while(!existingAlbum){
                System.out.println("'" + albumArtist + "' does not have an album " + "'" + albumName +"' in the library");
                System.out.println("Please enter the title of a '" + albumArtist + "' album in the library: ");
                albumName = br.readLine().trim();
                existingAlbum = Album.albumExist(albumName.toLowerCase(), albumArtist.toLowerCase());
            }
        }catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return albumName;
    }


    public static String promptForExistingAlbumTitle() {
        boolean existingAlbum;
        String albumName = null;
        try{
            String albumArtist = Artist.promptForExistingArtist();
            System.out.println("Please enter the title of an album in the library: ");
            albumName = br.readLine().trim();
            existingAlbum = Album.albumExist(albumName.toLowerCase(), albumArtist.toLowerCase());
            while(!existingAlbum){
                System.out.println("'" + albumArtist + "' does not an album " + "'" + albumName +"' in the library");
                System.out.println("Please enter the title of a '" + albumArtist + "' album in the library: ");
                albumName = br.readLine().trim();
                existingAlbum = Album.albumExist(albumName.toLowerCase(), albumArtist.toLowerCase());
            }
        }catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return albumName;
    }


    public static String promptForNewLocation() {
        String location = null;
        try{
            System.out.println("Please enter the location of the albums directory: ");
            location = br.readLine().trim();
             while(Album.locationPromptInputInvalid(location)){
                 System.out.println("Please enter a valid location for the albums directory: ");
                 location = br.readLine().trim();
              }
        }catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return location;
    }


    public static String promptForExistingLocation() {
        String location = null;
        try{
            System.out.println("Please enter a new directory for the albums music files (or hit return to keep existing): ");
            location = br.readLine().trim();
        }catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return location;
    }


    public static boolean albumExist(String albumName,   String albumArtist) {
        DataSource ds = new DataSource();
        ArrayList<Album> allAlbumNames = ds.artistsAlbums(albumArtist);
        boolean existingAlbum = false;
        for(Album albumNameInLibrary: allAlbumNames){
            if(albumNameInLibrary.getTitle().toLowerCase().equals(albumName.toLowerCase())){
                existingAlbum = true;
                break;
            }
        }
        return existingAlbum;
    }


    public static boolean albumHasNoSongs(String albumTitle){
        DataSource ds = new DataSource();
        ArrayList<Song> allTheSongs = ds.albumSongs(albumTitle);
        return allTheSongs.isEmpty();
    }

    public void listAllSongs(){
        DataSource ds = new DataSource();
        ArrayList<Song> allTheSongs = ds.albumSongs(this.title);
        for (Song theSong : allTheSongs){
            System.out.println("              " + theSong.getTitle());
        }
    }


    public static void closeStream(){
        try{
            br.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

}
