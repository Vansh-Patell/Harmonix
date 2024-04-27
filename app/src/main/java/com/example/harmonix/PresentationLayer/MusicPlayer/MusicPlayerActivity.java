/************************************
 * MusicPlayerActivity class
 * This class dynamically changes the UI
 * of our music player with a MusicPlayer
 * class that handles the logic behind it
 **********************************/

package com.example.harmonix.PresentationLayer.MusicPlayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import androidx.appcompat.app.AppCompatActivity;
import com.example.harmonix.DomainSpecificObjects.Songs;
import com.example.harmonix.LogicLayer.MusicPlayer;
import com.example.harmonix.LogicLayer.SongsHandler;
import com.example.harmonix.PersistenceLayer.Database;
import com.example.harmonix.PersistenceLayer.User;
import com.example.harmonix.PersistenceLayer.UserSingleton;
import com.example.harmonix.PresentationLayer.MusicDiscovery.MusicList;
import com.example.harmonix.R;
import com.example.harmonix.PresentationLayer.Library.CustomAddToPlaylistDialogFragment;
import com.example.harmonix.PresentationLayer.MusicDiscovery.MusicList;
import com.example.harmonix.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MusicPlayerActivity extends AppCompatActivity {

    public static int currentIndex = -1; // Current index in list of songs
    TextView titleTv, currentTimeTv, totalTimeTv;
    SeekBar seekBar, volumeBar;
    ImageView pausePlay, nextButton, previousButton, exitButton, musicPlayerImage;
    Songs currentSong;
    MusicPlayer mediaPlayer = MusicPlayer.getInstance();

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

        setResourcesWithMusic();

        MediaPlayer.OnCompletionListener cListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playNextSong();
            }
        };
        if (MusicPlayer.acessMediaPlayer() != null) {
            MusicPlayer.acessMediaPlayer().setOnCompletionListener(cListener);
            MusicPlayer.setCompleteListener(cListener);
        }

        // Update seekBar based on current songs progress
        MusicPlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!MusicPlayer.isNull()) {
                    seekBar.setProgress(MusicPlayer.getCurrentPosition());
                    currentTimeTv.setText(MusicPlayer.convertToMilliseconds(MusicPlayer.getCurrentPosition() + ""));
                    if (MusicPlayer.isPlaying()) {
                        pausePlay.setImageResource(R.drawable.pause_icon);
                    } else {
                        pausePlay.setImageResource(R.drawable.play_icon);
                    }
                }
                new Handler().postDelayed(this, 100);
            }
        });

        menu_click(); // Checks to see if menu is clicked.

        // Update the current time of the song based on if the seek bar is altered by a
        // user
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!MusicPlayer.isNull() && fromUser) {
                    MusicPlayer.seekTo(progress);
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
                    float log1 = (float) (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME));

                    log1 = 0.999f - log1; // Without doing this, song can never be fully muted as log1 will almost but
                                          // never reach 0
                    if (log1 < 0)
                        log1 = 0;

                    currVolume = log1;
                    MusicPlayer.setVolume(currVolume);

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
        if (!MusicPlayer.isSongListEmpty()) {
            currentSong = MusicPlayer.getCurrentSong();

            String parseName = currentSong.getSongTitle();
            titleTv.setText(SongsHandler.parseSongInformation(parseName).toString());
            // Get the album cover image now using resID for each song
            int resId = currentSong.getResID();
            Uri uri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + resId);
            byte[] image = MusicList.getSongImage(uri.toString());

            // If an album cover image exists, update the song_image in song_item.xml
            if (image != null) {
                Glide.with(getApplicationContext()).asBitmap().load(image).into(musicPlayerImage);
            } else { // show the default cover image
                Glide.with(getApplicationContext()).load(R.drawable.album_cover_img).into(musicPlayerImage);
            }

            totalTimeTv.setText(MusicPlayer.convertToMilliseconds(currentSong.getSongDuration()));
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
        MusicPlayer.delete();
        this.finish();
    }

    // Play the current song
    private void playMusic() {
        if (!MusicPlayer.isSongListEmpty()) {
            MusicPlayer.playMusic(getApplicationContext(),
                    getResources().getIdentifier(currentSong.getSongTitle(), "raw", getPackageName()));
            seekBar.setProgress(0);
            seekBar.setMax(MusicPlayer.getDuration());
        }
    }

    // Play the next song in the list
    private void playNextSong() {
        MusicPlayer.playNextSong();
        setResourcesWithMusic();
    }

    // Play the previous song in the list
    private void playPreviousSong() {
        MusicPlayer.playPreviousSong();
        setResourcesWithMusic();
    }

    // Pause or Play the current song
    private void pausePlay() {
        MusicPlayer.pausePlay();
    }

    /************************
     * Displays the menu options
     * with download and playlist
     * in it and listens to the
     * click
     ***********************/
    private void menu_click() {
        // Checks if someones clicks on the menu option
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), menuButton);
                popupMenu.inflate(R.menu.menu_songs);
                popupMenu.show();
                String currentUsername = Database.getInstance().getCurrentUser();
                User currentUser = UserSingleton.getInstance();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.item4) {
                            currentUser.addDownloadedSong(currentSong);
                            System.out.println(currentUser.getDownloadedSongs().size());
                            if (currentUser != null && currentUser.getDownloadedSongs().contains(currentSong)) {
                                System.out.println(
                                        "Downloaded song is correct saved on user : " + currentUser.getUsername());
                            } else {
                                System.out.println("ERROR");
                            }
                            return true;
                        } else if (item.getItemId() == R.id.item1) {
                            // Add to playlist option
                            return true;
                        } else if (item.getItemId() == R.id.item2) {
                            // Add to liked artists
                            return true;
                        } else if (item.getItemId() == R.id.item3) {
                            // Add to liked songs
                            return true;
                        } else if (item.getItemId() == R.id.item5) {
                            // Add current song to queue
                            MusicPlayer.pushSong(currentSong);
                            return true;
                        } else {
                            return false;
                        }

                    }
                });
            }
        });
    }

    private void downloadSongToDevice() throws IOException {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), currentSong.getSongTitle() + ".mp3");
        // File file = new File(currentSong.getSongPath(),currentSong.getSongTitle() +
        // ".mp3");

        try (InputStream in = getResources().openRawResource(currentSong.getResID())) {
            FileOutputStream out = new FileOutputStream(file);
            byte[] buff = new byte[1024];
            int read;
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
            // Notify user of successful download
            runOnUiThread(() -> Toast
                    .makeText(MusicPlayerActivity.this, "Downloaded " + currentSong.getSongTitle(), Toast.LENGTH_SHORT)
                    .show());
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions or notify the user
            runOnUiThread(() -> Toast.makeText(MusicPlayerActivity.this,
                    "Download failed for " + currentSong.getSongTitle(), Toast.LENGTH_SHORT).show());
        }
    }

    private void showAddToPlaylistDialog() {
        CustomAddToPlaylistDialogFragment dialogFragment = new CustomAddToPlaylistDialogFragment(currentSong);
        dialogFragment.show(getSupportFragmentManager(), "custom_dialog");
    }

}