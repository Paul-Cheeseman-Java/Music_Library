package com.paulc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Artist {

    private String name;

    //Need to check for artists existence, if exists tell user and don't do anything


    public Artist(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }


    /*
        Using Foreign Key constraint 'Cascade Delete' in both Albums (FK Artist) and Song (FK Album)
        so deleting an artist will delete all associated albums, which in turn, will delete all associated songs
     */
    public void removeArtist(String newName){

    }


    public static void addArtist(){
        //Datastore
        //Check is valid (it DOESN'T exist in DB) - ACTUALLY just report on NON-null returned value IS present
        //Add to DB - return message that update successful? (update method returns boolean?)
        //************************************************************************************
        //Need to ensure that Artist is added with an Album and that Album is added with Songs
        //************************************************************************************
    }

    public void updateArtist(){
        //Datastore
        //Check is valid (it DOESN'T exist in DB) - ACTUALLY just report on null returned value as not present
        //Update
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
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println("Please enter the name of an Artist in the library: ");
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


}
