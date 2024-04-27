/************************************
 * UserSingleton Class
 * A singleton class to ensure that
 * there is only one user logged in
 * and we are returning their data
 ***********************************/
package com.example.harmonix.PersistenceLayer;

public class UserSingleton {
    private static User user;
    private static String user1;

    private UserSingleton() {
    }

    public static User getInstance() {
        if (user == null) {
            // Initialize user here, e.g., fetch it from the database
            user = Database.getInstance().getUser(Database.getInstance().getCurrentUser());

        }
        return user;
    }

    public static String getUser() {
        user1 = Database.getInstance().getCurrentUser();
        return user1;
    }

}
