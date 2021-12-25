package com.example.proiect_tehnologii_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPlaylistActivity extends AppCompatActivity {

    // Variables
    Cursor cursor;
    EditText playlistName;
    Button addPlaylist;

    // OnCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_playlist);
        setTitle("Add playlist");

        // Getting the songs id
        Bundle extraModStuff = getIntent().getExtras();
        long songPos = -1;
        {
            songPos = extraModStuff.getLong("key");
        }

        // Reading the selected song
        MyDatabaseHelper db = new MyDatabaseHelper(AddPlaylistActivity.this);
        cursor = db.readAllSongs();
        cursor.moveToPosition((int) songPos);

        // findViewById
        playlistName = findViewById(R.id.inpPlaylistName);
        addPlaylist = findViewById(R.id.btnAddPlaylist);

        int song_id = cursor.getInt(0);

        // Button method
        addPlaylist.setOnClickListener(v ->
        {
            db.addPlaylists(playlistName.getText().toString(), song_id);
        });
    }
}