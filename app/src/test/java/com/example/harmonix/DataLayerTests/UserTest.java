package com.example.harmonix.DataLayerTests;

import com.example.harmonix.PersistenceLayer.Database;
import com.example.harmonix.PersistenceLayer.IDatabase;
import com.example.harmonix.PersistenceLayer.User;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    private IDatabase database;

    // Setting up the database before testing
    @Before
    public void setUp() {
        database = Database.getInstance();
    }

    @Test
    public void testAddUser() {

        // Create a new user and add it to the database - should be added
        User user = new User("testUser", "testEmail@email.com", "testPassword");
        database.addUser(user);
        assertEquals(user, database.getUser("testUser"));

        // New user without a username and email - should not be added to the database
        User user2 = new User("", "", "");
        database.addUser(user2);
        assertNull(database.getUser(""));

        // New user without a username - should not be added to the database
        User user3 = new User("", "testEmail", "testPassword");
        database.addUser(user3);
        assertNull(database.getUser(""));

    }

    @Test
    public void testRemoveUser() {

        // Remove a user that exists in the database - should be removed
        User user = new User("test", "test@email.com", "testPassword");
        database.addUser(user);
        database.removeUser(user);
        assertNull(database.getUser("test"));

        // Remove a user that does not exist in the database
        User user2 = new User("testUser2", "testEmail2@email.com", "testPassword2");
        database.removeUser(user2);
        assertNull(database.getUser("testUser2"));
    }

    @Test
    public void testGetUser() {
        User user = new User("testUser3", "testEmail3@email.com", "testPassword");
        database.addUser(user);
        assertEquals(user, database.getUser("testUser3"));

        // Empty user - should not be equal
        assertNotEquals(user, database.getUser(null));
    }

    @Test
    public void testSetCurrentUser() {

        User newUser = new User("testUser4", "testEmail4@gmail.com", "testPassword4");
        database.addUser(newUser);

        // Set the current user to null first and test
        database.setCurrentUser(null);
        assertNull(database.getCurrentUser());

        // Set the current user to the new user
        database.setCurrentUser("testUser4");
        assertEquals("testUser4", database.getCurrentUser());

    }

}