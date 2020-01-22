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

    private static final String LIST_ALL_ARTISTS = "SELECT " + COLUMN_ARTISTS_ARTIST_NAME + " FROM " + TABLE_ARTISTS;
    private static final String LIST_ALL_ALBUMS = "SELECT " + COLUMN_ALBUMS_TITLE + " FROM " + TABLE_ALBUMS;
    private static final String LIST_ALL_SONGS = "SELECT " + COLUMN_SONGS_TITLE + " FROM " + TABLE_SONGS;
    private static final String LIST_SPECIFIC_ARTIST = "SELECT * FROM " + TABLE_ARTISTS + " WHERE " +
            COLUMN_ARTISTS_ARTIST_ID + " = ?";
    private static final String LIST_SPECIFIC_ALBUM = "SELECT * FROM " + TABLE_ALBUMS + " WHERE " +
            COLUMN_ALBUMS_ALBUM_ID + " = ?";


    private static final String DELETE_ARTIST = "DELETE FROM " + TABLE_ARTISTS + " WHERE " + COLUMN_ARTISTS_ARTIST_NAME + " = ?";
    private static final String DELETE_ALBUM = "DELETE FROM " + TABLE_ALBUMS + " WHERE " + COLUMN_ALBUMS_TITLE + " = ? AND " + COLUMN_ALBUMS_ARTIST_ID + " = ?";
    private static final String DELETE_SONG = "DELETE FROM " + TABLE_SONGS + " WHERE " + COLUMN_SONGS_TITLE + " = ? AND " + COLUMN_SONGS_ALBUM_ID + " = ?";

    private static final String LIST_ARTIST_ALBUMS = "SELECT * FROM " +
            TABLE_ALBUMS + " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ARTISTS + "." +
            COLUMN_ARTISTS_ARTIST_ID + " = " + TABLE_ALBUMS + "." + COLUMN_ALBUMS_ARTIST_ID + " WHERE " +
            TABLE_ARTISTS + "." + COLUMN_ARTISTS_ARTIST_NAME + "= ?";
    private static final String LIST_ALBUM_SONGS = "SELECT * FROM " +
            TABLE_SONGS + " INNER JOIN " + TABLE_ALBUMS + " ON " + TABLE_SONGS + "." +
            COLUMN_ALBUMS_ALBUM_ID + " = " + TABLE_ALBUMS + "." + COLUMN_ALBUMS_ALBUM_ID + " WHERE " +
            TABLE_ALBUMS + "." + COLUMN_ALBUMS_TITLE + "= ?";

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
            " WHERE " + COLUMN_ARTISTS_ARTIST_NAME + " = ?";;
    private static final String GET_SPECIFIC_ALBUM_RECORD = "SELECT " + COLUMN_ALBUMS_ALBUM_ID + " FROM " + TABLE_ALBUMS +
            " WHERE " + COLUMN_ALBUMS_TITLE + " = ?";;



    /* A development helper method which displays the executable SQL for a supplied query */
    public void displayQuery()
    {
        System.out.println("Query: " + DELETE_ALBUM);
    }

    private Connection connect() {
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

    public int insertArtist(String artistName) {
        int generatedKey = 0;
        try (Connection conn = this.connect()) {
            PreparedStatement insert_artist_ps = conn.prepareStatement(INSERT_ARTIST, Statement.RETURN_GENERATED_KEYS);
            insert_artist_ps.setString(1, artistName);
            insert_artist_ps.executeUpdate();

            ResultSet keys_rs = insert_artist_ps.getGeneratedKeys();
            if (keys_rs.next()) {
                generatedKey = keys_rs.getInt(1);;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in insertArtist: " + e);
        }
        return generatedKey;
    }


    public void updateArtist(String currentName, String newName) {
        try (Connection conn = this.connect()) {
            PreparedStatement update_artist_ps = conn.prepareStatement(UPDATE_ARTIST);
            update_artist_ps.setString(1, newName);
            update_artist_ps.setString(2, currentName);
            update_artist_ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Error in updateArtist: " + e);
        }
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

    public ArrayList<String> listAllArtistNames() {
        ArrayList<String> allArtists = new ArrayList<>();
        try (Connection conn = this.connect())
        {
            PreparedStatement list_all_artists_ps = conn.prepareStatement(LIST_ALL_ARTISTS);
            ResultSet results = list_all_artists_ps.executeQuery();
            while (results.next()) {
                allArtists.add(results.getString(COLUMN_ARTISTS_ARTIST_NAME));
                //System.out.println(results.getString("Name"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in listAllArtistNames: " + e);
        }
        return allArtists;
    }


    public Artist returnArtist(int artistID) {
        Artist returnedArtist = null;
        try (Connection conn = this.connect()) {
            PreparedStatement returnArtist_ps = conn.prepareStatement(LIST_SPECIFIC_ARTIST);
            returnArtist_ps.setInt(1, artistID);
            ResultSet results = returnArtist_ps.executeQuery();
            returnedArtist = new Artist(results.getInt(COLUMN_ARTISTS_ARTIST_ID), results.getString(COLUMN_ARTISTS_ARTIST_NAME));
        } catch (SQLException e) {
            System.out.println("SQL Error in returnArtist: " + e);
        }
        return returnedArtist;
    }

    public int artistNameToArtistID(String artistName) {
        int artistID = 0;;
        try (Connection conn = this.connect()) {
            PreparedStatement artist_Name_To_ArtistID_ps = conn.prepareStatement(GET_SPECIFIC_ARTIST_RECORD);
            artist_Name_To_ArtistID_ps.setString(1, artistName);
            ResultSet results = artist_Name_To_ArtistID_ps.executeQuery();
            artistID = results.getInt(COLUMN_ARTISTS_ARTIST_ID);
        } catch (SQLException e) {
            System.out.println("SQL Error in artistNameToArtistID: " + e);
        }
        return artistID;
    }


    public int insertAlbum(int artist_ID, String albumTitle, String albumLocation) {
        int generatedKey = 0;
        try (Connection conn = this.connect()) {
            PreparedStatement insert_album_ps = conn.prepareStatement(INSERT_ALBUM);
            insert_album_ps.setInt(1, artist_ID);
            insert_album_ps.setString(2, albumTitle);
            insert_album_ps.setString(3, albumLocation);
            insert_album_ps.executeUpdate();

            ResultSet keys_rs = insert_album_ps.getGeneratedKeys();
            if (keys_rs.next()) {
                generatedKey = keys_rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in insertAlbum: " + e);
        }
        return generatedKey;
    }


    public void updateAlbum(int artistID, String oldTitle, String newTitle, String newLocation) {
        try (Connection conn = this.connect()) {
            PreparedStatement update_artist_ps = conn.prepareStatement(UPDATE_ALBUM);
            update_artist_ps.setString(1, newTitle);
            update_artist_ps.setString(2, newLocation);
            update_artist_ps.setString(3, oldTitle);
            update_artist_ps.setInt(4, artistID);
            update_artist_ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Error in updateAlbum: " + e);
        }
    }

    //
    public void updateAlbum(int artistID, String oldTitle, String newTitle) {
        try (Connection conn = this.connect()) {
            PreparedStatement update_artist_ps = conn.prepareStatement(UPDATE_ALBUM_TITLE_ONLY);
            update_artist_ps.setString(1, newTitle);
            update_artist_ps.setString(2, oldTitle);
            update_artist_ps.setInt(3, artistID);
            update_artist_ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Error in updateAlbum: " + e);
        }
    }

    public void deleteAlbum(int artistID, String albumTitle) {
        try (Connection conn = this.connect()) {
            PreparedStatement delete_album_ps = conn.prepareStatement(DELETE_ALBUM);
            delete_album_ps.setString(1, albumTitle);
            delete_album_ps.setInt(2, artistID);
            delete_album_ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Error in deleteAlbum: " + e);
        }
    }


    public Album returnAlbum(int albumID) {
        Album returnedAlbum = null;
        try (Connection conn = this.connect()) {
            PreparedStatement returnAlbum_ps = conn.prepareStatement(LIST_SPECIFIC_ALBUM);
            returnAlbum_ps.setInt(1, albumID);
            ResultSet results = returnAlbum_ps.executeQuery();
            returnedAlbum = new Album(results.getInt(COLUMN_ALBUMS_ALBUM_ID), results.getInt(COLUMN_ALBUMS_ARTIST_ID), results.getString(COLUMN_ALBUMS_TITLE), results.getString(COLUMN_ALBUMS_LOCATION));
        } catch (SQLException e) {
            System.out.println("SQL Error in returnAlbum: " + e);
        }
        return returnedAlbum;
    }

    public ArrayList<Album> artistsAlbums(String artistName) {
        ArrayList<Album> albumsOfArtist = new ArrayList<>();
        try (Connection conn = this.connect()) {
            PreparedStatement list_artist_albums_ps = conn.prepareStatement(LIST_ARTIST_ALBUMS);
            list_artist_albums_ps.setString(1, artistName);
            ResultSet results = list_artist_albums_ps.executeQuery();
            while (results.next()) {
                albumsOfArtist.add(new Album(results.getInt(COLUMN_ALBUMS_ALBUM_ID),
                        results.getInt(COLUMN_ALBUMS_ARTIST_ID),
                        results.getString(COLUMN_ALBUMS_TITLE),
                        results.getString(COLUMN_ALBUMS_LOCATION)));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in artistsAlbums: " + e);
        }
        return albumsOfArtist;
    }


    public ArrayList<String> listAllAlbumNames() {
        ArrayList<String> allAlbumNames = new ArrayList<>();
        try (Connection conn = this.connect())
        {
            PreparedStatement list_all_albums_ps = conn.prepareStatement(LIST_ALL_ALBUMS);
            ResultSet results = list_all_albums_ps.executeQuery();
            while (results.next()) {
                allAlbumNames.add(results.getString(COLUMN_ALBUMS_TITLE));
                //System.out.println(results.getString("Name"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in listAllAlbumNames: " + e);
        }
        return allAlbumNames;
    }




    public int albumNameToAlbumID(String albumName) {
        int albumID = 0;;
        try (Connection conn = this.connect()) {
            PreparedStatement album_Name_To_AlbumID_ps = conn.prepareStatement(GET_SPECIFIC_ALBUM_RECORD);
            album_Name_To_AlbumID_ps.setString(1, albumName);
            ResultSet results = album_Name_To_AlbumID_ps.executeQuery();
            albumID = results.getInt(COLUMN_ALBUMS_ALBUM_ID);
        } catch (SQLException e) {
            System.out.println("SQL Error in albumNameToAlbumID: " + e);
        }
        return albumID;
    }


    public int insertSong(int albumID, String songTitle) {
        int generatedKey = 0;
        try (Connection conn = this.connect()) {
            PreparedStatement insert_song_ps = conn.prepareStatement(INSERT_SONG, Statement.RETURN_GENERATED_KEYS);
            insert_song_ps.setInt(1, albumID);
            insert_song_ps.setString(2, songTitle);
            insert_song_ps.executeUpdate();
            ResultSet keys_rs = insert_song_ps.getGeneratedKeys();
            if (keys_rs.next()) {
                generatedKey = keys_rs.getInt(1);;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in insertSong: " + e);
        }
        return generatedKey;
    }





    public void updateSong(int albumID, String oldTitle, String newTitle) {
        try (Connection conn = this.connect()) {
            PreparedStatement update_artist_ps = conn.prepareStatement(UPDATE_SONG);
            update_artist_ps.setString(1, newTitle);
            update_artist_ps.setString(2, oldTitle);
            update_artist_ps.setInt(3, albumID);
            update_artist_ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Error in updateSong: " + e);
        }
    }

    public void deleteSong(int albumID, String songTitle) {
        try (Connection conn = this.connect()) {
            PreparedStatement delete_song_ps = conn.prepareStatement(DELETE_SONG);
            delete_song_ps.setString(1, songTitle);
            delete_song_ps.setInt(2, albumID);
            delete_song_ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Error in deleteSong: " + e);
        }
    }



    public ArrayList<String> listAllSongTitles() {
        ArrayList<String> allSongNames = new ArrayList<>();
        try (Connection conn = this.connect())
        {
            PreparedStatement list_all_song_ps = conn.prepareStatement(LIST_ALL_SONGS);
            ResultSet results = list_all_song_ps.executeQuery();
            while (results.next()) {
                allSongNames.add(results.getString(COLUMN_SONGS_TITLE));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in listAllSongNames: " + e);
        }
        return allSongNames;
    }


    public ArrayList<Song> albumSongs(String albumName) {
        ArrayList<Song> songsOfAlbum = new ArrayList<>();
        try (Connection conn = this.connect()) {
            PreparedStatement list_all_albums_ps = conn.prepareStatement(LIST_ALBUM_SONGS);
            list_all_albums_ps.setString(1, albumName);
            ResultSet results = list_all_albums_ps.executeQuery();
            while (results.next()) {
                songsOfAlbum.add(new Song(
                        results.getInt(COLUMN_SONGS_SONG_ID),
                        results.getInt(COLUMN_SONGS_ALBUM_ID),
                        results.getString(COLUMN_SONGS_TITLE)
                        ));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in albumSongs: " + e);
        }
        return songsOfAlbum;
    }



}


