/****************************
 * UpdateUserInformation page Class
 * This class is the responsible to
 * handle the UI when user wants
 * to update their account information
 ***************************/
package com.example.harmonix.PresentationLayer.UserProfile;

import static com.example.harmonix.LogicLayer.InfoUpdateHandler.updateAccount;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.harmonix.PersistenceLayer.Database;
import com.example.harmonix.PersistenceLayer.IDatabase;
import com.example.harmonix.PersistenceLayer.User;
import com.example.harmonix.R;

public class UpdateUserInformationPage extends AppCompatActivity {

    // Variables to store user input while creating new accounts
    private EditText new_mobile;
    private EditText new_email;
    private EditText new_password;
    private EditText new_password_confirm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_management); // start with the account management page

        // Get the new mobile, email, and password from the user fields
        new_mobile = findViewById(R.id.change_mobile);
        new_email = findViewById(R.id.change_email);
        new_password = findViewById(R.id.change_password);
        new_password_confirm = findViewById(R.id.change_password_confirm);

        /*************************************
         * Apply Change Button Pressed
         *************************************/
        Button applyAccountChangeButton = findViewById(R.id.apply_account_change_button);

        applyAccountChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAccountInformation();
            }
        });

        /*************************************
         * Logout Button Pressed
         *************************************/
        Button logoutButton = findViewById(R.id.logout_button);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

    }

    /*************************************
     * logout method
     * Shows a message on the UI to indicate
     * whether user has successfully logged out
     *************************************/
    private void logout() {
        // Get the instance of the stub database
        IDatabase database = Database.getInstance();
        String current = database.getCurrentUser();
        database.setCurrentUser("");
        startActivity(new Intent(UpdateUserInformationPage.this, AccountOptionsPage.class));
    }

    /*************************************
     * updateAccountInformation method
     * Shows a message on the UI to indicate
     * whether user has successfully updated
     * their information
     *************************************/
    private void updateAccountInformation() {

        String mobileString = new_mobile.getText().toString();
        String emailString = new_email.getText().toString();
        String passwordString = new_password.getText().toString();
        String confirmPasswordString = new_password_confirm.getText().toString();

        // use the InfoUpdate account handler to verify user inputs & update the
        // information
        boolean success = updateAccount(mobileString, emailString, passwordString, confirmPasswordString);

        if (success) {
            Toast.makeText(UpdateUserInformationPage.this, "Account information Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(UpdateUserInformationPage.this, "Account Update Failed", Toast.LENGTH_SHORT).show();
        }

        // Clear the user input fields
        new_mobile.setText("");
        new_email.setText("");
        new_password.setText("");
        new_password_confirm.setText("");
    }

}