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

public class GenericVideoPlaylistActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener
{
    // Variables
    private static final String API_KEY = "AIzaSyCZIHQkSFfFpu9PZ4HTzc4mZtfA8plAyQg";
    YouTubePlayer youtubePlayer;
    TextView txtVideoTitle;
    ImageButton btnPrev, btnNext, btnDelete, btnModify;
    Cursor cursor;
    YouTubePlayerView youTubePlayerView;
    String order, playlistName, videoCode;
    int songPos;

    // OnCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_video_playlist);
        setTitle("Video Player");

        // Grabbing the id, playlist name and order
        Bundle extraStuff = getIntent().getExtras();
        if( extraStuff != null)
        {
            songPos = extraStuff.getInt("key");
            playlistName = extraStuff.getString("playlist");
            order = extraStuff.getString("order");
        }

        // findViewById
        txtVideoTitle = findViewById(R.id.txtVideoPlaylistTitle);
        btnPrev = findViewById(R.id.btnPrevPlaylist);
        btnNext = findViewById(R.id.btnNextPlaylist);
        btnDelete = findViewById(R.id.btnDeletePlaylist);
        btnModify = findViewById(R.id.btnModifyPlaylist);
        youTubePlayerView = findViewById(R.id.vidYoutubePlaylist);

        // Reading the selected song
        MyDatabaseHelper db = new MyDatabaseHelper(GenericVideoPlaylistActivity.this);
        cursor = db.readSongsFromPlaylist(playlistName, order);
        cursor.moveToPosition(songPos);

        // Binding the data to the title and video
        txtVideoTitle.setText(cursor.getString(1));
        videoCode = cursor.getString(5);
        youTubePlayerView.initialize(API_KEY, this);

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
            youtubePlayer.loadVideo(videoCode);
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
            youtubePlayer.loadVideo(videoCode);
        });

        btnDelete.setOnClickListener(v ->
        {
            db.deleteOneEntry(cursor.getString(0));
            Intent intent = new Intent(GenericVideoPlaylistActivity.this, AllSongsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            GenericVideoPlaylistActivity.this.finish();
        });

        btnModify.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, ModifySongPlaylistActivity.class);
            Bundle extraModStuff = new Bundle();
            extraModStuff.putInt("key", cursor.getPosition());
            extraModStuff.putString("playlist", playlistName);
            extraModStuff.putString("order", order);
            intent.putExtras(extraModStuff);
            startActivity(intent);
        });
    }

    // YoutubeAPI Integration
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean b)
    {
        if (!b)
        {
            youtubePlayer = player;
            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            youtubePlayer.loadVideo(videoCode);

            // Hiding player controls
            //youtubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);

            youtubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                @Override
                public void onLoading()
                {

                }

                @Override
                public void onLoaded(String s)
                {

                }

                @Override
                public void onAdStarted() {

                }

                @Override
                public void onVideoStarted() {

                }

                @Override
                public void onVideoEnded()
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
                    youtubePlayer.loadVideo(videoCode);
                }

                @Override
                public void onError(YouTubePlayer.ErrorReason errorReason) {

                }
            });
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult)
    {
        Toast.makeText(getApplicationContext(), "onInitializationFailure()",
                Toast.LENGTH_LONG).show();
    }
}

