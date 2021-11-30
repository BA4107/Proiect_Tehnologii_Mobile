package com.example.proiect_tehnologii_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // Variables
    EditText songTitle;
    EditText songArtist;
    EditText songType;
    EditText songLink;
    Button addSong;

    // Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                MyDatabaseHelper myDb = new MyDatabaseHelper(MainActivity.this);
                myDb.addSongs(songTitle.getText().toString(), songArtist.getText().toString(),
                        songType.getText().toString(), songLink.getText().toString());
            }
        });
    }
}