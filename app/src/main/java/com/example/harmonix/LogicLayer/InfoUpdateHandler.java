package com.example.harmonix.LogicLayer;

import android.util.Log;

import com.example.harmonix.PersistenceLayer.*;

public class InfoUpdateHandler {

    public static boolean updateAccount(String mobileString, String emailString, String passwordString,
                                        String confirmPasswordString) {

        if (mobileString == null || emailString == null || passwordString == null || confirmPasswordString == null) {
            return false;
        }

        // Get the current user who is logged in the system
        IDatabase database = Database.getInstance();
        String userName = database.getCurrentUser();
        Log.e("TAG", "Username: " + userName);
        User currentUser = Database.getInstance().getUser(userName);
        userName = currentUser.getUsername();

        Log.e("User Information before update\n", "--" + currentUser.getEmail() + currentUser.getUsername() + currentUser.getPassword());

        boolean updated = false;

        if (passwordString.equals(confirmPasswordString)) {
            if (!mobileString.isEmpty()) {
                currentUser.setMobile(mobileString);
                updated = true;
            }

            if (!emailString.isEmpty()) {
                currentUser.setEmail(emailString);
                updated = true;
            }

            if (!passwordString.isEmpty()) {
                currentUser.setPassword(passwordString);
                updated = true;
            }

            database.updateUser(userName, currentUser.getEmail(), currentUser.getPassword());
        }

        Log.e("User Information after update\n", "--" + currentUser.getEmail() + currentUser.getUsername() + currentUser.getPassword());


        return updated;
    }

}
