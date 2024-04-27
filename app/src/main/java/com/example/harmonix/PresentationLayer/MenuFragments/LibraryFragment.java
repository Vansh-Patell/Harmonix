/***********************************
 * Library Class
 * This class is the responsible to
 * handle the UI when user wants
 * to see their playlists, artists and downloads
 **********************************/
package com.example.harmonix.PresentationLayer.MenuFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.harmonix.PersistenceLayer.Database;
import com.example.harmonix.PresentationLayer.Library.DownloadsFragment;
import com.example.harmonix.PresentationLayer.Library.Playlists;
import com.example.harmonix.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LibraryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LibraryFragment extends Fragment {

    // "param1" and "param2" are parameters that are passed when
    // creating an instance of the fragment using the newInstance method.
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String myParameterOne, myParameterTwo;

    public LibraryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LibraryFragment.
     */
    public static LibraryFragment newInstance(String param1, String param2) {
        LibraryFragment fragment = new LibraryFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_library, container, false);
        View download_button_view = rootView.findViewById(R.id.downloadsCardView);
        download_button_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Switches to DownloadsFragment
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.library_fragment, new DownloadsFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        View playlist_button_view = rootView.findViewById(R.id.playlistsCardView);
        playlist_button_view.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment previousFragment = fragmentManager.findFragmentById(R.id.library_fragment);
                if (previousFragment != null) {
                    fragmentManager.beginTransaction().remove(previousFragment).commit();

                }
                String currentUsername = Database.getInstance().getCurrentUser();
                if (currentUsername.isEmpty()) {
                    Toast.makeText(getContext(), "You need to be logged in to access playlists", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.library_fragment, new Playlists());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }

            }
        });
        return rootView;
    }
}