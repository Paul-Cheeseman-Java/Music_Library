package com.paulc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Album {

    private int artist_ID;      //Required
    private String title;       //Required
    private String location;    //Required

    //close this when app closes??
    //Stream Reader for class
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void closeStream(){
        try{
            br.close();
        } catch (IOException e){
            System.out.println("Problem closing Album's stream: " + e.getMessage());

        }
    }


    //Direct creation from DB read, uses artist_ID
    public Album(int artist_ID, String title, String location){
        this.artist_ID = artist_ID;
        this.title = title;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getLocation() {
        return location;
    }

    public static int albumNameToAlbumID(String albumName){
        DataSource ds = new DataSource();
        int albumID = ds.albumNameToArtistID(albumName);
        return albumID;
    }



    //Creation by user input, using Artist Name and a lookup for ID as user will not know Artist_ID
    public Album(String artistName, String title, String location){
        this.artist_ID = Artist.artistNameToArtistID(artistName);
        this.title = title;
        this.location = location;
    }

    public static void listAllAlbums(Artist theArtist){
        DataSource ds = new DataSource();
        ArrayList<Album> allTheAlbums = ds.artistsAlbums(theArtist.getName());
        for (Album theAlbum : allTheAlbums){
            System.out.println(theAlbum.getTitle());
        }
    }

    public static void removeAlbum(){
        String albumName = Album.promptForExistingAlbumTitle();
        DataSource ds = new DataSource();
        ds.deleteAlbum(albumName);
    }


    public static int getAlbumIDFromName(){
        return Album.albumNameToAlbumID(Album.promptForExistingAlbumTitle());
    }


    public static int addAlbum(int artistID){
        String newTitle = Album.promptForNewAlbumTitle();
        String newLocation = Album.promptForNewLocation();
        DataSource ds = new DataSource();
        int newAlbumDBKey = ds.insertAlbum(artistID, newTitle, newLocation);;
        return newAlbumDBKey;
    }

    public static void updateAlbum(){
        String oldTitle = Album.promptForExistingAlbumTitle();
        String newTitle = Album.promptForExistingAlbumTitle();
        String newLocation = Album.promptForNewLocation();
        DataSource ds = new DataSource();
        ds.updateAlbum(oldTitle, newTitle, newLocation);
    }


    //Validation method as validation could/should be more in-depth and this would allow easy changes
    private static boolean albumPromptInputValid(String albumName){
        return (!(albumName.equals("") || albumName.equals(" ") || albumName.equals("  ") || albumName.contains("   ")));
    }

    //
    private static boolean locationPromptInputValid(String location){
        return (!(location.equals("") || location.equals(" ") || location.equals("  ") || location.contains("   ")));
    }


    public static String promptForExistingAlbumTitle() {
        String albumName = null;
        try{
            System.out.println("Please enter the title of an Album in the library: ");
            albumName = br.readLine();
            //Check for album existence before creating
            while(!Album.albumExist(albumName)){
                System.out.println("'" + albumName + "' is not in the library, please enter an album in the library:");
                albumName = br.readLine();
                //Get valid input if first album input existed
            }
        }catch(IOException e) {
            System.out.println("Error prompting for Album name: " +e.getMessage());
        }
        return albumName;
    }


    //Couldn't use try-with-resources with BufferedReader, InputStreamReader, System.in
    //when (auto)closing it in one method would mean System.in would be closed when other methods tried to use it
    public static String promptForNewAlbumTitle() {
        String albumName = null;
        try{
            System.out.println("Please enter the title of the new Album: ");
            albumName = br.readLine();
            //Get valid input
            while(!Album.albumPromptInputValid(albumName)){
                System.out.println("Please enter a valid title for the new Album: ");
                albumName = br.readLine();
            }
            //Check for album existence before creating
            while(Album.albumExist(albumName)){
                System.out.println("'" + albumName + "' is already in the library, please enter a valid album name:");
                albumName = br.readLine();

                //Get valid input if first album input existed
                while(!Album.albumPromptInputValid(albumName)){
                    System.out.println("Please enter a valid title for the new/updated Album: ");
                    albumName = br.readLine();
               }
            }
        }catch(IOException e) {
            System.out.println("Error prompting for new Album name: " +e.getMessage());
        }
        return albumName;
    }


    public static String promptForNewLocation() {
        String location = null;
        try{
            System.out.println("Please enter the location of the Albums directory: ");
            location = br.readLine();

            //Get valid input
             while(!Album.locationPromptInputValid(location)){
                 System.out.println("Please enter a valid location for the Albums directory: ");
                 location = br.readLine();
              }
        }catch(IOException e) {
            System.out.println("Error prompting for new Location: " +e.getMessage());
        }
        return location;
    }


    public static String promptForExistingLocation() {
        String location = null;
        try{
            System.out.println("Please enter the directory of the albums music files (hit return to keep existing): ");
            location = br.readLine();
        }catch(IOException e) {
            System.out.println("Error prompting for new Location: " +e.getMessage());
        }
        return location;
    }


    public static void deleteAlbum(String albumName){
        DataSource ds = new DataSource();
        ds.deleteAlbum(albumName);
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


    public void listAllSongs(){
        DataSource ds = new DataSource();
        ArrayList<Song> allTheSongs = ds.albumSongs(this.title);
        for (Song theSong : allTheSongs){
            System.out.println("              Song: " + theSong.getTitle());
        }
    }

    public int amountOfSongs(){
        DataSource ds = new DataSource();
        return ds.amountOfSongsOnAlbum(this.title);
    }

}
