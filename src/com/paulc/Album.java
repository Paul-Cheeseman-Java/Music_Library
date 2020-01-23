package com.paulc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Album {

    private int album_ID;
    private int artist_ID;      // Kept in as part of original stored object, possibly less confusing for future development
    private String title;
    private String location;

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    // Direct creation from DB read, uses numeric artist_ID
    public Album(int album_ID, int artist_ID, String title, String location){
        this.album_ID = album_ID;
        this.artist_ID = artist_ID;
        this.title = title;
        this.location = location;
    }


    // Creation by app to insert into DB (so no id as yet)
    public Album(String title, String location){
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


    public int getArtist_ID() {
        return artist_ID;
    }


    public int getID() {
        return artist_ID;
    }


    public String getLocation() {
        return location;
    }


    public static int albumNameToAlbumID(String albumName){
        DataSource ds = new DataSource();
        int albumID = ds.albumNameToAlbumID(albumName);
        return albumID;
    }

    public static int returnExistingAlbumID(){
        DataSource ds = new DataSource();
        return ds.artistNameToArtistID(Artist.promptForExistingArtist());
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


    public static int addAlbumReturnID(Artist artist, Album album){
        DataSource ds = new DataSource();
        int generatedKey = ds.insertAlbum(artist.getID(), album.getTitle(), album.getLocation());
        return generatedKey;
    }


    public static int returnExistingArtistID(){
        DataSource ds = new DataSource();
        return ds.albumNameToAlbumID(Album.promptForExistingAlbumTitle());
    }


    public static int addAlbum(int artistID){
        String newAlbumName = Album.promptForNewAlbumTitle(artistID);
        String newLocation = Album.promptForNewLocation();
        DataSource ds = new DataSource();
        int newAlbumDBKey = ds.insertAlbum(artistID, newAlbumName, newLocation);;
      return newAlbumDBKey;
    }


    public static void updateAlbum(){
        int artistID = Artist.returnExistingArtistID();
        String oldTitle = Album.promptForExistingAlbumTitle(artistID);
        String newTitle = Album.promptForNewAlbumTitle(artistID);
        String newLocation = Album.promptForExistingLocation();     //<<----------------------- Is this right, if so check name
        DataSource ds = new DataSource();
        if(newLocation.isEmpty()){
            System.out.println("No new location");
            ds.updateAlbum(artistID, oldTitle, newTitle);
        }
        else {
            System.out.println("New location");
            ds.updateAlbum(artistID, oldTitle, newTitle, newLocation);
        }
    }


    // Very basic at the moment but extracted to a separate method to make sure it can be changed easily.
    private static boolean albumPromptInputValid(String albumName){
        return (!(albumName.equals("") || albumName.contains("   ") || (albumName.contains("@") || albumName.contains("#"))));
    }


    // Very basic at the moment but extracted to a separate method to make sure it can be changed easily.
    private static boolean locationPromptInputValid(String location){
        return (!(location.equals("") || location.contains("   ") || (location.contains("@") || location.contains("#"))));
    }


    public static String promptForNewAlbumTitle() {
        boolean albumExist;  //assuming album exists
        String albumArtist = Artist.promptForExistingArtist();
        String albumName = null;
        try{
            System.out.println("Please enter the new Album title: ");
            albumName = br.readLine().trim();
            while(!Album.albumPromptInputValid(albumName)){
                System.out.println("Please enter a valid new title for the Album: ");
                albumName = br.readLine().trim();
            }
            albumExist = Album.albumExist(albumName, albumArtist);
            while(albumExist){
                System.out.println("'" + albumArtist + "' already has an album " + "'" + albumName +"' in the library");
                System.out.println("Please enter a new album for '" + albumArtist + "': ");
                albumName = br.readLine().trim();
                while(!Album.albumPromptInputValid(albumName)){
                    System.out.println("Please enter a valid new title for the Album: ");
                    albumName = br.readLine().trim();
                }
                albumExist = Album.albumExist(albumName, albumArtist);
            }
        }catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return albumName;
    }



    public static String promptForNewAlbumTitle(int artistID) {
        boolean albumExist;  //assuming album exists
        String albumArtist = Artist.returnArtist(artistID).getName();
        String albumName = null;

        try{
            System.out.println("Please enter the new Album title: ");
            albumName = br.readLine().trim();
            while(!Album.albumPromptInputValid(albumName)){
                System.out.println("Please enter a valid new title for the Album: ");
                albumName = br.readLine().trim();
            }
            albumExist = Album.albumExist(albumName, albumArtist);
            while(albumExist){
                System.out.println("'" + albumArtist + "' already has an album " + "'" + albumName +"' in the library");
                System.out.println("Please enter a new album for '" + albumArtist + "': ");
                albumName = br.readLine().trim();
                while(!Album.albumPromptInputValid(albumName)){
                    System.out.println("Please enter a valid new title for the Album: ");
                    albumName = br.readLine().trim();
                }
                albumExist = Album.albumExist(albumName, albumArtist);
            }
        }catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return albumName;
    }


    public static String promptForExistingAlbumTitle(int artistID) {
        boolean existingAlbum = false;
        String albumName = null;
        try{
            String albumArtist = Artist.returnArtist(artistID).getName();
            System.out.println("Please enter the title of an album in the library: ");
            albumName = br.readLine();
            //existingAlbum = Album.albumExist(albumName, albumArtist);
            existingAlbum = Album.albumExist(albumName, albumArtist);
            while(!existingAlbum){
                System.out.println("'" + albumArtist + "' does not an album " + "'" + albumName +"' in the library");
                System.out.println("Please enter the title of a '" + albumArtist + "' album in the library: ");
                albumName = br.readLine().trim();
                existingAlbum = Album.albumExist(albumName, albumArtist);
            }
        }catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return albumName;
    }


    public static String promptForExistingAlbumTitle() {
        boolean existingAlbum = false;
        String albumName = null;
        try{
            String albumArtist = Artist.promptForExistingArtist();
            System.out.println("Please enter the title of an album in the library: ");
            albumName = br.readLine().trim();
            //existingAlbum = Album.albumExist(albumName, albumArtist);
            existingAlbum = Album.albumExist(albumName, albumArtist);
            while(!existingAlbum){
                System.out.println("'" + albumArtist + "' does not an album " + "'" + albumName +"' in the library");
                System.out.println("Please enter the title of a '" + albumArtist + "' album in the library: ");
                albumName = br.readLine().trim();
                existingAlbum = Album.albumExist(albumName, albumArtist);
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

            //Get valid input
             while(!Album.locationPromptInputValid(location)){
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
            System.out.println("Song in library: " +albumNameInLibrary.getTitle() + ", Album searching for: " +albumName);
            if(albumNameInLibrary.getTitle().toLowerCase().equals(albumName.toLowerCase())){
                existingAlbum = true;
            }
        }
        return existingAlbum;
    }


    public void listAllSongs(){
        DataSource ds = new DataSource();
        ArrayList<Song> allTheSongs = ds.albumSongs(this.title);
        for (Song theSong : allTheSongs){
            System.out.println("              Song: " + theSong.getTitle());
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
