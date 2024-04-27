/*****************************************
 * Radio Fragment class
 * Iteration 3...
 *****************************************/
package com.example.harmonix.PresentationLayer.MenuFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * Use the {@link QueueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QueueFragment extends Fragment {

    //"param1" and "param2" are parameters that are passed when
    //creating an instance of the fragment using the newInstance method.
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView;
    static ArrayList<Songs> queueList; // Queue of songs to play from

    MusicList musicList;

    private String myParameterOne, myParameterTwo;

    public QueueFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RadioFragment.
     */
    public static QueueFragment newInstance(String param1, String param2) {
        QueueFragment fragment = new QueueFragment();
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
        queueList = MusicPlayer.getCurrentQueue();
    }
// recyclerViewQueue
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_queue, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewQueue);
        recyclerView.setHasFixedSize(true);

        // set the view display the list of songs on the homepage
        if (queueList.size() >= 1) {
            musicList = new MusicList(getContext(), queueList);
            recyclerView.setAdapter(musicList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            MusicPlayer.setUpdateQueueList(getContext(), recyclerView, musicList);
        }

        MusicPlayer.playingFromQueue = true;
        queueList = MusicPlayer.getCurrentQueue();
        MusicPlayer.setSongList(queueList);
        return view;
    }

}