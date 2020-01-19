package com.paulc;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Application {


    //Application
    //check if database exists, if not prompt for one or create new one???
    //presents menu

    //If Artist doesn't exist, use ArtistAlbumSong, then add songs to album
    //If artist exists but Album doesn't exist, use AlbumSong, then add songs to album


    //Show a PLAY option which displays file location from album and song name (this would hand over to the playing softfware

    private static void load(){
        //A method to load in data???
    }



    public static void start(){
        //separate incase more initialization code required in future,
        Application.mainMenu();

    }


    private static void mainMenu(){
        int mainMenuControl = 1;
        try{
            while(mainMenuControl > 0) {
                System.out.println("    ------------------------------");
                System.out.println("    ------  Music Library  -------");
                System.out.println("    ------------------------------");
                System.out.println("    1 - List information from library");
                System.out.println("    2 - Search library");
                System.out.println("    3 - Add information to library");
                System.out.println("    4 - Update information in library");
                System.out.println("    5 - Remove information from library");
                System.out.println("    0 - Exit ");
                System.out.println("    ------------------------------");
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
                        Application.searchMenu();
                        mainMenuControl=0;
                        break;
                    case 3:
                        Application.addMenu();
                        mainMenuControl=0;
                        break;
                    case 4:
                        Application.updateMenu();
                        mainMenuControl=0;
                        break;
                    case 5:
                        Application.RemoveMenu();
                        mainMenuControl=0;
                        break;
                }
            }
        } catch (InputMismatchException exception) {
            System.out.println("Error: " +exception);
            Application.mainMenu();
        }
    }


    private static void listMenu(){
        /*
            Methods exist
        */
        int listMenuControl = 1;
        try{
            while(listMenuControl > 0) {
                System.out.println("    ------------------------------");
                System.out.println("    ------   List Options  -------");
                System.out.println("    ------------------------------");
                System.out.println("    1 - List all artists");
                System.out.println("    2 - List all albums by an artist");
                System.out.println("    3 - List albums and songs by an artist");
                System.out.println("    0 - Main Menu ");
                System.out.println("    ------------------------------");
                System.out.println("  ");

                System.out.println("Enter your choice ");
                Scanner sc = new Scanner(System.in);
                listMenuControl = sc.nextInt();
                switch (listMenuControl){
                    case 1:
                        System.out.println("Option 1 (List all artists) selected ");;
                        break;
                    case 2:
                        System.out.println("Option 2 (List all albums by an artist) selected ");;
                        break;
                    case 3:
                        System.out.println("Option 3 (List albums and songs by an artist) selected ");;
                        break;
                }
            }
            Application.mainMenu();
        } catch (InputMismatchException exception) {
            Application.listMenu();
        }
    }

    private static void searchMenu(){
        /*
            Simple msg, artist/album/song in db
        */
        int searchMenuControl = 1;
        try{
            while(searchMenuControl > 0) {
                System.out.println("    ------------------------------");
                System.out.println("    ------  Search Options  ------");
                System.out.println("    ------------------------------");
                System.out.println("    1 - Search for artist");
                System.out.println("    2 - Search for an album");
                System.out.println("    3 - Search for song");
                System.out.println("    0 - Main Menu ");
                System.out.println("    ------------------------------");
                System.out.println("  ");

                System.out.println("Enter your choice ");
                Scanner sc = new Scanner(System.in);
                searchMenuControl = sc.nextInt();
                switch (searchMenuControl){
                    case 1:
                        System.out.println("Option 1 (Search for artist) selected ");;
                        break;
                    case 2:
                        System.out.println("Option 2 (Search for an album) selected ");;
                        break;
                    case 3:
                        System.out.println("Option 3 (Search for song) selected ");;
                        break;
                }
            }
            Application.mainMenu();
        } catch (InputMismatchException exception) {
            Application.searchMenu();
        }
    }

    private static void addMenu(){
    /*
        Add new artist, add album, loop through songs
        Add Album, get new Artist, loop adding songs until termination char (blank???)
        Add Album, get existing Artist, loop adding songs until termination char (blank???)
        Add a song to existing album, prompt album, prompt for song
    */
        int addMenuControl = 1;
        try{
            while(addMenuControl > 0) {
                System.out.println("    ------------------------------");
                System.out.println("    --------  Add Options --------");
                System.out.println("    ------------------------------");
                System.out.println("    1 - Add an Artist");
                System.out.println("    2 - Add an album");
                System.out.println("    3 - Add a song");
                System.out.println("    0 - Main Menu ");
                System.out.println("    ------------------------------");
                System.out.println("  ");

                System.out.println("Enter your choice ");
                Scanner sc = new Scanner(System.in);
                addMenuControl = sc.nextInt();
                switch (addMenuControl){
                    case 1:
                        System.out.println("Option 1 (Add an artist) selected ");;
                        break;
                    case 2:
                        System.out.println("Option 2 (Add an album) selected ");;
                        break;
                    case 3:
                        System.out.println("Option 3 (Add a song) selected ");;
                        break;
                }
            }
            Application.mainMenu();
        } catch (InputMismatchException exception) {
            Application.addMenu();
        }
    }


    private static void updateMenu(){
        /*
            Just direct update to specific element
        */
        int updateMenuControl = 1;
        try{
            while(updateMenuControl > 0) {
                System.out.println("    ------------------------------");
                System.out.println("    ------  Update Options  ------");
                System.out.println("    ------------------------------");
                System.out.println("    1 - Update artist info");
                System.out.println("    2 - Update album info");
                System.out.println("    3 - Update song info");
                System.out.println("    0 - Main Menu ");
                System.out.println("    ------------------------------");
                System.out.println("  ");

                System.out.println("Enter your choice ");
                Scanner sc = new Scanner(System.in);
                updateMenuControl = sc.nextInt();
                switch (updateMenuControl){
                    case 1:
                        System.out.println("Option 1 (Update artist info) selected ");;
                        break;
                    case 2:
                        System.out.println("Option 2 (Update album info) selected ");;
                        break;
                    case 3:
                        System.out.println("Option 3 (Update song info) selected ");;
                        break;
                }
            }
            Application.mainMenu();
        } catch (InputMismatchException exception) {
            Application.updateMenu();
        }
    }


    private static void RemoveMenu(){
    /*
        Remove artist, remove album(s) (loop), remove song(s)
        Remove album, remove songs (loop), if last album for artist, remove artist
        Remove song, if last song remove album, if last album remove artist
            - Song.onWhichAlbum()
            - Album.byWhichArtist()
     */

        int removeMenuControl = 1;
        try{
            while(removeMenuControl > 0) {
                System.out.println("    ------------------------------");
                System.out.println("    ------  Remove Options  ------");
                System.out.println("    ------------------------------");
                System.out.println("    1 - Remove an artist");
                System.out.println("    2 - Remove and album");
                System.out.println("    3 - Remove a song");
                System.out.println("    0 - Main Menu ");
                System.out.println("    ------------------------------");
                System.out.println("  ");

                System.out.println("Enter your choice ");
                Scanner sc = new Scanner(System.in);
                removeMenuControl = sc.nextInt();
                switch (removeMenuControl){
                    case 1:
                        System.out.println("Option 1 (Remove an artist) selected ");;
                        break;
                    case 2:
                        System.out.println("Option 2 (Remove an album) selected ");;
                        break;
                    case 3:
                        System.out.println("Option 3 (Remove a song) selected ");;
                        break;
                }
            }
            Application.mainMenu();
        } catch (InputMismatchException exception) {
            Application.RemoveMenu();
        }
    }

}
