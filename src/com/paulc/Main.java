package com.paulc;


public class Main {

    public static void main(String[] args) {
        //Application.start();
        //System.out.println(Song.promptForNewSongTitle(1));

        //THIS ISN'T WORKING!!!
        //DataSource ds = new DataSource();
        //ds.displayQuery();

        Song.addSong(Album.albumNameToAlbumID(Album.promptForExistingAlbumTitle()));
    }
}
