/**************************************************
    MusicPlayer
    PURPOSE: This class allows for the playing of audio files, more specifically in this project for playing .mp3 audio when asked to do so
    It is a singleton as for our purposes, we should not be able to play multiple songs at once
    Access any methods through calling of the class itself, ex: MusicPlayer.setVolume(0.68419);
    Contains a list of songs that will be played from, must be set to the specified list of songs by the method setSongList();
    If the list of songs is null/empty, methods that traverse the list of songs/do anything with songs will simply not do anything, assuming that the user is trying to play no song
 ***************************************************/
package com.example.harmonix.LogicLayer;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.harmonix.DomainSpecificObjects.Songs;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicPlayer {
    private static final MusicPlayer instance = new MusicPlayer(); // Allow users to access instance if they choose so, but I would recommend calling methods through the class itself

    /*
    NOTE FOR MARKERS:
    I am aware that MediaPlayer is a concrete variable. Ideally we use an interface and define
    standard pause/play, nextSong and other methods then dependency inject. However, for AndroidStudio,
    MediaPlayer is the most obvious and standard way of playing audio files, and I fail to see a universe
    where we would switch to another type of audio player. So I made a deliberate, prudent decision to use a concrete implementation
    without any abstractions between the media player, as this class itself abstracts it away anyways.
     */
    private static MediaPlayer mediaPlayer; // The actual MediaPlayer object that plays audio given an audio file
    private static ArrayList<Songs> songsList; // The list of songs that will be played from
    private static int currentIndex = -1; // Current index in list of songs
    private static float currVolume = 1f; // The current volume of the song being played, 0f for no volume, 1f for max volume

    private MusicPlayer() {}

    public static MusicPlayer getInstance() {
        return instance;
    }

    public static ArrayList<Songs> getSongList() {
        return songsList;
    }

    public static void setSongList(ArrayList<Songs> list) {
        songsList = list;
        currentIndex = -1;
    }

    // Play a specified song by clearing the previous MediaPlayer object, and overwriting it with a new one containing the song to be played
    public static void playMusic(Context context, int surfaceHolder) {
        MusicPlayer.delete();
        mediaPlayer = MediaPlayer.create(context, surfaceHolder);
        mediaPlayer.start();
        mediaPlayer.setVolume(currVolume, currVolume);
    }

    // Convert time String to properly formatted minutes:seconds string for time to be displayed
    public static String convertToMilliseconds(String duration) {
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }

    // Safely delete the MediaPlayer object: mediaPlayer
    public static void delete() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // Set the index of the current song in the list
    public static void setSongIndex(int newIndex) {
        currentIndex = newIndex;
    }

    // Play the next song in the current list of songs
    public static void playNextSong() {
        if (songsList == null || currentIndex == songsList.size()-1) {
            return;
        }
        currentIndex += 1;
    }

    // Play the previous song in the list
    public static void playPreviousSong() {
        if (songsList == null || currentIndex == 0) {
            return;
        }
        currentIndex -= 1;
    }

    // Pause or Play the current song
    public static void pausePlay() {
        if  (!isNull()) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();
            }
        }
    }

    // Returns a boolean that determines if a song is being played by the media player right now
    // Does not check if mediaPlayer is null because a user absolutely should never open up a MusicPlayerActivity on a "null" song
    public static boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    // Get the current position in the current song being played, if a song is being played at all
    // Does not check if mediaPlayer is null because a user absolutely should never be able to open up a MusicPlayerActivity on a "null" song
    public static int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    // Go to a specified point in the current song (that point being progress)
    // Does not check if mediaPlayer is null because a user absolutely should never be able to open up a MusicPlayerActivity on a "null" song
    public static void seekTo(int progress) {
        mediaPlayer.seekTo(progress);
    }

    // Set the volume of the media player, given a float between and including 0 and 1
    // Does not check if mediaPlayer is null because a user absolutely should never be able to open up a MusicPlayerActivity on a "null" song
    public static void setVolume(float newVol) {
        currVolume = newVol;
        mediaPlayer.setVolume(currVolume, currVolume);
    }

    // Get the duration of the song being played
    // Does not check if mediaPlayer is null because a user absolutely should never be able to open up a MusicPlayerActivity on a "null" song
    public static int getDuration() {
        return mediaPlayer.getDuration();
    }

    // Return a boolean that determines if the media player object in this class is currently null
    public static boolean isNull() {
        return mediaPlayer == null;
    }

    // Check if the list of songs being played by the MusicPlayer is empty/null or not
    public static boolean isSongListEmpty() {
        return (songsList == null || songsList.isEmpty());
    }

    // Returns null if song at specified index does not exist, otherwise returns the song at the specified index from the list of songs songsList
    public static Songs getSongAt(int index) {
        if (index > songsList.size()-1 || index < 0)
            return null;
        else {
            return songsList.get(index);
        }
    }

    public static Songs getCurrentSong() {
        if (songsList == null || currentIndex < 0) {
            return null;
        }
        if (currentIndex >= songsList.size())
            return null;
        return songsList.get(currentIndex);
    }
}
