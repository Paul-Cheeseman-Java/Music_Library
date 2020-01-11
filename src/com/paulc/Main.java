package com.paulc;


public class Main {

    public static void main(String[] args) {
	// write your code here

        DataSource ds = new DataSource();
        ds.updateArtist("Bongo Billy", "Musical Pete");
        //ds.updateArtist("Musical Pete", "Bongo Billy");
        Artist test = new Artist("Musical Pete");
        test.listAllAlbums();

        //ds.testQuery();
        //ds.deleteArtist("Pan Pipes Percy");
        //System.out.println("Amount of Albums: " + ds.amountOfAlbumArtistHas("Pan Pipes Percy"));

        //Artist test = new Artist("Musical Malcolm");
        //Artist test = new Artist("Pan Pipes Percy");
        //test.listAllAlbums();
        //Artist.listAllArtists();

        //Album test = new Album("Music Man - Album 2");
        //Album test = new Album("Pan Pipes Percy - Album 1");
        //test.amountOfSongs();
        //test.listAllSongs();


    }
}
