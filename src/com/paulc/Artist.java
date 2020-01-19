package com.paulc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Artist {

    private String name;

    public Artist(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    //close this when app closes??
    //Stream Reader for class
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void removeArtist(String artistName){
        DataSource ds = new DataSource();
        ds.deleteArtist(artistName);
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


    public void updateArtistName(String newName){
        if (Artist.artistExist(newName)){
            System.out.println(newName + " is already in the library");
        } else {
            DataSource ds = new DataSource();
            ds.updateArtist(this.getName(), newName);
        }
    }


    public static int artistNameToArtistID(String artistName){
        DataSource ds = new DataSource();
        int newArtistDBKey = ds.artistNameToArtistID(artistName);
        return newArtistDBKey;
    }


    //Validation method as validation could/should be more in-depth and this would allow easy changes
    private static boolean artistPromptInputValid(String albumName){
        return (!(albumName.equals("") || albumName.equals(" ") || albumName.equals("  ") || albumName.contains("   ")));
    }


    public static String promptForNewArtist() {
        String artistName = null;
        try{
            System.out.println("Please enter the name of the Artist: ");
            artistName = br.readLine();

            while(!Artist.artistPromptInputValid(artistName)){
                System.out.println("Please enter a valid name for the Artist: ");
                artistName = br.readLine();
            }
            if (Artist.artistExist(artistName)){
                while(Artist.artistExist(artistName)){
                    System.out.println(artistName + " is already the library, please enter a new Artist");
                    artistName = br.readLine();

                }
            }
        }catch(IOException e) {
            System.out.println("Error prompting for Artist new name: " +e.getMessage());
        }
        return artistName;
    }



    public static boolean artistExist(String artistName) {
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


    public static String promptForExistingArtist() {
        String artistName = null;
        try{
            System.out.println("Please enter the name of the Artist: ");
            artistName = br.readLine();
            while(!Artist.artistExist(artistName)){
                System.out.println(artistName + " isn't an Artist in the library, please enter the Artist in the library");
                artistName = artistName = br.readLine();

            }
        }catch(IOException e) {
            System.out.println("Error prompting for Artist name: " +e.getMessage());
        }
        return artistName;
    }


    public int amountOfAlbums(){
        DataSource ds = new DataSource();
        return ds.amountOfAlbumsArtistHas(this.name);
    }

}
