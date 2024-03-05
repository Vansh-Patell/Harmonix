package com.example.harmonix.PresentationLayer.Library;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harmonix.DomainSpecificObjects.Songs;
import com.example.harmonix.LogicLayer.SongsHandler;
import com.example.harmonix.PersistenceLayer.User;
import com.example.harmonix.PresentationLayer.MenuFragments.SearchFragment;
import com.example.harmonix.PresentationLayer.MusicDiscovery.CurrentSongs;
import com.example.harmonix.PresentationLayer.MusicDiscovery.MusicList;
import com.example.harmonix.R;

import java.util.ArrayList;

public class Playlists extends Fragment {

    //"param1" and "param2" are parameters that are passed when
    //creating an instance of the fragment using the newInstance method.
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String myParameterOne, myParameterTwo;

    private RecyclerView playlistsRecyclerView;
    private MusicList adapter;
    private ArrayList<Songs> songsInPlaylist;

    public Playlists() {
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
            myParameterOne= getArguments().getString(ARG_PARAM1);
            myParameterTwo= getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.playlists, container, false);
        playlistsRecyclerView = view.findViewById(R.id.playlistsRecyclerView);
        playlistsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Populate allSongs with your list of songs
        songsInPlaylist = SongsHandler.getAllSongs(getContext());
        adapter = new MusicList(getContext(),new ArrayList<Songs>());
        playlistsRecyclerView.setAdapter(adapter);

        return view;
    }


}
