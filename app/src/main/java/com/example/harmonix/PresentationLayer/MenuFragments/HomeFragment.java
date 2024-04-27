/************************************
 * HomeFragment class
 * This class is displays the list of
 * available songs on the homepage as
 * a List
 ************************************/
package com.example.harmonix.PresentationLayer.MenuFragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.harmonix.DomainSpecificObjects.Songs;
import com.example.harmonix.LogicLayer.MusicPlayer;
import com.example.harmonix.PresentationLayer.MusicDiscovery.*;
import com.example.harmonix.R;
import java.util.ArrayList;
import com.example.harmonix.LogicLayer.SongsHandler;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // "param1" and "param2" are parameters that are passed when
    // creating an instance of the fragment using the newInstance method.
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String myParameterOne, myParameterTwo;
    static ArrayList<Songs> songList; // our Songs on the homepage as a "List"

    RecyclerView recyclerView;
    MusicList musicList;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        // when the users enters the app, create all the Song objects
        songList = SongsHandler.getAllSongs(requireContext());
    }

    /***************************
     * onCreateView method
     * This method inflates the layout for the fragment
     * and sets the view to display the list of songs
     ***************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        // set the view display the list of songs on the homepage
        if (songList.size() > 1) {
            musicList = new MusicList(getContext(), songList);
            recyclerView.setAdapter(musicList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        }
        // Set current List of songs to be all songs
        MusicPlayer.playingFromQueue = false;
        MusicPlayer.setSongList(SongsHandler.getAllSongs(getContext()));
        return view;
    }

}