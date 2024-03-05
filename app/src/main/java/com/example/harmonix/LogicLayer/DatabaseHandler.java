package com.example.harmonix.LogicLayer;
import com.example.harmonix.PersistenceLayer.*;

import java.util.List;

public class DatabaseHandler {

    /****************************
     * getUserFromList method
     * Contains the logic to find
     * a user in the list from
     * passed username or email
     ***************************/
    public static User getUserFromList(String usernameOrEmail, List<User> users){

        User findUser = null;

        if (usernameOrEmail != null) {
            for (User user :users) {
                if (user.getUsername().equals(usernameOrEmail) || user.getEmail().equals(usernameOrEmail)) {
                    findUser = user;
                    break;
                }
            }
        }

        return findUser;
    }
}
