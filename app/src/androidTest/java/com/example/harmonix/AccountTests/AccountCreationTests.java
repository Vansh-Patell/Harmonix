package com.example.harmonix.AccountTests;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import com.example.harmonix.PersistenceLayer.Database;
import com.example.harmonix.PersistenceLayer.IDatabase;
import com.example.harmonix.PersistenceLayer.RealDatabase;
import com.example.harmonix.PersistenceLayer.User;
import com.example.harmonix.PresentationLayer.UserProfile.SignUpPage;
import com.example.harmonix.R;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AccountCreationTests {

    private IDatabase database;

    /****************************************
     * Get the context and the database instance
     ****************************************/
    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        RealDatabase.context = context;
        database = Database.getInstance();
    }

    @Rule
    public ActivityScenarioRule<SignUpPage> activityRule = new ActivityScenarioRule<>(SignUpPage.class);

    /****************************************
     * User enters account data correctly - success
     ****************************************/
    @Test
    public void testSignUpSuccess() {

        // Enter account data and click create account button
        Espresso.onView(ViewMatchers.withId(R.id.username))
                .perform(ViewActions.typeText("testuser"));
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText("test@example.com"));
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("password"));
        Espresso.onView(ViewMatchers.withId(R.id.confirm_password))
                .perform(ViewActions.typeText("password"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.create_account_button))
                .perform(ViewActions.click());

        // Check if the user is added to the database
        User user = database.getUser("testuser");
        assert (user != null);

        // Check if the user is added to the database & is logged in
        assert (user.getUsername().equals("testuser"));
        assert (user.getUsername().equals(database.getCurrentUser()));

    }

    /****************************************
     * User makes a mistake while entering email - should fail
     ****************************************/
    @Test
    public void testSignUpFailViaEmail() {

        // Enter account data and click create account button
        Espresso.onView(ViewMatchers.withId(R.id.username))
                .perform(ViewActions.typeText("testuser2"));
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText("thisIsNotAnEmail"));
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("password"));
        Espresso.onView(ViewMatchers.withId(R.id.confirm_password))
                .perform(ViewActions.typeText("password"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.create_account_button))
                .perform(ViewActions.click());

        // User should not be added to the database
        User user = database.getUser("testuser2");
        assert (user == null);
    }

    /****************************************
     * User's passwords does not match - should fail
     ****************************************/
    @Test
    public void testSignUpFailViaPassword() {

        // Enter account data and click create account button
        Espresso.onView(ViewMatchers.withId(R.id.username))
                .perform(ViewActions.typeText("testuser3"));
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText("testuserEmail@gmail.com"));
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText(" "));
        Espresso.onView(ViewMatchers.withId(R.id.confirm_password))
                .perform(ViewActions.typeText("password"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.create_account_button))
                .perform(ViewActions.click());

        // User should not be added to the database
        User user = database.getUser("testuser3");
        assert (user == null);

    }

    /****************************************
     * User does not enter a username - should fail
     ****************************************/
    @Test
    public void testSignUpFailViaUsername() {

        // Enter account data and click create account button
        Espresso.onView(ViewMatchers.withId(R.id.username))
                .perform(ViewActions.typeText(""));
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText("newemail@gmail.com"));
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("password"));
        Espresso.onView(ViewMatchers.withId(R.id.confirm_password))
                .perform(ViewActions.typeText("password"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.create_account_button))
                .perform(ViewActions.click());

        // User should not be added to the database
        User user = database.getUser("");
        assert (user == null);
    }
}
