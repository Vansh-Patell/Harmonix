package com.example.harmonix.LibraryTests;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.example.harmonix.DomainSpecificObjects.Playlist;
import com.example.harmonix.LaunchApp;
import com.example.harmonix.LogicLayer.MusicPlayer;
import com.example.harmonix.PersistenceLayer.Database;
import com.example.harmonix.PersistenceLayer.IDatabase;
import com.example.harmonix.PersistenceLayer.RealDatabase;
import com.example.harmonix.PersistenceLayer.User;
import com.example.harmonix.PresentationLayer.UserProfile.SignUpPage;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

//import static android.support.test.espresso.matcher.ViewMatchers.withText;

import com.example.harmonix.LaunchApp;
import com.example.harmonix.R;

import java.util.ArrayList;

public class PlaylistsTests {
    private IDatabase database;
    /****************************************
     * Get the context
     ****************************************/
    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        RealDatabase.context = context;
        database = Database.getInstance();
    }

    @Rule
    public ActivityScenarioRule<LaunchApp> activityRule = new ActivityScenarioRule<>(LaunchApp.class);


    @Test
    public void testCreateNewPlaylist(){
       Espresso.onView(ViewMatchers.withId(R.id.start_listening_button))
               .perform(click());
       Espresso.onView(ViewMatchers.withId(R.id.sign_up_button))
               .perform(click());
       Espresso.onView(ViewMatchers.withId(R.id.username))
               .perform(typeText("Filip"));
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(typeText("Filip@gmail.com"));
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(typeText("testing123"));
        Espresso.onView(ViewMatchers.withId(R.id.confirm_password))
                .perform(typeText("testing123"));
        Espresso.onView(ViewMatchers.withId(R.id.create_account_button))
                .perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.library))
                .perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.playlistsCardView))
                .perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.createPlaylistButton))
                .perform(click());
        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(EditText.class.getName())))
                .perform(typeText("Your playlist name"));
        Espresso.onView(ViewMatchers.withText("Create"))
                .perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.createPlaylistButton))
                .perform(click());
        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(EditText.class.getName())))
                .perform(typeText("Your playlist name2"));
        Espresso.onView(ViewMatchers.withText("Create"))
                .perform(click());

        // Checks to see if user is registerd in database
        User user = database.getUser("Filip");
        ArrayList<Playlist> playlist = user.getPlaylists();

        assert(user.getPlaylists().size()==0);

    }


    /****************************************
     * Tests to see if system does not allow you to enter playlists without logging in
     ****************************************/

    @Test
    public void testLoginToViewPlaylists(){
        Espresso.onView(ViewMatchers.withId(R.id.start_listening_button))
                .perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.skip_for_now_button))
                .perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.library))
                .perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.playlistsCardView))
                .perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.search))
                .perform(click());
    }

    /****************************************
     * Test to see adding music to playlist works
     ****************************************/
    @Test
    public void testAddingMusic(){
        Espresso.onView(ViewMatchers.withId(R.id.start_listening_button))
                .perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.sign_up_button))
                .perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.username))
                .perform(typeText("Filip"));
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(typeText("Filip@gmail.com"));
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(typeText("testing123"));
        Espresso.onView(ViewMatchers.withId(R.id.confirm_password))
                .perform(typeText("testing123"));
        Espresso.onView(ViewMatchers.withId(R.id.create_account_button))
                .perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.library))
                .perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.playlistsCardView))
                .perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.createPlaylistButton))
                .perform(click());
        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(EditText.class.getName())))
                .perform(typeText("Your playlist name"));
        Espresso.onView(ViewMatchers.withText("Create"))
                .perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.home))
                .perform(click());
        Espresso.onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        Espresso.onView(withId(R.id.menu_button))
                .perform(click());
        Espresso.onView(ViewMatchers.withText("Add to Playlist")).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.exit_button))
                .perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.library))
                .perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.playlistsCardView))
                .perform(click());
        Espresso.onView(ViewMatchers.withText("Your playlist name")).perform(click());
    }


}
