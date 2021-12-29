package com.example.proiect_tehnologii_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class AddSongActivity extends AppCompatActivity
{
    // Variables
    EditText songTitle, songArtist, songType, songLink;
    Button addSong;

    //OnCreate method
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
        addSong.setOnClickListener(v ->
        {
            if(songTitle.getText().toString().isEmpty() || songArtist.getText().toString().isEmpty()
            || songType.getText().toString().isEmpty() || songLink.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Please fill all of the fields", Toast.LENGTH_SHORT).show();
            }
            else
            {
                MyDatabaseHelper myDb = new MyDatabaseHelper(AddSongActivity.this);
                myDb.addSongs(songTitle.getText().toString(), songArtist.getText().toString(),
                        songType.getText().toString(), songLink.getText().toString());

                // GoBack to previous activity
                Intent intent = new Intent(AddSongActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                AddSongActivity.this.finish();
            }
        });

        // GoBack to MainActivity
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}