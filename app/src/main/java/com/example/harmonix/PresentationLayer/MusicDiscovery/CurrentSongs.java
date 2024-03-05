package com.example.harmonix.PresentationLayer.MusicDiscovery;

import com.example.harmonix.DomainSpecificObjects.Songs;

import java.util.ArrayList;

public class CurrentSongs {
    private static final CurrentSongs instance = new CurrentSongs();
    private static ArrayList<Songs> songList;

    private CurrentSongs() {}

    public static CurrentSongs getInstance() {
        return instance;
    }

    public ArrayList<Songs> getSongList() {
        return songList;
    }

    public void setList(ArrayList<Songs> list) {
        songList = list;
    }
}
