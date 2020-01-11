package com.paulc;

import java.util.ArrayList;

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
    private String name;

    public Album(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void listAllSongs(){
        DataSource ds = new DataSource();
        ArrayList<Song> allTheSongs = ds.albumSongs(this.name);
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
        System.out.println("The Album '" + this.name + "' has " + ds.amountOfSongsOnAlbum(this.name) + " songs");
    }

}
