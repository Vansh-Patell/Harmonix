/************************************************************
 * This class is used to create a new User object. It contains
 * the username, password, mobile and email of the user.
 ************************************************************/
package com.example.harmonix.PersistenceLayer;

import com.example.harmonix.DomainSpecificObjects.Playlist;
import com.example.harmonix.DomainSpecificObjects.Songs;
import com.example.harmonix.LogicLayer.AccountHandler;

import java.util.ArrayList;

public class User {

    // Variables to store user information
    private String username;
    private String password;
    private String email;
    private String mobile;
    private final ArrayList<Songs> downloadedSongs; // list of downloaded songs

    private ArrayList<Playlist> playlists;

    public User(String username, String email, String password) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobile = ""; // no mobile number as a part of signup, the users can add it later on
        this.downloadedSongs = new ArrayList<>();
        this.playlists = new ArrayList<>();
    }

    /**********************
     * Getters
     *********************/
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getMobile() {
        return this.mobile;
    }

    public ArrayList<Songs> getDownloadedSongs() {
        return downloadedSongs;
    }

    public ArrayList<Playlist> getPlaylists() {
        return this.playlists;
    }

    public ArrayList<String> extractPlaylistNames() {
        ArrayList<String> playlistNames = new ArrayList<>();
        for (int i = 0; i < playlists.size(); i++) {
            playlistNames.add(playlists.get(i).getNameOfPlaylist());
        }
        return playlistNames;
    }

    /**********************
     * Setters
     *********************/
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        if (AccountHandler.isValidEmail(email)) {
            this.email = email;
        }
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void addSongToPlaylist(long id, Songs song) {
        playlists.get((int) id).addSong(song);
        if (song == null) {
            System.out.println("SONG IS NULL");
        }
    }

    public void addDownloadedSong(Songs song) {
        downloadedSongs.add(song);
    }

    public void addPlaylist(Playlist playlist) {
        this.playlists.add(playlist);
    }
}
