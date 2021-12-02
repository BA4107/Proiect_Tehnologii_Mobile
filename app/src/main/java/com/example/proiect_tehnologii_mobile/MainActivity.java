package com.example.proiect_tehnologii_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity
{
    // Variables
    ImageButton allSongsActivity;
    ImageButton playlistsActivity;
    ImageButton addSongActivity;
    ImageButton settingsActivity;

    // Methods
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //findById
        allSongsActivity = findViewById(R.id.btnAllSongsActivity);
        playlistsActivity = findViewById(R.id.btnPlaylistsActivity);
        addSongActivity = findViewById(R.id.btnAddSongActivity);
        settingsActivity = findViewById(R.id.btnSettingsActivity);

        // Button methods
        addSongActivity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, AddSongActivity.class));
            }
        });
    }
}