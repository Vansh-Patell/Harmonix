package com.example.harmonix.LogicLayer;

import com.example.harmonix.PersistenceLayer.Database;
import com.example.harmonix.PersistenceLayer.IDatabase;
import com.example.harmonix.PersistenceLayer.User;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountHandler {

    //pattern on how should an email address be!
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    /*************************************
     * Backend - Logic to Add an account
     *************************************/
    public static boolean createAccount(String usernameString, String emailString, String passwordString, String confirmPasswordString){
        boolean created = false;

        // Validate the user inputs
        if (passwordString.equals(confirmPasswordString) && !usernameString.isEmpty() && !emailString.isEmpty()) {
            // If they match, create a new user account and add it to the database
            User newUser = new User(usernameString,emailString,passwordString);
            // Initialize the database - Stub for Iteration 1
            IDatabase database = Database.getInstance();
            database.addUser(newUser);
            created = true;
            database.setCurrentUser(usernameString);
        }
        return created;  //success
    }

    /***************************
     * Check if the entered email
     * has a valid pattern
     **************************/
    public static boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
