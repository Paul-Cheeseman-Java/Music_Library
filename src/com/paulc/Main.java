package com.paulc;


public class Main {

    public static void main(String[] args) {
	// write your code here
        String songName = Song.promptForSong();
        if (!songName.equals("")){
            Song.addSong(songName, Album.getAlbumIDFromName());
        }
    }
}
