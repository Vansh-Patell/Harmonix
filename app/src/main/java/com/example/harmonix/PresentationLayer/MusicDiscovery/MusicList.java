/**************************************
 * MusicList class
 * Displays a list of all the songs on
 * the homepage
 *************************************/
package com.example.harmonix.PresentationLayer.MusicDiscovery;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.harmonix.DomainSpecificObjects.Songs;
import com.example.harmonix.LogicLayer.SongsHandler;
import com.example.harmonix.R;

import java.io.IOException;
import java.util.ArrayList;

public class MusicList extends RecyclerView.Adapter<MusicList.MyViewHolder> {

    private static Context myContext;
    private ArrayList<Songs> allSongs;   //the arraylist that contains all the song objects

    public MusicList(Context myContext, ArrayList<Songs> allSongs) {
        this.myContext = myContext;
        this.allSongs = allSongs;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(myContext).inflate(R.layout.song_item, parent, false);
        return new MyViewHolder(view);
    }

    /**************************************
     * onBindViewHolder
     * This function binds all the songs
     * together on the homepage along with
     * their artist names and images.
     *************************************/
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //Update the songNames first to display them
        String parseName = allSongs.get(position).getSongTitle();

        //set the songName and artistName for the homepage
        holder.songName.setText(SongsHandler.parseSongInformation(parseName).toString());
        holder.artistName.setText(allSongs.get(position).getSongArtist());

        //Get the album cover image now using resID for each song
        int resId = allSongs.get(position).getResID();
        Uri uri = Uri.parse("android.resource://" + myContext.getPackageName() + "/" + resId);
        byte[] images = getSongImage(uri.toString());

        //If an album cover image exists, update the song_image in song_item.xml
        if (images != null) {
            Glide.with(myContext).asBitmap().load(images).into(holder.songImage);
        } else {   //show the default cover image
            Glide.with(myContext).load(R.drawable.album_cover_img).into(holder.songImage);
        }

        // Handle user clicking on a song
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If it's the homepage, handle click accordingly
                Context context = v.getContext();
                MusicPlayerActivity.currentIndex = position;
                Intent intent = new Intent(context, MusicPlayerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

        });

    }

    /*************************
     * getItemCount
     * @return number of songs
     *************************/

   // @Override
    public int getItemCount() {
        return allSongs.size(); // Return the size of the filtered list
    }

    /***************************************
     * getSongImage
     * @return array of bytes
     * This function extracts the album
     * image using the given URI path
     *************************************/
    public static byte[] getSongImage(String uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(myContext, Uri.parse(uri));
        byte[] art = retriever.getEmbeddedPicture();
        try {
            retriever.release();
        } catch (IOException e) {
            Log.e("Couldn't find", " album cover");
        }
        return art;
    }

    // New method to filter the list
    public void setSongs(ArrayList<Songs> songs) {
        this.allSongs.clear();
        // Add the new list of songs
        this.allSongs.addAll(songs);
        notifyDataSetChanged();
    }

    /**************************************
     * MyViewHolder Inner class
     * Contains the views for a song item
     * i.e., references the song_item.xml file
     *************************************/
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView songName, artistName;
        ImageView songImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.song_name);
            songImage = itemView.findViewById(R.id.song_img);
            artistName = itemView.findViewById(R.id.artist_name);
        }
    }

}
