package com.paulc;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Application {


    //Application
    //check if database exists, if not prompt for one or create new one???
    //presents menu

    //If Artist doesn't exist, use ArtistAlbumSong, then add songs to album
    //If artist exists but Album doesn't exist, use AlbumSong, then add songs to album


    //Show a PLAY option which displays file location from album and song name (this would hand over to the playing softfware

    public static void start(){
        //separate incase more initialization code required in future,
        Application.menu();

    }

    private static void menu(){

        try{
            int menuControl = 1;
            while(menuControl > 0) {
                System.out.println("    ------------------------------");
                System.out.println("    ------  Music Library  -------");
                System.out.println("    ------------------------------");

                                         /*
                                            Methods exist
                                         */
                System.out.println("  ");
                System.out.println("    1 - List all artists in library");
                System.out.println("    2 - List albums in library by an artist");
                System.out.println("    3 - List albums and songs by an artist");
                System.out.println("  ");


                                        /*
                                            Simple msg, artist/album/song in db
                                         */
                System.out.println("    4 - Search for artist");
                System.out.println("    5 - Search for an album");
                System.out.println("    6 - Search for song");
                System.out.println("  ");

                                        /*
                                            Add artist, add album, loop through songs
                                            Add Album, get existing Artist, loop adding songs until termination char (blank???)
                                            Add a song to existing album, prompt album, prompt for song
                                         */
                System.out.println("    7 - Add an Artist");
                System.out.println("    8 - Add an Album");
                System.out.println("    9 - Add a Song");

                                        /*
                                            Just direct update to specific element
                                        */
                System.out.println("    X - Update Artist info");
                System.out.println("    Y - Update Album info");
                System.out.println("    Z - Update Song info");

                                        /*
                                            Remove artist, remove album(s) (loop), remove song(s)
                                            Remove album, remove songs (loop), if last album for artist, remove artist
                                            Remove song, if last song remove album, if last album remove artist
                                             - Song.onWhichAlbum()
                                             - Album.byWhichArtist()

                                        */
                System.out.println("    X - Remove an Artist");
                System.out.println("    Y - Remove an Album");
                System.out.println("    Z - Remove a Song");

                System.out.println("  ");
                System.out.println("    P - 'Play' a Song");
                System.out.println("  ");
                System.out.println("    0 - Exit ");
                System.out.println("    ------------------------------");
                System.out.println("  ");

               System.out.println("Enter your choice ");
                Scanner sc = new Scanner(System.in);
                menuControl = sc.nextInt();

                System.out.println("Choice selected: " + menuControl);
            }
    } catch (InputMismatchException exception) {
            Application.menu();
        }
    }



    private static void load(){
        //A method to load in data???
    }

}
