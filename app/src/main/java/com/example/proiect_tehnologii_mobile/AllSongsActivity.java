package com.example.proiect_tehnologii_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import java.util.Objects;

public class AllSongsActivity extends AppCompatActivity
{
    // Variables
    ListView allSongs;
    GenericSongAdapter cursorAdapter;

    // OnCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_songs);
        setTitle("All Songs");

        // Showing all songs from database
        allSongs = findViewById(R.id.viewAllSongs);

        MyDatabaseHelper db = new MyDatabaseHelper(AllSongsActivity.this);
        Cursor cursor = db.readAllSongs();

        cursorAdapter = new GenericSongAdapter(this, cursor);

        allSongs.setAdapter(cursorAdapter);

        //OnClick event for items in listview
        allSongs.setOnItemClickListener((parent, view, position, id) ->
        {
            Intent intent = new Intent(this, GenericVideoActivity.class);
            Bundle extraStuff = new Bundle();
            extraStuff.putLong("key", position);
            intent.putExtras(extraStuff);
            startActivity(intent);
        });

        // GoBack to MainActivity
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}