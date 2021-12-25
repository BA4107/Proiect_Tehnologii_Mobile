package com.example.proiect_tehnologii_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class AddToPlaylistActivity extends AppCompatActivity
{
    // Variables
    Button addPlaylist;
    ListView allPlaylists;
    GenericPlaylistAdapter cursorAdapter;

    // OnCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_playlist);
        setTitle("Choose playlist");

        // Grabbing the id
        Bundle extraPlayStuff = getIntent().getExtras();
        long songPos = -1;
        {
            songPos = extraPlayStuff.getLong("key");
        }

        // Button methods
        addPlaylist = findViewById(R.id.btnAddPlaylist);
        // TODO For some reason it hates the button method

        // Showing all playlists from database
        allPlaylists = findViewById(R.id.viewAddToPlaylists);

        MyDatabaseHelper db = new MyDatabaseHelper(AddToPlaylistActivity.this);
        Cursor cursor = db.readAllPlaylists();

        cursorAdapter = new GenericPlaylistAdapter(this, cursor);
        allPlaylists.setAdapter(cursorAdapter);

        //OnClick event for items in listview
        allPlaylists.setOnItemClickListener((parent, view, position, id) ->
        {
            // TODO Add functionality for adding songs into existing playlists
        });
    }
}