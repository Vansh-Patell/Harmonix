/******************************
 * Main Class that opens up when
 * the app is launched.
 *****************************/

package com.example.harmonix;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.harmonix.PersistenceLayer.Database;
import com.example.harmonix.PersistenceLayer.RealDatabase;
import com.example.harmonix.PresentationLayer.UserProfile.AccountOptionsPage;

public class LaunchApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page); // Start the App with the welcome page

        /******************************
         * Start Listening Button
         *****************************/
        Button startListening = findViewById(R.id.start_listening_button);
        startListening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LaunchApp.this, AccountOptionsPage.class)); // go to the User accounts Page
                RealDatabase.context = getApplicationContext();
                Database.getInstance();
            }
        });

    }




}