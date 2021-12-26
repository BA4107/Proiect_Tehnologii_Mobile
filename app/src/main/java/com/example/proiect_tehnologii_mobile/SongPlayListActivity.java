package com.example.proiect_tehnologii_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.sql.SQLException;

public class SongPlayListActivity extends AppCompatActivity {

    // Variables
    ListView allSongs;
    GenericSongAdapter cursorAdapter;
    EditText inpPlaylistName;
    Button changeName;
    ImageButton deletePlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_play_list);
        setTitle("Playlist's songs");

        // Grabbing the id
        Bundle extraStuff = getIntent().getExtras();
        long playPos = -1;
        if( extraStuff != null)
        {
            playPos = extraStuff.getLong("key");
        }

        // Showing all playlists from database
        allSongs = findViewById(R.id.viewAllSongsPlaylist);

        MyDatabaseHelper db = new MyDatabaseHelper(SongPlayListActivity.this);
        Cursor currentPlaylist = db.readAllPlaylists();
        currentPlaylist.moveToPosition((int) playPos);
        Cursor cursor = null;

        cursor = db.readSongsFromPlaylist(currentPlaylist.getString(1));

        //OnClick event for items in listview
        allSongs.setOnItemClickListener((parent, view, position, id) ->
        {
            Intent intent = new Intent(this, GenericPlaylistVideoActivity.class);
            Bundle extraPlayStuff = new Bundle();
            extraPlayStuff.putLong("key", position);
            extraPlayStuff.putString("playlist", currentPlaylist.getString(1));
            intent.putExtras(extraPlayStuff);
            startActivity(intent);
        });

        cursorAdapter = new GenericSongAdapter(this, cursor);
        allSongs.setAdapter(cursorAdapter);

        // Changing the playlists name
        inpPlaylistName = findViewById(R.id.inpNewPlaylistName);
        changeName = findViewById(R.id.btnModPlaylistName);

        inpPlaylistName.setText(currentPlaylist.getString(1));
        changeName.setOnClickListener(v ->
        {
            db.updatePlaylist(currentPlaylist.getString(1), inpPlaylistName.getText().toString());
            Intent intent = new Intent(SongPlayListActivity.this, PlaylistsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtras(extraStuff);
            startActivity(intent);
            SongPlayListActivity.this.finish();
        });

        // Deleting the playlist
        deletePlaylist = findViewById(R.id.btnDeletePlaylist);
        deletePlaylist.setOnClickListener(v ->
        {
            db.deletePlaylist(currentPlaylist.getString(1));
            Intent intent = new Intent(SongPlayListActivity.this, PlaylistsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtras(extraStuff);
            startActivity(intent);
            SongPlayListActivity.this.finish();
        });

    }
}