package com.example.harmonix.LogicLayerTests;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.harmonix.PersistenceLayer.Database;
import com.example.harmonix.LogicLayer.*;
import com.example.harmonix.PersistenceLayer.IDatabase;
import com.example.harmonix.PersistenceLayer.StubDatabase;

public class AccountsTest {

    /******************************
     * Tests AccountHandler
     ******************************/
    @Test
    public void testCreateAccount() {

        // Create a new account with valid inputs
        assertTrue(AccountHandler.createAccount("testUser", "testEmail", "testPassword", "testPassword"));

        // Create a new account with an empty username & email - should not be created
        assertFalse(AccountHandler.createAccount("", "", "testPassword", "testPassword"));

        // Create a new account where passwords do not match - should not be created
        assertFalse(AccountHandler.createAccount("testUser2", "testEmail2", "testPassword", "testPassword2"));

    }

    /******************************
     * Tests InfoUpdateHandler
     ******************************/
    @Test
    public void testInformationUpdate() {

        IDatabase database = new StubDatabase();

        // Create a new account first - so we can update it
        AccountHandler.createAccount("testUserUpdate", "testEmailUpdate", "testPassword", "testPassword");
        database.setCurrentUser("testUserUpdate");

        // Update the password only - also test if the email is staying the same as
        // before
        assertTrue(InfoUpdateHandler.updateAccount("", "", "checkPassword", "checkPassword"));
        assertEquals("testEmailUpdate", database.getUser("testUserUpdate").getEmail());

        // Update the email only
        assertTrue(InfoUpdateHandler.updateAccount("", "updatedEmail", "", ""));

        // Update the mobile only
        assertTrue(InfoUpdateHandler.updateAccount("updatedMobile", "", "", ""));

        // Update the email and mobile - also check if the previous mobile is staying
        assertTrue(InfoUpdateHandler.updateAccount("updatedMobile2", "updatedEmail", "", ""));
        assertNotEquals("updatedMobile", database.getUser("testUserUpdate").getMobile());

        // Update email and password
        assertTrue(InfoUpdateHandler.updateAccount("", "updatedEmail2", "updatedPassword", "updatedPassword"));

        // Update mobile and password
        assertTrue(InfoUpdateHandler.updateAccount("updatedMobile3", "", "updatedPassword2", "updatedPassword2"));

        // Update all
        assertTrue(InfoUpdateHandler.updateAccount("updatedMobile4", "updatedEmail3", "updatedPassword3",
                "updatedPassword3"));

        // test the update with null values
        assertFalse(InfoUpdateHandler.updateAccount(null, null, null, null));
        assertFalse(InfoUpdateHandler.updateAccount("updatedMobile5", null, "updatedPassword4", "updatedPassword4"));

    }
}
