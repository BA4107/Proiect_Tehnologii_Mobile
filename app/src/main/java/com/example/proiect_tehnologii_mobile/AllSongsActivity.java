package com.example.proiect_tehnologii_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class AllSongsActivity extends AppCompatActivity
{

    // Variables
    ListView allSongs;
    ArrayList<String> titles, artists, genres;
    GenericSongAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_songs);
        setTitle("All Songs");

        // Showing all songs from database
        allSongs = findViewById(R.id.viewAllSongs);
        titles = new ArrayList<>();
        artists = new ArrayList<>();
        genres = new ArrayList<>();

        MyDatabaseHelper db = new MyDatabaseHelper(AllSongsActivity.this);
        Cursor cursor = db.readAllData();

        cursorAdapter = new GenericSongAdapter(this, cursor);

        allSongs.setAdapter(cursorAdapter);

        // TODO Delete if necessary
        // Switch to new cursor and update contents of ListView
        //todoAdapter.changeCursor(todoCursor);

        // TODO OnClick CustomAdapter open fragment with video + buttons

        // GoBack to MainActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}