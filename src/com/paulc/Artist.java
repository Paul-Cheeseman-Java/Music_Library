package com.paulc;

import java.util.ArrayList;

public class Artist {

    private String name;

    public Artist(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void removeArtist(String newName){
        /*
            For removing artist (Delete Constraints)

            Foreign Keys with Cascade Delete, delete child (have this on Artist and Album, that way it should cascade through both)
            https://www.techonthenet.com/sqlite/foreign_keys/foreign_delete.php

            Delete Artist (cascaded deletes all of below)
            Delete Album (cascade deletes all songs)
            Delete Song

            For adding (Insert constraints)

            Other constraints, song must have an Album before it can be added, Album must have Artist before it can be added
         */
    }

    public static void artistExist(){

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


    public void listAllAlbums(){
        DataSource ds = new DataSource();
        ArrayList<Album> allTheAlbums = ds.artistsAlbums(this.getName());
        for (Album theAlbum : allTheAlbums){
            System.out.println(theAlbum.getName());
        }
    }


    public static void listAllArtists(){
        DataSource ds = new DataSource();
        ArrayList<Artist> allTheArtists = ds.listAllArtists();
        for (Artist theArtist : allTheArtists){
            System.out.println(theArtist.getName());
        }
    }


}
