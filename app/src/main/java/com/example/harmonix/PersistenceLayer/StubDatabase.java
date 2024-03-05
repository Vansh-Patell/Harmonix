/*************************************
 * StubDatabase
 * This class is a stub for the
 * database. (Used in Iteration1)
 *************************************/

package com.example.harmonix.PersistenceLayer;

import com.example.harmonix.LogicLayer.DatabaseHandler;
import java.util.List;
import java.util.ArrayList;

public class StubDatabase implements IDatabase {
    private List<User> users;
    private String currentUsername; // User that is currently loggedIn

    public StubDatabase() {
        this.users = new ArrayList<User>();
        this.currentUsername = "";
    }

    /****************************
     * addUser method
     * adds a user to the list
     ***************************/
    public void addUser(User user) {
        users.add(user);
    }

    /****************************
     * removeUser method
     * remove a user to the list
     ***************************/
    public void removeUser(User user) {
        this.users.remove(user);
    }

    /****************************
     * getUser Method
     * checks if the user already
     * exits in our list
     ***************************/
    public User getUser(String usernameOrEmail) {
        return DatabaseHandler.getUserFromList(usernameOrEmail, this.users);
    }

    /****************************
     * Getters and setters for
     * the current logged in
     * user
     ***************************/
    public String getCurrentUser() {
        return this.currentUsername;
    }

    public void setCurrentUser(String username) {
        this.currentUsername = username;
    }

    /****************************
     * updateUser method
     * updates the user's information
     * in the database when they
     * request it
     ***************************/
    public void updateUser(String username, String email, String password) {
        User userToUpdate = getUser(username);
        userToUpdate.setEmail(email);
        userToUpdate.setPassword(password);
    }

}
