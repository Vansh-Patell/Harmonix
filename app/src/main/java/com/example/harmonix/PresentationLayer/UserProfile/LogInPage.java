package com.example.harmonix.PresentationLayer.UserProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.harmonix.PersistenceLayer.Database;
import com.example.harmonix.PersistenceLayer.IDatabase;
import com.example.harmonix.PersistenceLayer.User;
import com.example.harmonix.PresentationLayer.MenuFragments.AppMainPage;
import com.example.harmonix.R;

public class LogInPage extends AppCompatActivity {

    // Variables to store the user input
    private EditText usernameOrEmail;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login); // start with the login page

        // Get the username/email and password from the user
        usernameOrEmail = findViewById(R.id.user_or_email);
        password = findViewById(R.id.password_for_login);

        /*************************
         * Back Button Pressed
         *************************/
        Button backButton = findViewById(R.id.back_button2);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When the button is clicked, go the previous page
                startActivity(new Intent(LogInPage.this, AccountOptionsPage.class));
            }
        });

        /************************************************
         * Log In Button Pressed - with user credentials
         ***********************************************/
        Button signInButton = findViewById(R.id.sign_in_button);

        signInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                logIn();
            }
        });

    }

    /******************************************
     * logIn method
     * Shows error messages using Toasts on the
     * UI when user makes mistakes when entering
     * their login information
     *****************************************/
    private void logIn() {

        //convert the user input to string
        String usernameOrEmailString = usernameOrEmail.getText().toString();
        String passwordString = password.getText().toString();

        //Get the instance of the stub database
        IDatabase database = Database.getInstance();
        User current = database.getUser(usernameOrEmailString);

        //If its a valid user - log in
        if (current != null && current.getPassword().equals(passwordString)){
            database.setCurrentUser(usernameOrEmailString);
            Toast.makeText(LogInPage.this, "Login successfull...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LogInPage.this, AppMainPage.class));
        } else {
            //No user found - shows a Toast for no user exists
            if(current == null){
                Toast.makeText(LogInPage.this, "No user found with this credentials.", Toast.LENGTH_SHORT).show();
                usernameOrEmail.setText("");
                password.setText("");
            }
            //User has entered an incorrect password - show a Toast for incorrect password
            if(current != null && !current.getPassword().equals(passwordString)){
                Toast.makeText(LogInPage.this, "Incorrect Password.", Toast.LENGTH_SHORT).show();
                password.setText("");
            }
        }
    }
}