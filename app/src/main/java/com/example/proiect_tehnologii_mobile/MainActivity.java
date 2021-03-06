package com.example.proiect_tehnologii_mobile;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity
{
    // Variables
    ImageButton allSongsActivity, playlistsActivity, addSongActivity, settingsActivity;

    // OnCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");

        //findById
        allSongsActivity = findViewById(R.id.btnAllSongsActivity);
        playlistsActivity = findViewById(R.id.btnPlaylistsActivity);
        addSongActivity = findViewById(R.id.btnAddSongActivity);
        settingsActivity = findViewById(R.id.btnSettingsActivity);

        // Button methods
        addSongActivity.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddSongActivity.class)));

        settingsActivity.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SettingsActivity.class)));

        allSongsActivity.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AllSongsActivity.class)));

        playlistsActivity.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AllPlaylistsActivity.class)));
    }
}