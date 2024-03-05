/****************************
 * Account Options Class
 * This class is the repsonsible to
 * change between signup, login and skip for now pages
 ***************************/
package com.example.harmonix.PresentationLayer.UserProfile;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import com.example.harmonix.PresentationLayer.MenuFragments.AppMainPage;
import com.example.harmonix.R;

public class AccountOptionsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_page); // start with the account page

        /*************************
         * Sign Up Button Pressed
         *************************/
        Button signUpButton = findViewById(R.id.sign_up_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When the button is clicked, go the next page
                startActivity(new Intent(AccountOptionsPage.this, SignUpPage.class));
            }
        });

        /*************************
         * Log in Button Pressed
         *************************/
        Button logInButton = findViewById(R.id.login_button);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When the button is clicked, go the next page
                startActivity(new Intent(AccountOptionsPage.this, LogInPage.class));
            }
        });

        /*************************
         * Skip for now Button Pressed
         *************************/
        Button skipForNowButton = findViewById(R.id.skip_for_now_button);

        skipForNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When the button is clicked, go the next page
                startActivity(new Intent(AccountOptionsPage.this, AppMainPage.class));
            }
        });

    }
}