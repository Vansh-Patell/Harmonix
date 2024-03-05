/***************************************************
 * InfoUpdateHandler class
 * This class contains the logic to update a user's
 * information
 **************************************************/

package com.example.harmonix.LogicLayer;

import android.util.Log;
import com.example.harmonix.PersistenceLayer.*;

public class InfoUpdateHandler {

    /*************************************
     * updateAccount method
     * This method updates the user's account
     * information after verifying the user's
     * inputs.
     *************************************/
    public static boolean updateAccount(String mobileString, String emailString, String passwordString,
                                        String confirmPasswordString) {

        if (mobileString == null || emailString == null || passwordString == null || confirmPasswordString == null) {
            return false;
        }

        // Get the current user who is logged in the system
        IDatabase database = Database.getInstance();
        String userName = database.getCurrentUser();
        User currentUser = Database.getInstance().getUser(userName);
        userName = currentUser.getUsername();

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

            // update the user's information in the database
            database.updateUser(userName, currentUser.getEmail(), currentUser.getPassword());
        }
        return updated;
    }

}
