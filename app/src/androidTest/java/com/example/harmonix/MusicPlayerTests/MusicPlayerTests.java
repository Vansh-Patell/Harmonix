package com.example.harmonix.MusicPlayerTests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;


import com.example.harmonix.LaunchApp;
import com.example.harmonix.LogicLayer.SongsHandler;
import com.example.harmonix.LogicLayer.MusicPlayer;
import com.example.harmonix.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MusicPlayerTests {
    /****************************************
     * Get the context
     ****************************************/
    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
    }

    @Rule
    public ActivityScenarioRule<LaunchApp> activityRule = new ActivityScenarioRule<>(LaunchApp.class);

    /****************************************
     * Song pauses/unpauses correctly
     ****************************************/
    @Test
    public void testPlayPause() {
        onView(withId(R.id.start_listening_button))
                .perform(ViewActions.click());
        onView(withId(R.id.skip_for_now_button))
                .perform(ViewActions.click());
        onView(withId(R.id.recyclerView)) .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        assert(MusicPlayer.isPlaying()); // Test is music is played as soon as song is clicked

        Espresso.onView(ViewMatchers.withId(R.id.pause_play))
                .perform(ViewActions.click());

        assert(!MusicPlayer.isPlaying()); // Assert song is paused after pause button is pressed

        Espresso.onView(ViewMatchers.withId(R.id.pause_play))
                .perform(ViewActions.click());

        assert(MusicPlayer.isPlaying()); // Assert song is unpaused after play button is pressed
    }

    /****************************************
     * Test next and previous buttons
     ****************************************/
    @Test
    public void testNextPrevious() {
        onView(withId(R.id.start_listening_button))
                .perform(ViewActions.click());
        onView(withId(R.id.skip_for_now_button))
                .perform(ViewActions.click());
        onView(withId(R.id.recyclerView)) .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        int currSongIndex = MusicPlayer.getCurrentIndex();
        int previousSongIndex = currSongIndex;

        onView(withId(R.id.previous))
                .perform(ViewActions.click());
        previousSongIndex = currSongIndex;
        currSongIndex = MusicPlayer.getCurrentIndex();

        assert(currSongIndex == previousSongIndex); // First song in list is clicked, so current index shouldn't change

        onView(withId(R.id.next))
                .perform(ViewActions.click());

        previousSongIndex = currSongIndex;
        currSongIndex = MusicPlayer.getCurrentIndex();

        assert(currSongIndex == previousSongIndex + 1); // Current index should have been incremented by 1

        onView(withId(R.id.previous))
                .perform(ViewActions.click());

        previousSongIndex = currSongIndex;
        currSongIndex = MusicPlayer.getCurrentIndex();

        assert(currSongIndex == previousSongIndex - 1);
    }

    /****************************************
     * Test audio seekbar
     ****************************************/
    @Test
    public void testAudioBar() {
        onView(withId(R.id.start_listening_button))
                .perform(ViewActions.click());
        onView(withId(R.id.skip_for_now_button))
                .perform(ViewActions.click());
        onView(withId(R.id.recyclerView)) .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        Espresso.onView(withId(R.id.seek_bar_volume)) // Max out volume
                .perform(ViewActions.swipeRight());

        float prevVol = MusicPlayer.getVolume();
        float currVol = prevVol;

        Espresso.onView(withId(R.id.seek_bar_volume)) // Mute volume
                .perform(ViewActions.swipeLeft());
        currVol = MusicPlayer.getVolume();

        assert(currVol < prevVol); // Assert that the volume changed from larger to smaller
        prevVol = currVol;

        Espresso.onView(withId(R.id.seek_bar_volume)) // Maximize volume
                .perform(ViewActions.swipeRight());
        currVol = MusicPlayer.getVolume();

        assert(currVol > prevVol); // Assert that the volume increased
    }

    /****************************************
     * Test seekbar to check if autoplay works correctly
     ****************************************/
    @Test
    public void testSongSeekbar() {
        onView(withId(R.id.start_listening_button))
                .perform(ViewActions.click());
        onView(withId(R.id.skip_for_now_button))
                .perform(ViewActions.click());
        onView(withId(R.id.recyclerView)) .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        int currSongIndex = MusicPlayer.getCurrentIndex();
        int previousSongIndex;

        Espresso.onView(withId(R.id.seek_bar)) // Progress the song completely
                .perform(ViewActions.swipeRight());

        previousSongIndex = currSongIndex;
        currSongIndex = MusicPlayer.getCurrentIndex();

        assert(currSongIndex != previousSongIndex); // Autoplay should have started the next song in the list

    }
}
