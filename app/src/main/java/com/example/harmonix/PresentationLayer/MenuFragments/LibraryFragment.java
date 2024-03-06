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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.example.harmonix.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LibraryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LibraryFragment extends Fragment {

    //"param1" and "param2" are parameters that are passed when
    //creating an instance of the fragment using the newInstance method.
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
            myParameterTwo= getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        //Get each CardView
        CardView playlistsCardView = view.findViewById(R.id.playlistsCardView);
        CardView artistsCardView = view.findViewById(R.id.artistsCardView);
        CardView songsCardView = view.findViewById(R.id.songsCardView);
        CardView downloadsCardView =view.findViewById(R.id.downloadsCardView);

        // Set OnClickListener for each CardView and navigate to that screen
        playlistsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        artistsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        songsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        downloadsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return view;
    }
}