package com.paulc;

import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.util.ArrayList;

public class DataSource {

    private static final String DB_NAME = "Music_Library.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\Monkey\\Java11Projects\\Music_Library\\" + DB_NAME;

    private static final String TABLE_ARTISTS = "Artists";
    private static final String COLUMN_ARTISTS_ARTIST_ID = "Artist_ID";
    private static final String COLUMN_ARTISTS_ARTIST_NAME = "Name";

    private static final String TABLE_ALBUMS = "Albums";
    private static final String COLUMN_ALBUMS_ALBUM_ID = "Album_ID";
    private static final String COLUMN_ALBUMS_ARTIST_ID = "Artist_ID";
    private static final String COLUMN_ALBUMS_TITLE = "Title";
    private static final String COLUMN_ALBUMS_LOCATION = "Location";

    private static final String TABLE_SONGS = "Songs";
    private static final String COLUMN_SONGS_SONG_ID = "Song_ID";
    private static final String COLUMN_SONGS_ALBUM_ID = "Album_ID";
    private static final String COLUMN_SONGS_TITLE = "Title";

    private static final String LIST_ALL_ARTISTS = "SELECT " + COLUMN_ARTISTS_ARTIST_NAME + " FROM " + TABLE_ARTISTS + " ORDER BY " + COLUMN_ARTISTS_ARTIST_NAME;
    private static final String LIST_SPECIFIC_ARTIST = "SELECT * FROM " + TABLE_ARTISTS + " WHERE " + COLUMN_ARTISTS_ARTIST_ID + " = ?" + " ORDER BY " + COLUMN_ARTISTS_ARTIST_NAME;
    private static final String LIST_SPECIFIC_ALBUM = "SELECT * FROM " + TABLE_ALBUMS + " WHERE " + COLUMN_ALBUMS_ALBUM_ID + " = ?" + " ORDER BY " + COLUMN_ALBUMS_TITLE;

    private static final String DELETE_ARTIST = "DELETE FROM " + TABLE_ARTISTS + " WHERE " + COLUMN_ARTISTS_ARTIST_NAME + " = ?";
    private static final String DELETE_ALBUM = "DELETE FROM " + TABLE_ALBUMS + " WHERE " + COLUMN_ALBUMS_TITLE + " = ? AND " + COLUMN_ALBUMS_ARTIST_ID + " = ?";
    private static final String DELETE_SONG = "DELETE FROM " + TABLE_SONGS + " WHERE " + COLUMN_SONGS_TITLE + " = ? AND " + COLUMN_SONGS_ALBUM_ID + " = ?";

    private static final String LIST_ARTIST_ALBUMS = "SELECT * FROM " +
            TABLE_ALBUMS + " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ARTISTS + "." +
            COLUMN_ARTISTS_ARTIST_ID + " = " + TABLE_ALBUMS + "." + COLUMN_ALBUMS_ARTIST_ID + " WHERE " +
            TABLE_ARTISTS + "." + COLUMN_ARTISTS_ARTIST_NAME + "= ?" + " ORDER BY " + COLUMN_ALBUMS_TITLE;
    private static final String LIST_ALBUM_SONGS = "SELECT * FROM " +
            TABLE_SONGS + " INNER JOIN " + TABLE_ALBUMS + " ON " + TABLE_SONGS + "." +
            COLUMN_ALBUMS_ALBUM_ID + " = " + TABLE_ALBUMS + "." + COLUMN_ALBUMS_ALBUM_ID + " WHERE " +
            TABLE_ALBUMS + "." + COLUMN_ALBUMS_TITLE + "= ?" + " ORDER BY " + COLUMN_SONGS_TITLE;

    private static final String UPDATE_ARTIST = "UPDATE " + TABLE_ARTISTS + " SET Name = ? WHERE " + COLUMN_ARTISTS_ARTIST_NAME + " = ?";
    private static final String UPDATE_ALBUM = "UPDATE " + TABLE_ALBUMS + " SET Title = ?, Location = ? WHERE " + COLUMN_ALBUMS_TITLE + " = ? AND " + COLUMN_ALBUMS_ARTIST_ID + " = ?";
    private static final String UPDATE_ALBUM_TITLE_ONLY = "UPDATE " + TABLE_ALBUMS + " SET Title = ? WHERE " + COLUMN_ALBUMS_TITLE + " = ? AND " + COLUMN_ALBUMS_ARTIST_ID + " = ?";
    private static final String UPDATE_SONG = "UPDATE " + TABLE_SONGS + " SET Title = ? WHERE " + COLUMN_SONGS_TITLE + " = ? AND " + COLUMN_SONGS_ALBUM_ID + " = ?";

    private static final String INSERT_ARTIST = "INSERT INTO " + TABLE_ARTISTS + "(" + COLUMN_ARTISTS_ARTIST_NAME + ") VALUES(?)";
    private static final String INSERT_ALBUM = "INSERT INTO " + TABLE_ALBUMS + "(" + COLUMN_ALBUMS_ARTIST_ID + ", " +
            COLUMN_ALBUMS_TITLE +  ", " + COLUMN_ALBUMS_LOCATION + ") VALUES(?, ?, ?)";
    private static final String INSERT_SONG = "INSERT INTO " + TABLE_SONGS + "(" + COLUMN_SONGS_ALBUM_ID + ", " +
            COLUMN_SONGS_TITLE + ") VALUES(?, ?)";

   private static final String GET_SPECIFIC_ARTIST_RECORD = "SELECT " + COLUMN_ARTISTS_ARTIST_ID + " FROM " + TABLE_ARTISTS +
            " WHERE " + COLUMN_ARTISTS_ARTIST_NAME + " = ?";
    private static final String GET_SPECIFIC_ALBUM_RECORD = "SELECT " + COLUMN_ALBUMS_ALBUM_ID + " FROM " + TABLE_ALBUMS +
            " WHERE " + COLUMN_ALBUMS_TITLE + " = ?";



    /* A development helper method which displays SQL statement */
    public void displayQuery()
    {
        System.out.println("Query: " +GET_SPECIFIC_ALBUM_RECORD);
    }


    private Connection connect() {
        Connection conn = null;
        try {
            //Differs from normal connection code as need to enforce foreign keys in SQLite each time a connection is made to db
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            conn = DriverManager.getConnection(CONNECTION_STRING, config.toProperties());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public int insertArtist(String artistName) {
        int generatedKey = 0;
        try (Connection conn = this.connect()) {
            PreparedStatement insertArtistPS = conn.prepareStatement(INSERT_ARTIST, Statement.RETURN_GENERATED_KEYS);
            insertArtistPS.setString(1, artistName);
            insertArtistPS.executeUpdate();
            ResultSet keys_rs = insertArtistPS.getGeneratedKeys();
            if (keys_rs.next()) {
                generatedKey = keys_rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return generatedKey;
    }


    public void updateArtist(String currentName, String newName) {
        try (Connection conn = this.connect()) {
            PreparedStatement updateArtistPS = conn.prepareStatement(UPDATE_ARTIST);
            updateArtistPS.setString(1, newName);
            updateArtistPS.setString(2, currentName);
            updateArtistPS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void deleteArtist(String artistName) {
        try (Connection conn = this.connect()) {
            PreparedStatement deleteArtistPS = conn.prepareStatement(DELETE_ARTIST);
            deleteArtistPS.setString(1, artistName);
            deleteArtistPS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<String> listAllArtistNames() {
        ArrayList<String> allArtists = new ArrayList<>();
        try (Connection conn = this.connect()) {
            PreparedStatement listAllArtistsPS = conn.prepareStatement(LIST_ALL_ARTISTS);
            ResultSet results = listAllArtistsPS.executeQuery();
            while (results.next()) {
                allArtists.add(results.getString(COLUMN_ARTISTS_ARTIST_NAME));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allArtists;
    }


    public Artist returnArtist(int artistID) {
        Artist returnedArtist = null;
        try (Connection conn = this.connect()) {
            PreparedStatement returnArtistPS = conn.prepareStatement(LIST_SPECIFIC_ARTIST);
            returnArtistPS.setInt(1, artistID);
            ResultSet results = returnArtistPS.executeQuery();
            returnedArtist = new Artist(results.getInt(COLUMN_ARTISTS_ARTIST_ID), results.getString(COLUMN_ARTISTS_ARTIST_NAME));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return returnedArtist;
    }


    public int artistNameToArtistID(String artistName) {
        int artistID = 0;
        try (Connection conn = this.connect()) {
            PreparedStatement artistNameToArtistIDPS = conn.prepareStatement(GET_SPECIFIC_ARTIST_RECORD);
            artistNameToArtistIDPS.setString(1, artistName);
            ResultSet results = artistNameToArtistIDPS.executeQuery();
            artistID = results.getInt(COLUMN_ARTISTS_ARTIST_ID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return artistID;
    }


    public int insertAlbum(int artist_ID, String albumTitle, String albumLocation) {
        int generatedKey = 0;
        try (Connection conn = this.connect()) {
            PreparedStatement insertAlbumPS = conn.prepareStatement(INSERT_ALBUM);
            insertAlbumPS.setInt(1, artist_ID);
            insertAlbumPS.setString(2, albumTitle);
            insertAlbumPS.setString(3, albumLocation);
            insertAlbumPS.executeUpdate();
            ResultSet keys_rs = insertAlbumPS.getGeneratedKeys();
            if (keys_rs.next()) {
                generatedKey = keys_rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return generatedKey;
    }


    public void updateAlbum(int artistID, String oldTitle, String newTitle, String newLocation) {
        try (Connection conn = this.connect()) {
            PreparedStatement updateAlbumPS = conn.prepareStatement(UPDATE_ALBUM);
            updateAlbumPS.setString(1, newTitle);
            updateAlbumPS.setString(2, newLocation);
            updateAlbumPS.setString(3, oldTitle);
            updateAlbumPS.setInt(4, artistID);
            updateAlbumPS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void updateAlbum(int artistID, String oldTitle, String newTitle) {
        try (Connection conn = this.connect()) {
            PreparedStatement updateAlbumPS = conn.prepareStatement(UPDATE_ALBUM_TITLE_ONLY);
            updateAlbumPS.setString(1, newTitle);
            updateAlbumPS.setString(2, oldTitle);
            updateAlbumPS.setInt(3, artistID);
            updateAlbumPS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void deleteAlbum(int artistID, String albumTitle) {
        try (Connection conn = this.connect()) {
            PreparedStatement deleteAlbumPS = conn.prepareStatement(DELETE_ALBUM);
            deleteAlbumPS.setString(1, albumTitle);
            deleteAlbumPS.setInt(2, artistID);
            deleteAlbumPS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public Album returnAlbum(int albumID) {
        Album returnedAlbum = null;
        try (Connection conn = this.connect()) {
            PreparedStatement returnAlbumPS = conn.prepareStatement(LIST_SPECIFIC_ALBUM);
            returnAlbumPS.setInt(1, albumID);
            ResultSet results = returnAlbumPS.executeQuery();
            returnedAlbum = new Album(results.getInt(COLUMN_ALBUMS_ARTIST_ID), results.getString(COLUMN_ALBUMS_TITLE), results.getString(COLUMN_ALBUMS_LOCATION));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return returnedAlbum;
    }

    public ArrayList<Album> artistsAlbums(String artistName) {
        ArrayList<Album> albumsOfArtist = new ArrayList<>();
        try (Connection conn = this.connect()) {
            PreparedStatement artistAlbumsPS = conn.prepareStatement(LIST_ARTIST_ALBUMS);
            artistAlbumsPS.setString(1, artistName);
            ResultSet results = artistAlbumsPS.executeQuery();
            while (results.next()) {
                albumsOfArtist.add(new Album(results.getInt(COLUMN_ALBUMS_ARTIST_ID),
                        results.getString(COLUMN_ALBUMS_TITLE),
                        results.getString(COLUMN_ALBUMS_LOCATION)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return albumsOfArtist;
    }


    public int albumNameToAlbumID(String albumName) {
        int albumID = 0;
        try (Connection conn = this.connect()) {
            PreparedStatement albumNameToAlbumIDPS = conn.prepareStatement(GET_SPECIFIC_ALBUM_RECORD);
            albumNameToAlbumIDPS.setString(1, albumName);
            ResultSet results = albumNameToAlbumIDPS.executeQuery();
            albumID = results.getInt(COLUMN_ALBUMS_ALBUM_ID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return albumID;
    }


    public int insertSong(int albumID, String songTitle) {
        int generatedKey = 0;
        try (Connection conn = this.connect()) {
            PreparedStatement insertSongPS = conn.prepareStatement(INSERT_SONG, Statement.RETURN_GENERATED_KEYS);
            insertSongPS.setInt(1, albumID);
            insertSongPS.setString(2, songTitle);
            insertSongPS.executeUpdate();
            ResultSet keys_rs = insertSongPS.getGeneratedKeys();
            if (keys_rs.next()) {
                generatedKey = keys_rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return generatedKey;
    }



    public void updateSong(int albumID, String oldTitle, String newTitle) {
        try (Connection conn = this.connect()) {
            PreparedStatement updateSongPS = conn.prepareStatement(UPDATE_SONG);
            updateSongPS.setString(1, newTitle);
            updateSongPS.setString(2, oldTitle);
            updateSongPS.setInt(3, albumID);
            updateSongPS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void deleteSong(int albumID, String songTitle) {
        try (Connection conn = this.connect()) {
            PreparedStatement deleteSongPS = conn.prepareStatement(DELETE_SONG);
            deleteSongPS.setString(1, songTitle);
            deleteSongPS.setInt(2, albumID);
            deleteSongPS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Song> albumSongs(String albumName) {
        ArrayList<Song> songsOfAlbum = new ArrayList<>();
        try (Connection conn = this.connect()) {
            PreparedStatement albumSongsPS = conn.prepareStatement(LIST_ALBUM_SONGS);
            albumSongsPS.setString(1, albumName);
            ResultSet results = albumSongsPS.executeQuery();
            while (results.next()) {
                songsOfAlbum.add(new Song(
                        results.getInt(COLUMN_SONGS_SONG_ID),
                        results.getInt(COLUMN_SONGS_ALBUM_ID),
                        results.getString(COLUMN_SONGS_TITLE)
                        ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return songsOfAlbum;
    }


}


