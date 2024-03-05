/************************************************************
 * This class is used to create a new User object. It contains
 * the username, password, mobile and email of the user for
 * Iteration 1.
 ************************************************************/
package com.example.harmonix.PersistenceLayer;

public class User {

    private String username;
    private String password;
    private String email;
    // mobile number
    private String mobile;

    public User(String username, String email, String password) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobile = "";  //no mobile number as a part of signup, the users can add it later on
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
        if (username.length() > 0) {
            this.username = username;
        } else {
            throw new IllegalArgumentException("Invalid username");
        }
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        if (email.length() > 0) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email");
        }
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
