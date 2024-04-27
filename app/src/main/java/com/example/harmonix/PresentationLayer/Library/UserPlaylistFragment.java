package com.example.harmonix.PresentationLayer.Library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harmonix.DomainSpecificObjects.Playlist;
import com.example.harmonix.DomainSpecificObjects.Songs;
import com.example.harmonix.LogicLayer.MusicPlayer;
import com.example.harmonix.PersistenceLayer.Database;
import com.example.harmonix.PersistenceLayer.User;
import com.example.harmonix.PersistenceLayer.UserSingleton;
import com.example.harmonix.PresentationLayer.MusicDiscovery.MusicList;
import com.example.harmonix.R;

import java.util.ArrayList;

public class UserPlaylistFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1, mParam2;
    static ArrayList<Songs> songList;  //the entire songList

    RecyclerView recyclerView;
    MusicList musicList;

    Playlist playlist;
    public UserPlaylistFragment(Playlist playlist) {
        // Required empty public constructor
        this.playlist = playlist;
    }

    public static UserPlaylistFragment newInstance(String param1, String param2,Playlist playlist) {
        UserPlaylistFragment fragment = new UserPlaylistFragment(playlist);
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        songList = playlist.getSongs();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.downloaded, container, false);
//        recyclerView = view.findViewById(R.id.downloadsRecyclerView);
        View view = inflater.inflate(R.layout.user_playlist_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        //set the view display the list of songs
      //  System.out.println(Database.getInstance().getUser(Database.getInstance().getCurrentUser()).getDownloadedSongs().size());
        if (songList.size() >= 1) {
            musicList = new MusicList(getContext(), songList);
            recyclerView.setAdapter(musicList);
            MusicPlayer.setSongList(songList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        }
        return view;
    }
}

