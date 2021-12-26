package com.example.proiect_tehnologii_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
    EditText playlistName;
    MyDatabaseHelper db = new MyDatabaseHelper(AddToPlaylistActivity.this);
    Cursor playlistCursor;

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

        // Showing all playlists from database
        allPlaylists = findViewById(R.id.viewAddToPlaylists);

        playlistCursor = db.readAllPlaylists();

        cursorAdapter = new GenericPlaylistAdapter(this, playlistCursor);
        allPlaylists.setAdapter(cursorAdapter);

        // Button methods
        Cursor cursor = db.readAllSongs();
        cursor.moveToPosition((int) songPos);
        int song_id = cursor.getInt(0);

        addPlaylist = findViewById(R.id.btnAddPlaylist);
        playlistName = findViewById(R.id.inpPlaylistName);

        addPlaylist.setOnClickListener(v ->
        {
            db.addPlaylists(playlistName.getText().toString(), song_id);
            refreshListview();
        });

        //OnClick event for items in listview
        allPlaylists.setOnItemClickListener((parent, view, position, id) ->
        {
            playlistCursor.moveToPosition(position);
            db.addPlaylists(playlistCursor.getString(1), song_id);
        });
    }

    private void refreshListview()
    {
        playlistCursor = db.readAllPlaylists();
        cursorAdapter.swapCursor(playlistCursor);
    }
}