package com.example.proiect_tehnologii_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class AllSongsActivity extends AppCompatActivity
{

    // Variables
    RecyclerView allSongs;
    ArrayList<String> ids, titles, artists, genres, links;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_songs);

        // Showing all songs from database
        allSongs = findViewById(R.id.viewAllSongs);
        ids = new ArrayList<>();
        titles = new ArrayList<>();
        artists = new ArrayList<>();
        genres = new ArrayList<>();
        links = new ArrayList<>();

        MyDatabaseHelper db = new MyDatabaseHelper(AllSongsActivity.this);

        Cursor cursor = db.readAllData();
        while(cursor.moveToNext())
        {
            ids.add(cursor.getString(0));
            titles.add(cursor.getString(1));
            artists.add(cursor.getString(2));
            genres.add(cursor.getString(4));
            links.add(cursor.getString(5));
        }


    }
}