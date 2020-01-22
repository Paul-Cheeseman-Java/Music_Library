package com.paulc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Artist {

    private String name;
    private int id;

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public Artist(int artistID, String name){
        this.id = artistID;
        this.name = name;
    }


    public String getName(){
        return this.name;
    }

    public int getID(){
        return this.id;
    }


    public static void deleteArtist(){
        String artistName = Artist.promptForExistingArtist();
        DataSource ds = new DataSource();
        ds.deleteArtist(artistName);
    }


    public static ArrayList<String> returnArtistsAlbumNames(){
        DataSource ds = new DataSource();
        String artist = Artist.promptForExistingArtist();
        ArrayList<String> artistsAlbumNames = new ArrayList<>();
        ArrayList<Album> artistsAlbums = ds.artistsAlbums(artist);
        for (Album album : artistsAlbums ){
            artistsAlbumNames.add(album.getTitle());
        }
        return artistsAlbumNames;
    }


    public static void listArtistsAlbums(){
        DataSource ds = new DataSource();
        String artist = Artist.promptForExistingArtist();
        ArrayList<Album> artistsAlbums = ds.artistsAlbums(artist);
        if (artistsAlbums.isEmpty()){
            System.out.println("Artist: " + artist + " doesn't have any albums in the library");
        } else {
            System.out.println("Artist: " + artist + " has the following Albums");
            for(Album album: artistsAlbums) {
                System.out.println(album.getTitle());
            }
        }
    }


    public static void listArtistsAlbumsAndSongs(){
        DataSource ds = new DataSource();
        String artist = Artist.promptForExistingArtist();
        ArrayList<Album> artistsAlbums = ds.artistsAlbums(artist);
        if (artistsAlbums.isEmpty()){
            System.out.println("Artist: " + artist + " doesn't have any albums in the library");
        } else {
            System.out.println("Artist: " + artist + " has the following Albums:");
            for(Album album: artistsAlbums) {
                System.out.println("        Album " + album.getTitle() + " has the following songs:");
                album.listAllSongs();
            }
        }
    }

    public static int addArtistReturnID(){
        DataSource ds = new DataSource();
        int generatedKey = ds.insertArtist(Artist.promptForNewArtist());
        return generatedKey;
    }

    public static int returnExistingArtistID(){
        DataSource ds = new DataSource();
        return ds.artistNameToArtistID(Artist.promptForExistingArtist());
    }

    public static Artist returnArtist(int artistID){
        DataSource ds = new DataSource();
        return ds.returnArtist(artistID);
    }



    public static void updateArtist(){
        String currentName = Artist.promptForExistingArtist();
        String newName = Artist.promptForNewArtist();
        if (Artist.artistExist(newName)){
            System.out.println("'" + newName + "' is already in the library");
        } else {
            DataSource ds = new DataSource();
            ds.updateArtist(currentName, newName);
        }
    }


    public static int artistNameToArtistID(String artistName){
        DataSource ds = new DataSource();
        int newArtistDBKey = ds.artistNameToArtistID(artistName);
        return newArtistDBKey;
    }


    // Validation for user input.
    // Very basic at the moment but extracted to a separate method to make sure it will only need to be updated in one place.
    private static boolean artistPromptInputValid(String albumName){
        return (!(albumName.equals("") || albumName.equals(" ") || albumName.equals("  ") || albumName.contains("   ")));
    }


    public static String promptForNewArtist() {
        String artistName = null;
        try{
            System.out.println("Please enter the name of the new Artist: ");
            artistName = br.readLine();

            while(!Artist.artistPromptInputValid(artistName)){
                System.out.println("Please enter a valid name for the Artist: ");
                artistName = br.readLine();
            }
            if (Artist.artistExist(artistName)){
                while(Artist.artistExist(artistName)){
                    System.out.println("'" + artistName + "' is already the library, please enter a new Artist");
                    artistName = br.readLine();
                }
            }
        }catch(IOException e) {
            System.out.println("Error prompting for New Artist name: " +e.getMessage());
        }
        return artistName;
    }


    public static String promptForExistingArtist() {
        String artistName = null;
        try{
            System.out.println("Please enter the name of an Artist in the library: ");
            artistName = br.readLine();
            while(!Artist.artistExist(artistName)){
                System.out.println("'" + artistName + "' isn't an Artist in the library, please enter an Artist in the library");
                artistName = br.readLine();

            }
        }catch(IOException e) {
            System.out.println("Error prompting for Existing Artist name: " +e.getMessage());
        }
        return artistName;
    }


    private static boolean artistExist(String artistName) {
        DataSource ds = new DataSource();
        ArrayList<String> allArtistsNames = ds.listAllArtistNames();
        boolean artistExists = false;
        for(String artistNameInLibrary: allArtistsNames){
            if(artistNameInLibrary.equals(artistName)){
                artistExists = true;
            }
        }
        return artistExists;
    }


    public static void listAllArtists(){
        DataSource ds = new DataSource();
        ArrayList<String> allTheArtists = ds.listAllArtistNames();
        for (String artistsName : allTheArtists){
            System.out.println(artistsName);
        }
    }

    public static void closeStream(){
        try{
            br.close();
        }
        catch(IOException e) {
            System.out.println("Problem closing Artist's stream: " + e.getMessage());
        }
    }


}
