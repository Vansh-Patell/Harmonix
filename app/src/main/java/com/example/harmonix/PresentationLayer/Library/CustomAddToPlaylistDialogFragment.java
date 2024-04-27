package com.example.harmonix.PresentationLayer.Library;

// CustomAddToPlaylistDialogFragment.java
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.harmonix.DomainSpecificObjects.Songs;
import com.example.harmonix.PersistenceLayer.Database;
import com.example.harmonix.PersistenceLayer.User;
import com.example.harmonix.PersistenceLayer.UserSingleton;
import com.example.harmonix.R;

import java.util.ArrayList;

public class CustomAddToPlaylistDialogFragment extends DialogFragment {

    private ListView listView;
    private User currentUser;
    private Songs currentSong;

    public CustomAddToPlaylistDialogFragment(Songs currentSong) {
        // Required empty public constructor
        this.currentSong = currentSong;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_add_to_playlist, container, false);
        listView = view.findViewById(R.id.listView);

        ArrayList<String> playlistItems = new ArrayList<>();
        String currentUsername = Database.getInstance().getCurrentUser();
        User currentUser = UserSingleton.getInstance();
        // Populate the list view with playlist names (replace with your own data)
        playlistItems = currentUser.extractPlaylistNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, playlistItems);
        listView.setAdapter(adapter);

        // Handle item click event
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            long clickedId = id;
            //Toast.makeText(requireContext(),"You selected : " + clickedId,Toast.LENGTH_SHORT).show();
            currentUser.addSongToPlaylist(clickedId,currentSong);
            dismiss(); // Close the dialog after selection
        });

        return view;
    }
}
