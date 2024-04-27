/*****************************************
 * SearchFragment class
 * This class allows the users to display the
 * search bar and its returned results
 *****************************************/
package com.example.harmonix.PresentationLayer.MenuFragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.harmonix.DomainSpecificObjects.Songs;
import com.example.harmonix.LogicLayer.MusicPlayer;
import com.example.harmonix.LogicLayer.SongsHandler;
import com.example.harmonix.PresentationLayer.MusicDiscovery.MusicList;
import com.example.harmonix.R;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // "param1" and "param2" are parameters that are passed when
    // creating an instance of the fragment using the newInstance method.
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String myParameterOne, myParameterTwo;

    // ---------------------------------------------------------------
    private EditText searchInput; // search bar input from user
    private RecyclerView searchRecyclerView;
    private MusicList adapter;
    private ArrayList<Songs> allSongs; // all songs in system
    private ArrayList<Songs> filteredSongs; // filtered songs

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Initialize the fields for searching
        searchInput = view.findViewById(R.id.searchInput);
        searchRecyclerView = view.findViewById(R.id.searchRecyclerView);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Populate allSongs with original list of songs initially
        allSongs = SongsHandler.getAllSongs(getContext());
        adapter = new MusicList(getContext(), new ArrayList<Songs>());
        searchRecyclerView.setAdapter(adapter);

        // Add TextChangedListener to EditText to update the results when user enters
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter the list of songs based on the input text
                filterSongs(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        MusicPlayer.playingFromQueue = false;
        return view;
    }

    /**************************************
     * filterSongs
     * This function returns a list of songs
     * that match the search text entered by
     * the user.
     *************************************/
    private void filterSongs(String searchText) {
        filteredSongs = new ArrayList<>();
        for (Songs song : allSongs) {
            // Filter logic based on your requirements, e.g., song title or artist contains
            // the searchText
            if (song.getSongTitle().toLowerCase().contains(searchText.toLowerCase())
                    || song.getSongTitle().toLowerCase().contains(searchText.toLowerCase())) {
                filteredSongs.add(song);
            }
        }
        adapter.setSongs(filteredSongs);
        MusicPlayer.setSongList(filteredSongs);
    }
}