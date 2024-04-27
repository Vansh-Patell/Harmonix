/************************************
 * Playlists Fragment
 * Class to display a list of downloaded
 * Songs by the user
 ***********************************/
package com.example.harmonix.PresentationLayer.Library;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harmonix.DomainSpecificObjects.Playlist;
import com.example.harmonix.DomainSpecificObjects.Songs;
import com.example.harmonix.LogicLayer.SongsHandler;
import com.example.harmonix.PersistenceLayer.Database;
import com.example.harmonix.PersistenceLayer.IDatabase;
import com.example.harmonix.PersistenceLayer.RealDatabase;
import com.example.harmonix.PersistenceLayer.User;
import com.example.harmonix.PersistenceLayer.UserSingleton;
import com.example.harmonix.PresentationLayer.MenuFragments.SearchFragment;
import com.example.harmonix.PresentationLayer.MusicDiscovery.MusicList;
import com.example.harmonix.R;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Playlists extends Fragment {

    // "param1" and "param2" are parameters that are passed when
    // creating an instance of the fragment using the newInstance method.
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String myParameterOne, myParameterTwo;

    private RecyclerView playlistsRecyclerView;

    private ListView listView;
    private Button createPlaylistButton;
    private MusicList adapter;

    private ArrayAdapter<String> adapter1;
    private ArrayList<Songs> songsInPlaylist;

    private static IDatabase database;

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
            myParameterOne = getArguments().getString(ARG_PARAM1);
            myParameterTwo = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.playlists, container, false);
        ;
        listView = view.findViewById(R.id.listView);
        createPlaylistButton = view.findViewById(R.id.createPlaylistButton);
        User currentUser = UserSingleton.getInstance();
        ArrayList<String> currentUserPlaylists = currentUser.extractPlaylistNames();
        adapter1 = new ArrayAdapter<>(requireContext(), R.layout.customlayout, currentUserPlaylists);
        listView.setAdapter(adapter1);
        createPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Enter the Playlist Name");
                final EditText input = new EditText(requireContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nameOfPlaylist = input.getText().toString();
                        adapter1.add(nameOfPlaylist);
                        Playlist newPlaylist = new Playlist(nameOfPlaylist);
                        currentUser.addPlaylist(newPlaylist);
                        adapter1.notifyDataSetChanged();

                    }
                });
                builder.show();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long playlistId = id;
                ArrayList<Playlist> all_playlists = currentUser.getPlaylists();
                Playlist playlist = all_playlists.get((int) playlistId);
                Fragment newFragment = UserPlaylistFragment.newInstance("param1", "param2", playlist);
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.playlists_fragment, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

}
