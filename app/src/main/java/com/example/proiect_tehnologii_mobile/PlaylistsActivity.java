package com.example.proiect_tehnologii_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PlaylistsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);
        setTitle("Playlists");

        // TODO Add functionality

        // TODO Create Tables and other stuff

        // GoBack to MainActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}