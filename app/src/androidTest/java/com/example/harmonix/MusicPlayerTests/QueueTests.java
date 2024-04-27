package com.example.harmonix.MusicPlayerTests;

import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.harmonix.LaunchApp;
import com.example.harmonix.LogicLayer.MusicPlayer;
import com.example.harmonix.PersistenceLayer.Database;
import com.example.harmonix.PersistenceLayer.IDatabase;
import com.example.harmonix.PersistenceLayer.RealDatabase;
import com.example.harmonix.PersistenceLayer.User;
import com.example.harmonix.PresentationLayer.UserProfile.SignUpPage;
import com.example.harmonix.R;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class QueueTests {
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
     * Test adding songs to queue
     ****************************************/
    @Test
    public void testAddToQueue() {
        onView(withId(R.id.start_listening_button))
                .perform(click());
        onView(withId(R.id.skip_for_now_button))
                .perform(click());
        onView(withId(R.id.recyclerView)) .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.menu_button))
                .perform(click());
        onView(withText("Add to Queue")).perform(click());
        onView(withId(R.id.menu_button))
                .perform(click());
        onView(withText("Add to Queue")).perform(click());
        onView(withId(R.id.menu_button))
                .perform(click());
        onView(withText("Add to Queue")).perform(click());
        assert(MusicPlayer.getCurrentQueue().size() == 3); // Check if song was added to queue 3 times
    }

    /****************************************
     * Test playing songs in queue
     ****************************************/
    @Test
    public void testPlayQueue() {
        onView(withId(R.id.start_listening_button))
                .perform(click());
        onView(withId(R.id.skip_for_now_button))
                .perform(click());
        onView(withId(R.id.recyclerView)) .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.menu_button))
                .perform(click());
        onView(withText("Add to Queue")).perform(click());
        onView(withId(R.id.exit_button))
                .perform(click());
        onView(withId(R.id.radio))
                .perform(click());

        assert(MusicPlayer.getCurrentQueue().size() == 1);

        onView(withId(R.id.recyclerViewQueue)) .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        assert(MusicPlayer.getCurrentQueue().size() == 0);

        onView(withId(R.id.exit_button))
                .perform(click());
        onView(withId(R.id.home))
                .perform(click());
        onView(withId(R.id.recyclerView)) .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.menu_button))
                .perform(click());
        onView(withText("Add to Queue")).perform(click());
        onView(withId(R.id.menu_button))
                .perform(click());
        onView(withText("Add to Queue")).perform(click());
        onView(withId(R.id.menu_button))
                .perform(click());
        onView(withText("Add to Queue")).perform(click());

        assert(MusicPlayer.getCurrentQueue().size() == 3);

        onView(withId(R.id.exit_button))
                .perform(click());
        onView(withId(R.id.radio))
                .perform(click());
        onView(withId(R.id.recyclerViewQueue)) .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        assert(MusicPlayer.getCurrentQueue().size() == 2);

        onView(withId(R.id.next))
                .perform(click());

        assert(MusicPlayer.getCurrentQueue().size() == 1);

        onView(withId(R.id.next))
                .perform(click());

        assert(MusicPlayer.getCurrentQueue().size() == 0);
    }
}
