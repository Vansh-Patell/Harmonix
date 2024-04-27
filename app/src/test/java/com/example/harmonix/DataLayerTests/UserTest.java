package com.example.harmonix.DataLayerTests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.example.harmonix.PersistenceLayer.IDatabase;
import com.example.harmonix.PersistenceLayer.StubDatabase;
import com.example.harmonix.PersistenceLayer.User;

public class UserTest {

        private IDatabase database;

        /**************************
         * Mock the behaviour of
         * Database using Mockito
         ************************/
        @Before
        public void setUp() {
                database = Mockito.mock(StubDatabase.class);
        }

        @Test
        public void testAddUser() {

                // Create a new user and add it to the database - should be added
                User user = new User("testUser", "testEmail@email.com", "testPassword");
                when(database.getUser("testUser")).thenReturn(user);
                database.addUser(user);
                assertEquals(user, database.getUser("testUser"));

                // New user without a username and email - should not be added to the database
                User user2 = new User("", "", "");
                when(database.getUser("")).thenReturn(null);
                database.addUser(user2);
                assertNull(database.getUser(""));

                // New user without a username - should not be added to the database
                User user3 = new User("", "testEmail", "testPassword");
                when(database.getUser("")).thenReturn(null);
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
                when(database.getUser("testUser2")).thenReturn(null);
                database.removeUser(user2);
                assertNull(database.getUser("testUser2"));
        }

        @Test
        public void testGetUser() {
                User user = new User("testUser3", "testEmail3@email.com", "testPassword");
                when(database.getUser("testUser3")).thenReturn(user);
                database.addUser(user);
                assertEquals(user, database.getUser("testUser3"));

                // Empty user - should not be equal
                when(database.getUser(null)).thenReturn(null);
                assertNotEquals(user, database.getUser(null));
        }

        @Test
        public void testSetCurrentUser() {
                User newUser = new User("testUser4", "testEmail4@gmail.com", "testPassword4");
                when(database.getUser("testUser4")).thenReturn(newUser);
                database.addUser(newUser);
                doNothing().when(database).setCurrentUser("testUser4");
                when(database.getCurrentUser()).thenReturn(null, "testUser4");

                // Set the current user to null first and test
                database.setCurrentUser(null);
                assertNull(database.getCurrentUser());

                // Set the current user to the new user
                database.setCurrentUser("testUser4");
                assertEquals("testUser4", database.getCurrentUser());

        }

}
