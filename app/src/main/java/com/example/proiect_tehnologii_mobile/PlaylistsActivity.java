package com.example.proiect_tehnologii_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class PlaylistsActivity extends AppCompatActivity
{
    // Variables
    ListView allPlaylists;
    GenericPlaylistAdapter cursorAdapter;

    // OnCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);
        setTitle("Playlists");

        // Showing all playlists from database
        allPlaylists = findViewById(R.id.viewAllPlaylists);

        MyDatabaseHelper db = new MyDatabaseHelper(PlaylistsActivity.this);
        Cursor cursor = db.readAllPlaylists();

        cursorAdapter = new GenericPlaylistAdapter(this, cursor);
        allPlaylists.setAdapter(cursorAdapter);

        //OnClick event for items in listview
        allPlaylists.setOnItemClickListener((parent, view, position, id) ->
        {
            Intent intent = new Intent(this, SongPlayListActivity.class);
            Bundle extraStuff = new Bundle();
            extraStuff.putLong("key", position);
            intent.putExtras(extraStuff);
            startActivity(intent);
        });

        // GoBack to MainActivity
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}