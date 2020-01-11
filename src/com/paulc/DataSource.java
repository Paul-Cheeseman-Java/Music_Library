package com.paulc;

//**************************************************************************************
//Put in column indexes as numbers too as STATIC variables...
//Put in selected columns eg '(results.getString("Name"))' to reference STATIC variables
//**************************************************************************************


 
import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataSource {


    private static final String DB_NAME = "Music_Library.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\Monkey\\Java11Projects\\Music_Library\\" + DB_NAME;

    //Listing out Tables/Columns as constants for best practice
    private static final String TABLE_ARTISTS = "Artists";
    private static final String COLUMN_ARTISTS_ARTIST_ID = "Artist_ID";
    private static final String COLUMN_ARTISTS_ARTIST_NAME = "Name";

    //Listing out Tables/Columns as constants for best practice
    private static final String TABLE_ALBUMS = "Albums";
    private static final String COLUMN_ALBUMS_ALBUM_ID = "Album_ID";
    private static final String COLUMN_ALBUMS_ARTIST_ID = "Artist_ID";
    private static final String COLUMN_ALBUMS_TITLE = "Title";
    private static final String COLUMN_ALBUMS_ALBUM_ARTWORK = "Album_Artwork";
    private static final String COLUMN_ALBUMS_LOCATION = "Location";


    //Listing out Tables/Columns as constants for best practice
    private static final String TABLE_SONGS = "Songs";
    private static final String COLUMN_SONGS_SONG_ID = "Song_ID";
    private static final String COLUMN_SONGS_ALBUM_ID = "Album_ID";
    private static final String COLUMN_SONGS_TITLE = "Title";
    private static final String COLUMN_SONGS_TRACK_NUMBER = "Track_Number";
    private static final String COLUMN_SONGS_DURATION = "Duration";


    private static final String LIST_ALL_ARTISTS = "SELECT " + COLUMN_ARTISTS_ARTIST_NAME + " FROM " +
            TABLE_ARTISTS;

    private static final String DELETE_ARTIST = "DELETE FROM " + TABLE_ARTISTS + " WHERE " + COLUMN_ARTISTS_ARTIST_NAME + "= ?";


    private static final String DELETE_ALBUM = "DELETE FROM " + TABLE_ALBUMS + " WHERE " + COLUMN_ALBUMS_TITLE + "= ?";

    private static final String DELETE_SONG = "DELETE FROM " + TABLE_SONGS + " WHERE " + COLUMN_SONGS_TITLE + "= ?";

    private static final String LIST_ARTIST_ALBUMS = "SELECT " + TABLE_ALBUMS + "." + COLUMN_ALBUMS_TITLE + " FROM " +
            TABLE_ALBUMS + " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ARTISTS + "." +
            COLUMN_ARTISTS_ARTIST_ID + " = " + TABLE_ALBUMS + "." + COLUMN_ALBUMS_ARTIST_ID + " WHERE " +
            TABLE_ARTISTS + "." + COLUMN_ARTISTS_ARTIST_NAME + "= ?";

    private static final String LIST_ALBUM_SONGS = "SELECT * FROM " +
            TABLE_SONGS + " INNER JOIN " + TABLE_ALBUMS + " ON " + TABLE_SONGS + "." +
            COLUMN_ALBUMS_ALBUM_ID + " = " + TABLE_ALBUMS + "." + COLUMN_ALBUMS_ALBUM_ID + " WHERE " +
            TABLE_ALBUMS + "." + COLUMN_ALBUMS_TITLE + "= ?";

    private static final String AMOUNT_OF_SONGS_ON_ALBUM = "SELECT " + TABLE_ALBUMS + "." + COLUMN_ALBUMS_TITLE + ", " +
            "COUNT(*) FROM " + TABLE_ALBUMS + " INNER JOIN  " + TABLE_SONGS + " ON " + TABLE_ALBUMS + "." + COLUMN_ALBUMS_ALBUM_ID + " = " +
            TABLE_SONGS + "." + COLUMN_SONGS_ALBUM_ID + " WHERE " + TABLE_ALBUMS + "." + COLUMN_ALBUMS_TITLE + " = ?";

    private static final String AMOUNT_OF_ALBUMS_ARTIST_HAS = "SELECT " + TABLE_ARTISTS + "." + COLUMN_ARTISTS_ARTIST_NAME + ", " +
            "COUNT(*) FROM " + TABLE_ARTISTS + " INNER JOIN  " + TABLE_ALBUMS + " ON " + TABLE_ARTISTS + "." + COLUMN_ARTISTS_ARTIST_ID + " = " +
            TABLE_ALBUMS + "." + COLUMN_ALBUMS_ARTIST_ID + " WHERE " + TABLE_ARTISTS + "." + COLUMN_ARTISTS_ARTIST_NAME + " = ?";





    private Connection connect() {
        // SQLite connection string
        String url = CONNECTION_STRING;
        Connection conn = null;
        try {
            //Differs from normal connection code as need to enforce foreign keys in SQLite each time a connection is made to db
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            conn = DriverManager.getConnection(url, config.toProperties());
        } catch (SQLException e) {
            System.out.println("SQL Error in Connection: " + e.getMessage());
        }
        return conn;
    }


    public void testQuery()
    {
        System.out.println("Query: " +AMOUNT_OF_SONGS_ON_ALBUM);
    }

    public void deleteArtist(String artistName) {
        try (Connection conn = this.connect()) {
            PreparedStatement delete_artist_ps = conn.prepareStatement(DELETE_ARTIST);
            delete_artist_ps.setString(1, artistName);
            delete_artist_ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Error in deleteArtist: " + e);
        }
    }

    public void deleteAlbum(String albumTitle) {
        try (Connection conn = this.connect()) {
            PreparedStatement delete_album_ps = conn.prepareStatement(DELETE_ALBUM);
            delete_album_ps.setString(1, albumTitle);
            delete_album_ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Error in deleteArtist: " + e);
        }
    }

    public void deleteSong(String songTitle) {
        try (Connection conn = this.connect()) {
            PreparedStatement delete_song_ps = conn.prepareStatement(DELETE_SONG);
            delete_song_ps.setString(1, songTitle);
            delete_song_ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Error in deleteSong: " + e);
        }
    }


    public int amountOfAlbumArtistHas(String artist) {
        int amountOfAlbums = 0;
        try (Connection conn = this.connect()) {
            PreparedStatement amountOfAlbumArtistHas_ps = conn.prepareStatement(AMOUNT_OF_ALBUMS_ARTIST_HAS);
            amountOfAlbumArtistHas_ps.setString(1, artist);
            ResultSet results = amountOfAlbumArtistHas_ps.executeQuery();
            while (results.next()) {
                amountOfAlbums = results.getInt(2);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in amountOfAlbumArtistHas: " + e);
        }
        return amountOfAlbums;
    }


    public int amountOfSongsOnAlbum(String albumName) {
        int amountOfSongs = 0;
        try (Connection conn = this.connect()) {
            PreparedStatement amountOfSongsOnAlbum_ps = conn.prepareStatement(AMOUNT_OF_SONGS_ON_ALBUM);
            amountOfSongsOnAlbum_ps.setString(1, albumName);
            ResultSet results = amountOfSongsOnAlbum_ps.executeQuery();
            while (results.next()) {
                amountOfSongs = results.getInt(2);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in amountOfSongsOnAlbum: " + e);
        }
        return amountOfSongs;
    }


    public ArrayList<Album> artistsAlbums(String artistName) {
        ArrayList<Album> albumsOfArtist = new ArrayList<>();
        try (Connection conn = this.connect()) {
            PreparedStatement list_artist_albums_ps = conn.prepareStatement(LIST_ARTIST_ALBUMS);
            list_artist_albums_ps.setString(1, artistName);
            ResultSet results = list_artist_albums_ps.executeQuery();
            while (results.next()) {
                albumsOfArtist.add(new Album(results.getString("Title")));
                //System.out.println("Title: "+ results.getString("Title"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in artistsAlbums: " + e);
        }
        return albumsOfArtist;
    }


    public ArrayList<Artist> listAllArtists() {
        ArrayList<Artist> allArtists = new ArrayList<>();
        try (Connection conn = this.connect())
        {
            PreparedStatement list_all_artists_ps = conn.prepareStatement(LIST_ALL_ARTISTS);
            ResultSet results = list_all_artists_ps.executeQuery();
            while (results.next()) {
                allArtists.add(new Artist(results.getString("Name")));
                //System.out.println(results.getString("Name"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in listAllArtists: " + e);
        }
        return allArtists;
    }


    public ArrayList<Song> albumSongs(String albumName) {
        ArrayList<Song> songsOfAlbum = new ArrayList<>();
        try (Connection conn = this.connect()) {
            PreparedStatement list_all_albums_ps = conn.prepareStatement(LIST_ALBUM_SONGS);
            list_all_albums_ps.setString(1, albumName);
            ResultSet results = list_all_albums_ps.executeQuery();
            while (results.next()) {
                songsOfAlbum.add(new Song(
                        results.getInt("Song_ID"),
                        results.getInt("Album_ID"),
                        results.getString("Title"),
                        results.getInt("Track_Number"),
                        results.getInt("Duration")
                        ));
                /*
                System.out.println("Song_ID: "+ results.getString("Song_ID"));
                System.out.println("Album_ID: "+ results.getString("Album_ID"));
                System.out.println("Title: "+ results.getString("Title"));
                System.out.println("Track No.: "+ results.getString("Track_Number"));
                System.out.println("Duration: "+ results.getString("Duration"));
                 */
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in albumSongs: " + e);
        }
        return songsOfAlbum;
    }



}


