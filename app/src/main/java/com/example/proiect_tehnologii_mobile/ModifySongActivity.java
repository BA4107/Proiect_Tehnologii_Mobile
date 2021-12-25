package com.example.proiect_tehnologii_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ModifySongActivity extends AppCompatActivity
{
    // Variables
    EditText songTitle, songArtist, songType, songLink;
    Button modSong;
    Cursor cursor;


    // OnCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_song);
        setTitle("Change Song Details");

        // Grabbing the id
        Bundle extraModStuff = getIntent().getExtras();
        long songPos = -1;
        {
            songPos = extraModStuff.getLong("key");
        }

        // Reading the selected song
        MyDatabaseHelper db = new MyDatabaseHelper(ModifySongActivity.this);
        cursor = db.readAllSongs();
        cursor.moveToPosition((int) songPos);
        // findViewById
        songTitle = findViewById(R.id.inpModSongTitle);
        songArtist =findViewById(R.id.inpModSongArtist);
        songType = findViewById(R.id.inpModSongType);
        songLink = findViewById(R.id.inpModSongLink);
        modSong = findViewById(R.id.btnModifySong);

        songTitle.setText(cursor.getString(1));
        songArtist.setText(cursor.getString(2));
        songType.setText(cursor.getString(4));
        songLink.setText(cursor.getString(5));

        modSong.setOnClickListener(v ->
        {
            MyDatabaseHelper myDb = new MyDatabaseHelper(ModifySongActivity.this);
            myDb.updateData(cursor.getString(0), songTitle.getText().toString(),
                    songArtist.getText().toString(), songType.getText().toString(),
                    songLink.getText().toString());
            Intent intent = new Intent(ModifySongActivity.this, GenericVideoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtras(extraModStuff);
            startActivity(intent);
            ModifySongActivity.this.finish();
        });
    }
}