package com.example.harmonix.LogicLayerTests;

import static org.junit.Assert.*;

import com.example.harmonix.LogicLayer.AccountHandler;
import com.example.harmonix.LogicLayer.InfoUpdateHandler;
import com.example.harmonix.PersistenceLayer.Database;
import com.example.harmonix.PersistenceLayer.IDatabase;
import org.junit.Before;
import org.junit.Test;


public class LogicIntegrationTests {

    // Get the account handler class and also an instance of HSQLDB
    private IDatabase database;

    @Before
    public void setUp() {
        database = Database.getInstance();
    }

    //Test AccountHandler
    @Test
    public void testCreateAccount() {

        // Create a new account - should return true
        assertTrue(AccountHandler.createAccount("myUsername", "myEmail@gmail.com", "abcd1234", "abcd1234"));

        // Create a new account - should return false
        assertFalse(AccountHandler.createAccount("", "", "", ""));

        // Create a new account - should return false
        assertFalse(AccountHandler.createAccount("myUsername2", "myEmail2@gmail.com", "abcd", "abcd1234"));

    }

    //Test InfoUpdateHandler
    @Test
    public void testInformationUpdate(){

        //create an account
        assertTrue(AccountHandler.createAccount("myNewUsername", "myNewEmail@gmail.com", "abcd1234", "abcd1234"));
        database.setCurrentUser("myNewUsername");

        // Update the email
        assertTrue(InfoUpdateHandler.updateAccount("", "updatedEmail", "", ""));
        assertEquals("myNewEmail@gmail.com", database.getUser("myNewUsername").getEmail());

    }
}
