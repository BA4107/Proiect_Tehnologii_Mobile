package com.example.proiect_tehnologii_mobile;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddSongActivity extends AppCompatActivity {
    // Variables
    EditText songTitle, songArtist, songType, songLink;
    Button addSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);
        setTitle("Add Song");

        // findViewById
        songTitle = findViewById(R.id.inpSongTitle);
        songArtist =findViewById(R.id.inpSongArtist);
        songType = findViewById(R.id.inpSongType);
        songLink = findViewById(R.id.inpSongLink);
        addSong = findViewById(R.id.btnAddSong);

        // Button method
        addSong.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MyDatabaseHelper myDb = new MyDatabaseHelper(AddSongActivity.this);
                myDb.addSongs(songTitle.getText().toString(), songArtist.getText().toString(),
                        songType.getText().toString(), songLink.getText().toString());
            }
        });

        // GoBack to MainActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}