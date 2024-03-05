package com.example.harmonix.PresentationLayer.MusicDiscovery;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;

import com.example.harmonix.DomainSpecificObjects.Songs;
import com.example.harmonix.LogicLayer.SongsHandler;
import com.example.harmonix.R;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicPlayerActivity extends AppCompatActivity {

    public static int currentIndex = -1; // Current index in list of songs
    TextView titleTv, currentTimeTv, totalTimeTv;
    SeekBar seekBar, volumeBar;
    ImageView pausePlay, nextButton, previousButton, exitButton, musicPlayerImage;
    ArrayList<Songs> songsList;
    Songs currentSong;
    MediaPlayer mediaPlayer;

    float currVolume = 1f; // Current volume of app, float between 0.0 to 1.0

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        titleTv = findViewById(R.id.song_title);
        musicPlayerImage = findViewById(R.id.music_player_image);
        currentTimeTv = findViewById(R.id.current_time);
        totalTimeTv = findViewById(R.id.total_time);
        seekBar = findViewById(R.id.seek_bar);
        pausePlay = findViewById(R.id.pause_play);
        nextButton = findViewById(R.id.next);
        previousButton = findViewById(R.id.previous);
        exitButton = findViewById(R.id.exit_button);
        volumeBar = findViewById(R.id.seek_bar_volume);

        titleTv.setSelected(true);

        songsList = CurrentSongs.getInstance().getSongList(); // THIS SETS THE LIST OF SONGS TO BE PLAYED FROM!!! VERY IMPORTANT

        setResourcesWithMusic();

        // Update seekBar based on current songs progress
        MusicPlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currentTimeTv.setText(convertToMilliseconds(mediaPlayer.getCurrentPosition()+""));
                    if (mediaPlayer.isPlaying()) {
                        pausePlay.setImageResource(R.drawable.pause_icon);
                    } else {
                        pausePlay.setImageResource(R.drawable.play_icon);
                    }
                }
                new Handler().postDelayed(this, 100);
            }
        });

        // Update the current time of the song based on if the seek bar is altered by a user
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Update the volume bar if altered by a user
        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    final int MAX_VOLUME = volumeBar.getMax();
                    float log1 = (float)(Math.log(MAX_VOLUME-progress)/Math.log(MAX_VOLUME));

                    log1 = 0.999f - log1; // Without doing this, song can never be fully muted as log1 will almost but never reach 0
                    if (log1 < 0)
                        log1 = 0;

                    currVolume = log1;
                    mediaPlayer.setVolume(currVolume, currVolume);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    // Initiate all variables including bars and times for the song
    void setResourcesWithMusic() {
        exitButton.setOnClickListener(v -> exitActivity());
        if (songsList != null && songsList.size() > 0) {
            currentSong = songsList.get(currentIndex);

            String parseName = currentSong.getSongTitle();
            titleTv.setText(SongsHandler.parseSongInformation(parseName).toString());

            //Get the album cover image now using resID for each song
            int resId = currentSong.getResID();
            Uri uri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + resId);
            byte[] image = MusicList.getSongImage(uri.toString());

            //If an album cover image exists, update the song_image in song_item.xml
            if (image != null) {
                Glide.with(getApplicationContext()).asBitmap().load(image).into(musicPlayerImage);
            } else {   //show the default cover image
                Glide.with(getApplicationContext()).load(R.drawable.album_cover_img).into(musicPlayerImage);
            }

            totalTimeTv.setText(convertToMilliseconds(currentSong.getSongDuration()));
            pausePlay.setOnClickListener(v -> pausePlay());
            nextButton.setOnClickListener(v -> playNextSong());
            previousButton.setOnClickListener(v -> playPreviousSong());

            if (currVolume == 1f)
                volumeBar.setProgress(volumeBar.getMax());

            playMusic();
        }
    }

    // Exit this activity and safely close the MediaPlayer object
    private void exitActivity() {
        clearMediaPlayer();
        currentIndex = -1;
        mediaPlayer = null;
        this.finish();
    }

    // Play a specified song by clearing the previous MediaPlayer object, and overwriting it with a new one containing the song to be played
    private void playMusic() {
        clearMediaPlayer();
        mediaPlayer=MediaPlayer.create(getApplicationContext(), getResources().getIdentifier(currentSong.getSongTitle(),"raw",getPackageName()));
        mediaPlayer.start();
        mediaPlayer.setVolume(currVolume, currVolume);
        seekBar.setProgress(0);
        seekBar.setMax(mediaPlayer.getDuration());
    }

    // Safely delete the MediaPlayer object: mediaPlayer
    private void clearMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // Play the next song in the list
    private void playNextSong() {
        if (currentIndex == songsList.size()-1) {
            return;
        }
        currentIndex += 1;
        setResourcesWithMusic();
    }

    // Play the previous song in the list
    private void playPreviousSong() {
        if (currentIndex == 0) {
            return;
        }
        currentIndex -= 1;
        setResourcesWithMusic();
    }

    // Pause or Play the current song
    private void pausePlay() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }

    }

    // Convert time String to properly formatted minutes:seconds string for time to be displayed
    public static String convertToMilliseconds(String duration) {
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }

}