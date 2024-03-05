/*****************************
 * Interface for Database that
 * stores User information
 ****************************/
package com.example.harmonix.PersistenceLayer;

public interface IDatabase {

    //add a new user to the database
    public void addUser(User user);

    //remove the user information
    public void removeUser(User user);

    //get the user object
    public User getUser(String username);

    public String getCurrentUser();

    public void setCurrentUser(String username);

    public void updateUser(String username, String email, String password);

}
