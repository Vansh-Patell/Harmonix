/****************************
 * Signup page Class
 * This class is the repsonsible to
 * handle the UI for the signup page
 * and show Toasts for errors
 ***************************/
package com.example.harmonix.PresentationLayer.UserProfile;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.harmonix.LogicLayer.AccountHandler;
import com.example.harmonix.PresentationLayer.MenuFragments.AppMainPage;
import com.example.harmonix.R;

public class SignUpPage extends AppCompatActivity {

    // Variables to store user input while creating new accounts
    private EditText username;
    private EditText password;
    private EditText email;
    private EditText confirmPassword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup); // start with the signup page

        // Get the username, password, and email from the user
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        confirmPassword = findViewById(R.id.confirm_password);

        /*************************
         * Back Button Pressed
         *************************/
        Button backButton = findViewById(R.id.back_button1);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When the button is clicked, go the previous page
                startActivity(new Intent(SignUpPage.this, AccountOptionsPage.class));
            }
        });

        /********************************
         * Create Account Button Pressed
         *******************************/
        Button createAccountButton = findViewById(R.id.create_account_button);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    /******************************************
     * signUp method
     * Shows error messages using Toasts on the
     * UI when user makes mistakes while signing up
     *****************************************/
    private void signUp() {

        // convert the user input to string
        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();
        String confirmPasswordString = confirmPassword.getText().toString();
        String emailString = email.getText().toString();

        // if user successfully created an account, show it on UI
        if (AccountHandler.createAccount(usernameString, emailString, passwordString, confirmPasswordString)
                && AccountHandler.isValidEmail(emailString)
                && !passwordString.isEmpty() && !confirmPasswordString.isEmpty()) {
            Toast.makeText(SignUpPage.this, "User account created successfully...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignUpPage.this, AppMainPage.class)); // go to the app
        }

        // check the email pattern
        if (!AccountHandler.isValidEmail(emailString)) {
            Toast.makeText(SignUpPage.this, "Invalid email format! Please enter a valid email", Toast.LENGTH_SHORT)
                    .show();
            email.setText("");
        }

        // No username entered
        if (usernameString.isEmpty()) {
            Toast.makeText(SignUpPage.this, "Please enter a username.", Toast.LENGTH_SHORT).show();
        }

        // No email entered
        if (emailString.isEmpty()) {
            Toast.makeText(SignUpPage.this, "Please enter an email.", Toast.LENGTH_SHORT).show();
        }

        // No passwords entered
        if (passwordString.isEmpty() || confirmPasswordString.isEmpty()) {
            Toast.makeText(SignUpPage.this, "Please enter passwords correctly.", Toast.LENGTH_SHORT).show();
            password.setText("");
            confirmPassword.setText("");
        }

        // Passwords don't match
        if (!passwordString.equals(confirmPasswordString)) {
            // Display an error message to the user and ask them to try again
            Toast.makeText(SignUpPage.this, "Passwords do not match. Please try again.", Toast.LENGTH_SHORT)
                    .show();

            // Clear the password and confirm password fields
            password.setText("");
            confirmPassword.setText("");
        }

    }

}