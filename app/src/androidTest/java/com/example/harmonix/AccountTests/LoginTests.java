package com.example.harmonix.AccountTests;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import com.example.harmonix.PersistenceLayer.Database;
import com.example.harmonix.PersistenceLayer.IDatabase;
import com.example.harmonix.PersistenceLayer.RealDatabase;
import com.example.harmonix.PersistenceLayer.User;
import com.example.harmonix.PresentationLayer.UserProfile.LogInPage;
import com.example.harmonix.PresentationLayer.UserProfile.SignUpPage;
import com.example.harmonix.R;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LoginTests {

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
    public ActivityScenarioRule<SignUpPage> activityRuleOne = new ActivityScenarioRule<>(SignUpPage.class);

    /****************************************
     * Signup the User first
     ****************************************/
    @Test
    public void testLoginSuccess() {

        // Enter account data and click create account button
        Espresso.onView(ViewMatchers.withId(R.id.username))
                .perform(ViewActions.typeText("Vansh1604"));
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText("vansh223@icloud.com"));
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("Vansh@1234"));
        Espresso.onView(ViewMatchers.withId(R.id.confirm_password))
                .perform(ViewActions.typeText("Vansh@1234"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.create_account_button))
                .perform(ViewActions.click());

        // Check if the user is added to the database
        User user = database.getUser("Vansh1604");
        assert (user != null);

        // Check if the user is added to the database & is logged in
        assert (user.getUsername().equals("Vansh1604"));
        assert (user.getUsername().equals(database.getCurrentUser()));

        // Click the account update button on the main menu
        Espresso.onView(ViewMatchers.withId(R.id.account))
                .perform(ViewActions.click());

        // click the logout button
        Espresso.onView(ViewMatchers.withId(R.id.logout_button))
                .perform(ViewActions.click());

        /****************************************
         * Now login the user with same credentials
         ****************************************/
        ActivityScenario<LogInPage> scenario = ActivityScenario.launch(LogInPage.class);

        // Enter account data and click create account button
        Espresso.onView(ViewMatchers.withId(R.id.user_or_email))
                .perform(ViewActions.typeText("Vansh1604"));
        Espresso.onView(ViewMatchers.withId(R.id.password_for_login))
                .perform(ViewActions.typeText("Vansh@1234"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.sign_in_button))
                .perform(ViewActions.click());

        // Check if the user is logged in
        User userTwo = database.getUser("Vansh1604");
        assert (userTwo != null);

        assert (userTwo.getUsername().equals("Vansh1604"));
        assert (userTwo.getUsername().equals(database.getCurrentUser()));

    }

}
