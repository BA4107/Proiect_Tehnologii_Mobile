package com.example.proiect_tehnologii_mobile;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ModifySongPlaylistActivity extends AppCompatActivity
{
    // Variables
    EditText songTitle, songArtist, songType, songLink;
    Button modSong;
    Cursor cursor;
    String order, playlistName;
    int songPos;

    // OnCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_song);
        setTitle("Change Song Details");

        // Grabbing the id
        Bundle extraModStuff = getIntent().getExtras();
        {
            songPos = extraModStuff.getInt("key");
            playlistName = extraModStuff.getString("playlist");
            order = extraModStuff.getString("order");
        }

        // findViewById
        songTitle = findViewById(R.id.inpModSongTitle);
        songArtist =findViewById(R.id.inpModSongArtist);
        songType = findViewById(R.id.inpModSongType);
        songLink = findViewById(R.id.inpModSongLink);
        modSong = findViewById(R.id.btnModifySong);

        // Reading the selected song
        MyDatabaseHelper db = new MyDatabaseHelper(ModifySongPlaylistActivity.this);
        cursor = db.readSongsFromPlaylist(playlistName, order);
        cursor.moveToPosition((int) songPos);

        songTitle.setText(cursor.getString(1));
        songArtist.setText(cursor.getString(2));
        songType.setText(cursor.getString(4));
        songLink.setText(cursor.getString(5));

        modSong.setOnClickListener(v ->
        {
            if(songTitle.getText().toString().isEmpty() || songArtist.getText().toString().isEmpty()
                    || songType.getText().toString().isEmpty() || songLink.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Please fill all of the fields", Toast.LENGTH_SHORT).show();
            }
            else
            {
                MyDatabaseHelper myDb = new MyDatabaseHelper(ModifySongPlaylistActivity.this);
                myDb.updateData(cursor.getString(0), songTitle.getText().toString(),
                        songArtist.getText().toString(), songType.getText().toString(),
                        songLink.getText().toString());
                Intent intent = new Intent(ModifySongPlaylistActivity.this, GenericVideoPlaylistActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtras(extraModStuff);
                startActivity(intent);
                ModifySongPlaylistActivity.this.finish();
            }
        });
    }
}