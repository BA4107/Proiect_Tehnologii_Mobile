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

public class GenericVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener
{
    // Variables
    private static final String API_KEY = "AIzaSyCZIHQkSFfFpu9PZ4HTzc4mZtfA8plAyQg";
    YouTubePlayer youtubePlayer;
    TextView txtVideoTitle;
    ImageButton btnPrev, btnNext, btnAddPlaylist, btnDelete, btnModify;
    Cursor cursor;
    YouTubePlayerView youTubePlayerView;
    String videoCode, order;
    int songPos;

    // OnCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_video);
        setTitle("Video Player");

        // Grabbing the id and order
        Bundle extraStuff = getIntent().getExtras();
        if( extraStuff != null)
        {
            songPos = extraStuff.getInt("key");
            order = extraStuff.getString("order");
        }

        // findViewById
        txtVideoTitle = findViewById(R.id.txtVideoTitle);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        btnAddPlaylist = findViewById(R.id.btnAddToPlaylist);
        btnDelete = findViewById(R.id.btnDelete);
        btnModify = findViewById(R.id.btnModify);
        youTubePlayerView = findViewById(R.id.vidYoutube);

        // Reading the selected song
        MyDatabaseHelper db = new MyDatabaseHelper(GenericVideoActivity.this);
        cursor = db.readAllSongs(order);
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

        btnAddPlaylist.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, AddToPlaylistActivity.class);
            Bundle extraPlayStuff = new Bundle();
            extraPlayStuff.putInt("key", cursor.getPosition());
            extraPlayStuff.putString("order", order);
            intent.putExtras(extraPlayStuff);
            startActivity(intent);
        });

        btnDelete.setOnClickListener(v ->
        {
            db.deleteOneEntry(cursor.getString(0));
            Intent intent = new Intent(GenericVideoActivity.this, AllSongsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            GenericVideoActivity.this.finish();
        });

        btnModify.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, ModifySongActivity.class);
            Bundle extraModStuff = new Bundle();
            extraModStuff.putInt("key", cursor.getPosition());
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
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason)
    {
        Toast.makeText(getApplicationContext(), "onInitializationFailure()",
                Toast.LENGTH_LONG).show();
    }
}

