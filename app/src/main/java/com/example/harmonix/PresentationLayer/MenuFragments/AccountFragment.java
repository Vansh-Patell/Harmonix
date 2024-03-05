package com.example.harmonix.PresentationLayer.MenuFragments;

import static com.example.harmonix.LogicLayer.InfoUpdateHandler.updateAccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.harmonix.PersistenceLayer.Database;
import com.example.harmonix.PersistenceLayer.IDatabase;
import com.example.harmonix.PresentationLayer.UserProfile.AccountOptionsPage;
import com.example.harmonix.PresentationLayer.UserProfile.UpdateUserInformationPage;
import com.example.harmonix.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {


    // Variables to store user input while updating account
    private EditText new_mobile;
    private EditText new_email;
    private EditText new_password;
    private EditText new_password_confirm;

    //Get the instance of the stub database
    IDatabase database = Database.getInstance();

    //"param1" and "param2" are parameters that are passed when
    //creating an instance of the fragment using the newInstance method.
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String myParameterOne, myParameterTwo;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myParameterOne = getArguments().getString(ARG_PARAM1);
            myParameterTwo = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.account_management, container, false);

        // Get the input fields
        new_mobile = rootView.findViewById(R.id.change_mobile);
        new_email = rootView.findViewById(R.id.change_email);
        new_password = rootView.findViewById(R.id.change_password);
        new_password_confirm = rootView.findViewById(R.id.change_password_confirm);

        // Apply Change Button Pressed
        Button applyAccountChangeButton = rootView.findViewById(R.id.apply_account_change_button);
        applyAccountChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!database.getCurrentUser().equals("")){
                    updateAccountInformation();
                } else {
                    Toast.makeText(getActivity(), "You are not logged in! Please login first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Logout Button Pressed
        Button logoutButton = rootView.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        return rootView;
    }

    /*************************************
     * logout method
     * Shows a message on the UI to indicate
     * whether user has successfully logged out
     *************************************/
    private void logout() {
        database.setCurrentUser("");
        startActivity(new Intent(getActivity(), AccountOptionsPage.class));
    }

    /*************************************
     * updateAccountInformation method
     * Shows a message on the UI to indicate
     * whether user has successfully updated
     * their information
     *************************************/
    private void updateAccountInformation(){

        String mobileString = new_mobile.getText().toString();
        String emailString = new_email.getText().toString();
        String passwordString = new_password.getText().toString();
        String confirmPasswordString = new_password_confirm.getText().toString();

        //use the InfoUpdate account handler to verify user inputs & update the information
        boolean success = updateAccount(mobileString,emailString,passwordString,confirmPasswordString);

        if (success) {
            Toast.makeText(getActivity(), "Account information Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Account Update Failed", Toast.LENGTH_SHORT).show();
        }

        // Clear the user input fields
        new_mobile.setText("");
        new_email.setText("");
        new_password.setText("");
        new_password_confirm.setText("");
    }
}