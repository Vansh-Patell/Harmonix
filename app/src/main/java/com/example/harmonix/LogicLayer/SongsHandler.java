/***************************************************
 * Song Handler class
 * Contains the Logic to create a Song object using
 * /res/raw folder where the .mp3 files are stored
 **************************************************/
package com.example.harmonix.LogicLayer;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;
import com.example.harmonix.DomainSpecificObjects.Songs;
import com.example.harmonix.R;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class SongsHandler {

    public static ArrayList<Songs> getAllSongs(Context context) {

        ArrayList<Songs> temp = new ArrayList<>();
        final Field[] fields = R.raw.class.getFields();

        //loop through all the.mp3 in the /res/raw folder
        for (Field field : fields) {
            try {
                int resId = field.getInt(null);
                String songName = field.getName();
                String title = songName;   //songTitle
                Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + resId);
                String songPath = uri.toString();    //songPath

                // Get other data for the song object
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(context, Uri.parse(songPath));
                String artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);  //artistName
                String album = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);   //albumName
                String duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);  //duration

                // Create a Song object and add it to the list
                Songs song = new Songs(songPath, title, artist, album, duration, resId);
                temp.add(song);

                // Print song details to Logcat
                Log.e("Song Details", "Title: " + title + ", Path: " + songPath + ", Artist: " + artist + ", Album: " + album + ", Duration: " + duration);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return temp;
    }

    /**************************************
     * parseSongInformation
     * This function updates the song names
     * since in the /res/raw folder they
     * are stored in lower case and contains
     * "_".
     *************************************/
    public static StringBuilder parseSongInformation(String parseName) {

        // Split the string by underscore
        String[] words = parseName.split("_");

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            // Capitalize the first letter
            String capitalizedWord = words[i].substring(0, 1).toUpperCase() + words[i].substring(1);
            // Append the capitalized word to the builder
            builder.append(capitalizedWord);
            // If it's not the last word, update the underscore with a space
            if (i < words.length - 1) {
                builder.append(" ");
            }
        }
        return builder;
    }

}
