package com.example.harmonix.DomainSpecificObjects;

import com.example.harmonix.PersistenceLayer.User;

import java.util.ArrayList;

public class Playlist {

    private final String nameOfPlaylist;
    private ArrayList<Songs> songs;

    public Playlist(String nameOfPlaylist){
        this.nameOfPlaylist = nameOfPlaylist;
        this.songs = new ArrayList<>();
    }

    public String getNameOfPlaylist() {
        return nameOfPlaylist;
    }

    public ArrayList<Songs> getSongs(){
        return songs;
    }

    public void setSongs(ArrayList<Songs> songs) {
        this.songs = songs;
    }

    public void addSong(Songs song){songs.add(song); }

}
