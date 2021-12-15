package com.example.proiect_tehnologii_mobile;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class GenericSongAdapter extends CursorAdapter
{
    // Variables
    TextView songTitle;
    TextView songArtist;
    TextView songGenre;

    // Constructor
    public GenericSongAdapter(Context context, Cursor cursor)
    {
        super(context, cursor, 0);
    }

    // Methods
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.generic_song, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        songTitle = view.findViewById(R.id.txtGenericTitle);
        songArtist = view.findViewById(R.id.txtGenericArtist);
        songGenre = view.findViewById(R.id.txtGenericGenre);

        // Extract properties from cursor
        String title = cursor.getString(cursor.getColumnIndexOrThrow("song_title"));
        String artist = cursor.getString(cursor.getColumnIndexOrThrow("song_artist"));
        String genre = cursor.getString(cursor.getColumnIndexOrThrow("song_type"));

        // Populate fields with extracted properties
        songTitle.setText(title);
        songArtist.setText(artist);
        songGenre.setText(genre);

    }
}