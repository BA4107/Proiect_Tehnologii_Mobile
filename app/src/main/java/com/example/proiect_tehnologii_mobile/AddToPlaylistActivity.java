package com.example.proiect_tehnologii_mobile;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AddToPlaylistActivity extends AppCompatActivity
{
    // Variables
    MyDatabaseHelper db = new MyDatabaseHelper(AddToPlaylistActivity.this);
    Button addPlaylist;
    ListView allPlaylists;
    GenericPlaylistAdapter cursorAdapter;
    EditText playlistName;
    Cursor playlistCursor, cursor;
    int songPos, song_id;
    String order;

    // OnCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_playlist);
        setTitle("Choose playlist");

        // Grabbing the position
        Bundle extraPlayStuff = getIntent().getExtras();
        {
            songPos = extraPlayStuff.getInt("key");
            order = extraPlayStuff.getString("order");
        }

        // findViewByID
        allPlaylists = findViewById(R.id.viewAddToPlaylists);
        addPlaylist = findViewById(R.id.btnAddPlaylist);
        playlistName = findViewById(R.id.inpPlaylistName);

        // Showing all playlists from database
        playlistCursor = db.readAllPlaylists();
        cursorAdapter = new GenericPlaylistAdapter(this, playlistCursor);
        allPlaylists.setAdapter(cursorAdapter);

        // Button method
        cursor = db.readAllSongs(order);
        cursor.moveToPosition(songPos);
        song_id = cursor.getInt(0);

        addPlaylist.setOnClickListener(v ->
        {
            if(playlistName.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Please fill all of the fields", Toast.LENGTH_SHORT).show();
            }
            else
            {
                db.addPlaylists(playlistName.getText().toString(), song_id);
                refreshListview();
            }
        });

        //OnClick event for items in listview
        allPlaylists.setOnItemClickListener((parent, view, position, id) ->
        {
            playlistCursor.moveToPosition(position);
            db.addPlaylists(playlistCursor.getString(1), song_id);
        });
    }

    private void refreshListview()
    {
        playlistCursor = db.readAllPlaylists();
        cursorAdapter.swapCursor(playlistCursor);
    }
}