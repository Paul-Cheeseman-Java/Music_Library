package com.paulc;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
       Application.mainMenu();
    }


    private static void closeStreams(){
        Artist.closeStream();
        Album.closeStream();
        Song.closeStream();
    }


    private static void mainMenu(){
        int mainMenuControl = 1;
        try{
            while(mainMenuControl > 0) {
                System.out.println("    *******************************************");
                System.out.println("    ***********   Music Library   *************");
                System.out.println("    *******************************************");
                System.out.println("    1 - List information from library");
                System.out.println("    2 - Add information to library");
                System.out.println("    3 - Update information in library");
                System.out.println("    4 - Remove information from library");
                System.out.println("    0 - Exit ");
                System.out.println("    *******************************************");
                System.out.println("  ");
                System.out.println("Enter your choice ");
                Scanner sc = new Scanner(System.in);
                mainMenuControl = sc.nextInt();

                switch (mainMenuControl){
                    case 1:
                        Application.listMenu();
                        mainMenuControl=0;
                        break;
                    case 2:
                        Application.addMenu();
                        mainMenuControl=0;
                        break;
                    case 3:
                        Application.updateMenu();
                        mainMenuControl=0;
                        break;
                    case 4:
                        Application.RemoveMenu();
                        mainMenuControl=0;
                        break;
                }
            }
        } catch (InputMismatchException exception) {
            Application.mainMenu();
        }
        Application.closeStreams();
    }


    private static void listMenu(){
        int listMenuControl = 1;
        try{
            while(listMenuControl > 0) {
                System.out.println("    -------------------------------------------");
                System.out.println("    ------------   List Options  --------------");
                System.out.println("    -------------------------------------------");
                System.out.println("    1 - List all artists");
                System.out.println("    2 - List all albums by an artist");
                System.out.println("    3 - List all albums and songs by an artist");
                System.out.println("    0 - Main Menu ");
                System.out.println("    -------------------------------------------");
                System.out.println("  ");

                System.out.println("Enter your choice ");
                Scanner sc = new Scanner(System.in);
                listMenuControl = sc.nextInt();
                switch (listMenuControl){
                    case 1:
                        Artist.listAllArtists();
                        break;
                    case 2:
                        Artist.listArtistsAlbums();
                        break;
                    case 3:
                        Artist.listArtistsAlbumsAndSongs();
                        break;
                }
            }
            Application.mainMenu();
        } catch (InputMismatchException exception) {
            Application.listMenu();
        }
    }


    private static void addMenu(){
        int addMenuControl = 1;
        try{
            while(addMenuControl > 0) {
                System.out.println("    -------------------------------------------");
                System.out.println("    -------------   Add Options  --------------");
                System.out.println("    -------------------------------------------");
                System.out.println("    1 - Add a new Artist & Album");
                System.out.println("    2 - Add a new Album for an existing Artist");
                System.out.println("    3 - Add a song to an Album");
                System.out.println("    0 - Main Menu ");
                System.out.println("    -------------------------------------------");
                System.out.println("  ");
                System.out.println("Enter your choice ");
                Scanner sc = new Scanner(System.in);
                addMenuControl = sc.nextInt();
                int albumID;
                switch (addMenuControl){
                    case 1:
                        albumID = Album.addAlbum(Artist.addArtistReturnID());
                        while(Song.addSong(albumID)){
                            Song.addSong(albumID);
                        }
                        break;

                    case 2:
                        albumID = Album.addAlbum(Artist.returnExistingArtistID());
                        while(Song.addSong(albumID)){
                            Song.addSong(albumID);
                        }
                        break;
                    case 3:
                        albumID = Album.albumNameToAlbumID(Album.promptForExistingAlbumTitle());
                        Song.addSong(albumID);
                        break;
                }
            }
            Application.mainMenu();
        } catch (InputMismatchException exception) {
            Application.addMenu();
        }
    }

    private static void updateMenu(){
        int updateMenuControl = 1;
        try{
            while(updateMenuControl > 0) {
                System.out.println("    -------------------------------------------");
                System.out.println("    -----------   Update Options   ------------");
                System.out.println("    -------------------------------------------");
                System.out.println("    1 - Update artist info");
                System.out.println("    2 - Update album info");
                System.out.println("    3 - Update song info");
                System.out.println("    0 - Main Menu ");
                System.out.println("    -------------------------------------------");
                System.out.println("  ");

                System.out.println("Enter your choice ");
                Scanner sc = new Scanner(System.in);
                updateMenuControl = sc.nextInt();
                switch (updateMenuControl){
                    case 1:
                        Artist.updateArtist();
                        break;
                    case 2:
                        Album.updateAlbum();
                        break;
                    case 3:
                        Song.updateSong();
                        break;
                }
            }
            Application.mainMenu();
        } catch (InputMismatchException exception) {
            Application.updateMenu();
        }
    }


    private static void RemoveMenu(){
        int removeMenuControl = 1;
        try{
            while(removeMenuControl > 0) {
                System.out.println("    -------------------------------------------");
                System.out.println("    -----------   Remove Options   ------------");
                System.out.println("    -------------------------------------------");
                System.out.println("    1 - Remove an artist");
                System.out.println("    2 - Remove and album");
                System.out.println("    3 - Remove a song");
                System.out.println("    0 - Main Menu ");
                System.out.println("    -------------------------------------------");
                System.out.println("  ");
                System.out.println("Enter your choice ");
                Scanner sc = new Scanner(System.in);
                removeMenuControl = sc.nextInt();
                switch (removeMenuControl){
                    case 1:
                        Artist.deleteArtist();
                        break;
                    case 2:
                        Album.deleteAlbum();
                        break;
                    case 3:
                        Song.deleteSong();
                        break;
                }
            }
            Application.mainMenu();
        } catch (InputMismatchException exception) {
            Application.RemoveMenu();
        }
    }
}
