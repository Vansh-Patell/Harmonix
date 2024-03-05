/************************************************************
 * This class is used to create a new User object. It contains
 * the username, password, mobile and email of the user.
 ************************************************************/
package com.example.harmonix.PersistenceLayer;

import com.example.harmonix.LogicLayer.AccountHandler;

public class User {

    // Variables to store user information
    private String username;
    private String password;
    private String email;
    private String mobile;

    public User(String username, String email, String password) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobile = ""; // no mobile number as a part of signup, the users can add it later on
    }

    /**********************
     * Getters
     *********************/
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getMobile() {
        return this.mobile;
    }

    /**********************
     * Setters
     *********************/
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        if (AccountHandler.isValidEmail(email)) {
            this.email = email;
        }
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
