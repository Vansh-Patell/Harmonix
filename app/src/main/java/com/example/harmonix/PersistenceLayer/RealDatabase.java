package com.example.harmonix.PersistenceLayer;

import android.content.Context;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RealDatabase implements IDatabase {
    public static Context context;
    private final String dbName = "UserDB";
    private String fullDBPath;

    private String currentUsername = ""; // User that is currently loggedIn

    public RealDatabase() {
        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading HSQLDB JDBC driver");
            e.printStackTrace();
            return;
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }

        if (context != null) {
            fullDBPath = context.getApplicationContext().getFilesDir().getAbsolutePath() + "/" + dbName;
        } else {
            fullDBPath = System.getProperty("user.dir") + "/" + dbName;
        }

        // fullDBPath = context.getApplicationContext().getFilesDir().getAbsolutePath()
        // + "/" + dbName;
        initializeDB();
    }

    private Connection getConnection() {
        String url = "jdbc:hsqldb:file:" + fullDBPath + ";ifexists=false";// This will create the database if it doesn't
                                                                          // exist
        String user = "SA"; // default user
        String password = ""; // default password is empty

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeDB() {
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {

            // Create profile table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS user_profiles (\n" +
                    "    username VARCHAR(255) NOT NULL,\n" +
                    "    email VARCHAR(255) NOT NULL,\n" +
                    "    password VARCHAR(255) NOT NULL,\n" +
                    "    PRIMARY KEY (username)\n" +
                    ");");

            // Insert some data
            // stmt.executeUpdate("INSERT INTO user_profiles VALUES('zhang',
            // 'zhangl@gmail.com', '123');\n" +
            // "INSERT INTO user_profiles VALUES('vansh', 'vansh@gmail.com', 'qwe');\n" +
            // "INSERT INTO user_profiles VALUES('leon', 'leon@hotmail.com', '1234');");

        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(User user) {
        try (Connection conn = getConnection()) {
            String stmtString = "INSERT INTO user_profiles VALUES(?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(stmtString);

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void removeUser(User user) {
        try (Connection conn = getConnection()) {
            String stmtString = "DELETE FROM user_profiles WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(stmtString);

            stmt.setString(1, user.getUsername());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public User getUser(String usernameOrEmail) {
        try (Connection conn = getConnection()) {
            String stmtString = "SELECT * FROM user_profiles WHERE username = ? OR email = ?";
            PreparedStatement stmt = conn.prepareStatement(stmtString);

            stmt.setString(1, usernameOrEmail);
            stmt.setString(2, usernameOrEmail);
            ResultSet query_result = stmt.executeQuery();

            query_result.next();
            User retrievedUser = new User(query_result.getString("username"),
                    query_result.getString("email"), query_result.getString("password"));

            query_result.close();
            stmt.close();

            return retrievedUser;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void updateUser(String username, String email, String password) {
        try (Connection conn = getConnection()) {
            String stmtString = "UPDATE user_profiles SET email = ?, password = ? WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(stmtString);

            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, username);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
               Log.e("No user exists with: ","username " + username);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String getCurrentUser() {
        return this.currentUsername;
    }

    @Override
    public void setCurrentUser(String username) {
        this.currentUsername = username;
    }
}
