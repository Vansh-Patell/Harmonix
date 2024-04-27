package com.example.harmonix.LogicLayerTests;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.example.harmonix.DomainSpecificObjects.Songs;
import com.example.harmonix.LogicLayer.MusicPlayer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class MusicPlayerTest {
    /******************************
     * Tests MusicPlayer
     ******************************/
    ArrayList<Songs> currentActualList;

    Songs songA = new Songs("a", "a", "a", "a", "a", 1);
    Songs songB = new Songs("b", "b", "b", "b", "b", 2);
    Songs songC = new Songs("c", "c", "c", "c", "c", 3);
    ArrayList<Songs> nullList = null; // Null list of songs
    ArrayList<Songs> listSizeOne = new ArrayList<Songs>(Arrays.asList(songA)); // Song list with a singular song
    ArrayList<Songs> listSizeN = new ArrayList<Songs>(Arrays.asList(songA, songB, songC)); // Song list with "n" songs
                                                                                           // (3 songs in this case)

    // Saves the important state of the MusicPlayer class
    private void saveSongListState() {
        currentActualList = MusicPlayer.getSongList();
    }

    // Returns the important state of the MusicPlayer class
    private void returnSongListState() {
        MusicPlayer.setSongList(currentActualList);
    }

    @Test
    public void testGetCurrentSong() {
        saveSongListState(); // Save state of MusicPlayer

        // Testing cases with null list of songs
        MusicPlayer.setSongList(nullList);

        assertNull(MusicPlayer.getCurrentSong());

        // Testing cases with list of songs of size 1
        MusicPlayer.setSongList(listSizeOne);

        MusicPlayer.setSongIndex(0); // Simulate clicking on the first song
        assertTrue(MusicPlayer.getCurrentSong().getResID() == 1); // This song should have resID of 1

        MusicPlayer.setSongIndex(1); // Check that song returned when index is not in list is null
        assertNull(MusicPlayer.getCurrentSong());

        // Testing cases with general lists of > 1 elements
        MusicPlayer.setSongList(listSizeN);

        MusicPlayer.setSongIndex(0);
        assertTrue(MusicPlayer.getCurrentSong().getResID() == 1); // This song should have resID of 1

        MusicPlayer.setSongIndex(1);
        assertTrue(MusicPlayer.getCurrentSong().getResID() == 2); // This song should have resID of 1

        MusicPlayer.setSongIndex(2);
        assertTrue(MusicPlayer.getCurrentSong().getResID() == 3); // This song should have resID of 1

        MusicPlayer.setSongIndex(5);
        assertNull(MusicPlayer.getCurrentSong()); // Song out of bounds should return null

        MusicPlayer.setSongIndex(-1);
        assertNull(MusicPlayer.getCurrentSong()); // Song out of bounds should return null

        returnSongListState(); // Return State of MusicPlayer
    }

    @Test
    public void testPlayNextAndPreviousSong() {
        saveSongListState(); // Save state of MusicPlayer

        // Testing cases with null list of songs
        MusicPlayer.setSongList(nullList);

        MusicPlayer.playNextSong(); // Next song should also be null
        assertNull(MusicPlayer.getCurrentSong());

        // Testing cases with list of songs of size 1
        MusicPlayer.setSongList(listSizeOne);
        MusicPlayer.setSongIndex(0); // Simulate clicking on the first song

        // MusicPlayer.playNextSong(); // Next song should keep at current song
        // assertTrue(MusicPlayer.getCurrentSong().getResID() == 1); // This song should
        // have resID of 1

        MusicPlayer.playPreviousSong(); // Previous song should also be the current song if size of list is 1
        assertTrue(MusicPlayer.getCurrentSong().getResID() == 1); // This song should have resID of 1

        // Testing cases with general lists of > 1 elements
        MusicPlayer.setSongList(listSizeN);
        MusicPlayer.setSongIndex(0);

        MusicPlayer.playPreviousSong();
        assertTrue(MusicPlayer.getCurrentSong().getResID() == 1); // This song should have resID of 1

        MusicPlayer.playNextSong();
        assertTrue(MusicPlayer.getCurrentSong().getResID() == 2); // This song should have resID of 2

        MusicPlayer.playPreviousSong();
        assertTrue(MusicPlayer.getCurrentSong().getResID() == 1); // This song should have resID of 1

        MusicPlayer.playNextSong();
        MusicPlayer.playNextSong();
        assertTrue(MusicPlayer.getCurrentSong().getResID() == 3); // This song should have resID of 3

        // MusicPlayer.playNextSong();
        // assertTrue(MusicPlayer.getCurrentSong().getResID() == 3); // This song should
        // have resID of 3

        returnSongListState(); // Return State of MusicPlayer
    }

    @Test
    public void testConvertToMilliseconds() {
        // Important note, songs are not expected to exceed 59:59 in length

        // Test edge cases
        String result = MusicPlayer.convertToMilliseconds("0");
        assertTrue(result.equals("00:00"));

        result = MusicPlayer.convertToMilliseconds("3599000");
        assertTrue(result.equals("59:59"));

        // Test when millseconds cant be converted to whole second, should round down
        result = MusicPlayer.convertToMilliseconds("1999");
        assertTrue(result.equals("00:01"));

        // Usual case with only seconds
        result = MusicPlayer.convertToMilliseconds("59000");
        assertTrue(result.equals("00:59"));

        // Usual case with only minutes
        result = MusicPlayer.convertToMilliseconds("1800000");
        assertTrue(result.equals("30:00"));

        // Usual case with both minutes and seconds
        result = MusicPlayer.convertToMilliseconds("1859000");
        assertTrue(result.equals("30:59"));
    }

}
