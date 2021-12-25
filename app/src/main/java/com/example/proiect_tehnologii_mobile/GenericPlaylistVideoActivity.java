package com.example.proiect_tehnologii_mobile;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class GenericPlaylistVideoActivity extends YouTubeBaseActivity
{

    // Variables
    TextView txtVideoTitle;
    ImageButton btnPrev, btnPause, btnNext, btnAddPlaylist, btnDelete, btnModify;
    Cursor cursor;
    YouTubePlayerView youTubePlayerView;
    private final String API_KEY = "AIzaSyCZIHQkSFfFpu9PZ4HTzc4mZtfA8plAyQg";
    private String videoCode = "";
    private YouTubePlayer.OnInitializedListener onInitializedListener;

    // OnCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_video);
        setTitle("Video Player");

        // Grabbing the id
        Bundle extraStuff = getIntent().getExtras();
        long songPos = -1;
        String playlistName = "";
        if( extraStuff != null)
        {
            songPos = extraStuff.getLong("key");
            playlistName = extraStuff.getString("playlist");
        }

        // Reading the selected song
        MyDatabaseHelper db = new MyDatabaseHelper(GenericPlaylistVideoActivity.this);
        cursor = db.readSongsFromPlaylist(playlistName);
        cursor.moveToPosition((int) songPos);

        // findViewById
        txtVideoTitle = findViewById(R.id.txtVideoTitle);
        btnPrev = findViewById(R.id.btnPrev);
        btnPause = findViewById(R.id.btnPause);
        btnNext = findViewById(R.id.btnNext);
        btnAddPlaylist = findViewById(R.id.btnAddToPlaylist);
        btnDelete = findViewById(R.id.btnDelete);
        btnModify = findViewById(R.id.btnModify);
        youTubePlayerView = findViewById(R.id.vidYoutube);

        // Binding the data to the title and video
        txtVideoTitle.setText(cursor.getString(1));
        videoCode = cursor.getString(5);
        youTubePlayerSetup();
        //playVideo(cursor.getString(5), youTubePlayerView);

        // Button methods
        btnPrev.setOnClickListener(v ->
        {
            if (!cursor.isFirst())
            {
                cursor.moveToPrevious();
            }
            else
            {
                cursor.moveToLast();
            }
            txtVideoTitle.setText(cursor.getString(1));
            videoCode = cursor.getString(5);
            // TODO Go to prev vid
        });

        btnPause.setOnClickListener(v ->
        {
            //TODO Pause/Play video
        });

        btnNext.setOnClickListener(v ->
        {
            if (!cursor.isLast())
            {
                cursor.moveToNext();
            }
            else
            {
                cursor.moveToFirst();
            }
            txtVideoTitle.setText(cursor.getString(1));
            videoCode = cursor.getString(5);
            // TODO Go to next video
        });

        btnAddPlaylist.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, AddToPlaylistActivity.class);
            Bundle extraPlayStuff = new Bundle();
            extraPlayStuff.putLong("key", cursor.getPosition());
            intent.putExtras(extraPlayStuff);
            startActivity(intent);
        });

        btnDelete.setOnClickListener(v ->
        {
            db.deleteOneEntry(cursor.getString(0));

            // GoBack to previous activity
            Intent intent = new Intent(GenericPlaylistVideoActivity.this, AllSongsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            GenericPlaylistVideoActivity.this.finish();
        });

        btnModify.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, ModifySongActivity.class);
            Bundle extraModStuff = new Bundle();
            extraModStuff.putLong("key", cursor.getPosition());
            intent.putExtras(extraModStuff);
            startActivity(intent);
        });
    }

    // YoutubeAPI Integration
    private void youTubePlayerSetup(){

        onInitializedListener = new YouTubePlayer.OnInitializedListener()
        {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b)
            {
                if(!b)
                {
                    youTubePlayer.cueVideo(videoCode);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult)
            {
                Toast.makeText(getApplicationContext(), "onInitializationFailure()",
                        Toast.LENGTH_LONG).show();
            }
        };

        youTubePlayerView.initialize(API_KEY,onInitializedListener);
    }
}

