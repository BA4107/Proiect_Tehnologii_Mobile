package com.example.proiect_tehnologii_mobile;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import java.util.Objects;

public class AllPlaylistsActivity extends AppCompatActivity
{
    // Variables
    ListView allPlaylists;
    GenericPlaylistAdapter cursorAdapter;
    Cursor cursor;

    // OnCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);
        setTitle("Playlists");

        // findViewById
        allPlaylists = findViewById(R.id.viewAllPlaylists);

        // Showing all playlists from database
        MyDatabaseHelper db = new MyDatabaseHelper(AllPlaylistsActivity.this);
        cursor = db.readAllPlaylists();
        cursorAdapter = new GenericPlaylistAdapter(this, cursor);
        allPlaylists.setAdapter(cursorAdapter);

        //OnClick event for items in listview
        allPlaylists.setOnItemClickListener((parent, view, position, id) ->
        {
            Intent intent = new Intent(this, AllSongsPlaylistActivity.class);
            Bundle extraStuff = new Bundle();
            extraStuff.putInt("key", position);
            intent.putExtras(extraStuff);
            startActivity(intent);
        });

        // GoBack to MainActivity
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}