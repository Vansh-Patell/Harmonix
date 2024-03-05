/***************************************************
 * DatabaseHandler class
 * This class contains the logic to find a user in
 * the Stub Database
 **************************************************/

package com.example.harmonix.LogicLayer;

import com.example.harmonix.PersistenceLayer.*;
import java.util.List;

public class DatabaseHandler {

    /****************************
     * getUserFromList method
     * Checks if a username or email
     * matches to a user in the list
     ***************************/
    public static User getUserFromList(String usernameOrEmail, List<User> users) {

        User findUser = null;

        if (usernameOrEmail != null) {
            for (User user : users) {
                if (user.getUsername().equals(usernameOrEmail) || user.getEmail().equals(usernameOrEmail)) {
                    findUser = user;
                    break;
                }
            }
        }

        return findUser;
    }
}
