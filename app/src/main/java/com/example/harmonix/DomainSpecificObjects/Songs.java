/*************************************
 * Songs object
 * This class holds information about
 * a song, such as its Path, Title,
 * artist, and duration
 *************************************/
package com.example.harmonix.DomainSpecificObjects;

public class Songs {

    // Variables to represent song's information
    private String songPath, songTitle, songArtist, albumName, songDuration;
    private int resID; // the resource ID that gets assigned to a song when its created on the front
    // end

    public Songs(String songPath, String songTitle, String songArtist, String albumName, String songDuration,
                 int resID) {
        this.songPath = songPath;
        this.songTitle = songTitle;
        this.songArtist = songArtist;
        this.albumName = albumName;
        this.songDuration = songDuration;
        this.resID = resID;
    }

    /*************************
     * Getters
     ************************/
    public int getResID() {
        return resID;
    }

    public String getSongPath() {
        return songPath;
    }

    public String getSongDuration() {
        return songDuration;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

}
