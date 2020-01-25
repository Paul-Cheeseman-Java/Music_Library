package com.paulc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Song {

    private int song_ID;
    private int album_ID;
    private String title;

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public Song(int song_ID, int album_ID, String title) {
        this.song_ID = song_ID;
        this.album_ID = album_ID;
        this.title = title;
    }

    public Song(int album_ID, String title) {
        this.album_ID = album_ID;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static boolean addSong(int albumID){
        boolean addAnotherSong = false;
        String newSongName = Song.promptForNewSongTitle(albumID);
        while(!newSongName.isEmpty()){
            DataSource ds = new DataSource();
            ds.insertSong(albumID, newSongName);
            newSongName = Song.promptForNewSongTitle(albumID);
            addAnotherSong = true;
        }
        return addAnotherSong;
    }


    public static void updateSong(){
        int newSongsArtistID = Artist.artistNameToArtistID(Artist.promptForExistingArtist());
        String newSongAlbumName = Album.promptForExistingAlbumTitle(newSongsArtistID);
        int newSongsAlbumID = Album.albumNameToAlbumID(newSongAlbumName);
        if (Album.albumHasNoSongs(newSongAlbumName)){
            System.out.println("'" +newSongAlbumName + "' doesn't currently have any songs in it to update");
        }
        else{
            String oldTitle = Song.promptForExistingSong(newSongsAlbumID);
            String newTitle = Song.promptForNewSongTitle(newSongsAlbumID);
            if (newTitle.isEmpty()){
                newTitle = oldTitle;
            }
            DataSource ds = new DataSource();
            ds.updateSong(newSongsAlbumID, oldTitle, newTitle);
        }

    }



    public static void deleteSong(){
        int songToBeDeletedAlbumID = Album.albumNameToAlbumID(Album.promptForExistingAlbumTitle());
        String songToBeDeletedTitle = Song.promptForExistingSong(songToBeDeletedAlbumID);
        DataSource ds = new DataSource();
        ds.deleteSong(songToBeDeletedAlbumID, songToBeDeletedTitle);
    }


    // Very basic at the moment but extracted to a separate method to make sure it will only need to be updated in one place.
    private static boolean songPromptInputValid(String songTitle){
        return (!(songTitle.contains("@") || songTitle.contains("#")));
    }


    public static String promptForNewSongTitle(int albumID) {
        String songTitle = null;
        try{
            String albumName = Album.returnAlbum(albumID).getTitle();
            System.out.println("Please enter the new song (or hit return to exit): ");
            songTitle = br.readLine().trim();

            while(!Song.songPromptInputValid(songTitle)){
                System.out.println("Please enter a valid title for the song (or hit return to exit): ");
                songTitle = br.readLine().trim();
            }
            while(Song.songExist(songTitle, albumName)){
                System.out.println("'" + songTitle + "' is already the library for " +albumName +", please enter a new song:");
                songTitle = br.readLine().trim();
            }
        }catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return songTitle;
    }


    public static String promptForExistingSong(int album_ID) {
        String songTitle = null;
        try{
            String songAlbum = Album.returnAlbum(album_ID).getTitle();
            System.out.println("Please enter the name of a song on '" + songAlbum+ "': ");
            songTitle = br.readLine().trim();
            while(!Song.songExist(songTitle, songAlbum)){
                System.out.println("'" + songTitle + "' is not a song on '" +songAlbum+ "' Please enter a song on the album: ");
                songTitle = br.readLine().trim();
            }
        }catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return songTitle;
    }


    public static boolean songExist(String songName, String albumName) {
        DataSource ds = new DataSource();
        ArrayList<Song> allSongs = ds.albumSongs(albumName);
        boolean existingSong = false;
        for(Song songInAlbum: allSongs){
            if(songInAlbum.getTitle().toLowerCase().equals(songName.toLowerCase())){
                existingSong = true;
                break;
            }
        }
        return existingSong;
    }


    public static void closeStream(){
        try{
            br.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

}
