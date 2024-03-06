/******************************
 * Homepage for the app once
 * the user is inside it after
 * signing up/in or skipping.
 *****************************/
package com.example.harmonix.PresentationLayer.MenuFragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import com.example.harmonix.R;

public class AppMainPage extends AppCompatActivity {

    com.example.harmonix.databinding.AppMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.harmonix.databinding.AppMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // User is in the app now
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            // Using switch causes error: "Constant Expression Required" for R.id.home. If else used instead.
            // This block switches the current fragment displayed based off of the button pressed on the bottom navigation menu
            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.account) {
                replaceFragment(new AccountFragment());
            } else if (item.getItemId() == R.id.library) {
                replaceFragment(new LibraryFragment());
            } else if (item.getItemId() == R.id.radio) {
                replaceFragment(new RadioFragment());
            } else if (item.getItemId() == R.id.search) {
                replaceFragment(new SearchFragment());
            }

            return true;
        });
    }

    /**********************************
     * Fragments for different pages
     * in the app i.e., Home, Library
     * Radio, Search, and account
     *********************************/
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}