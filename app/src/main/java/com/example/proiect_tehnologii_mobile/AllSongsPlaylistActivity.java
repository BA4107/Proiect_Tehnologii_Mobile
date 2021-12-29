package com.example.proiect_tehnologii_mobile;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class AllSongsPlaylistActivity extends AppCompatActivity {

    // Variables
    MyDatabaseHelper db = new MyDatabaseHelper(AllSongsPlaylistActivity.this);
    ListView allSongs;
    GenericSongAdapter cursorAdapter;
    EditText inpPlaylistName;
    Button changeName;
    ImageButton deletePlaylist;
    String order = "default";
    Cursor cursor, currentPlaylist;
    int playPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_play_list);
        setTitle("Playlist's songs");

        // Grabbing the id
        Bundle extraStuff = getIntent().getExtras();
        if( extraStuff != null)
        {
            playPos = extraStuff.getInt("key");
        }

        // findViewById
        allSongs = findViewById(R.id.viewAllSongsPlaylist);
        inpPlaylistName = findViewById(R.id.inpNewPlaylistName);
        changeName = findViewById(R.id.btnModPlaylistName);
        deletePlaylist = findViewById(R.id.btnDeletePlaylist);

        // Showing all playlists from database
        currentPlaylist = db.readAllPlaylists();
        currentPlaylist.moveToPosition(playPos);
        cursor = db.readSongsFromPlaylist(currentPlaylist.getString(1), order);

        //OnClick event for items in listview
        allSongs.setOnItemClickListener((parent, view, position, id) ->
        {
            Intent intent = new Intent(this, GenericVideoPlaylistActivity.class);
            Bundle extraPlayStuff = new Bundle();
            extraPlayStuff.putInt("key", position);
            extraPlayStuff.putString("playlist", currentPlaylist.getString(1));
            extraPlayStuff.putString("order", order);
            intent.putExtras(extraPlayStuff);
            startActivity(intent);
        });

        cursorAdapter = new GenericSongAdapter(this, cursor);
        allSongs.setAdapter(cursorAdapter);

        // Changing the playlists name
        inpPlaylistName.setText(currentPlaylist.getString(1));
        changeName.setOnClickListener(v ->
        {
            if(inpPlaylistName.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Please fill all of the fields", Toast.LENGTH_SHORT).show();
            }
            else
            {
                db.updatePlaylist(currentPlaylist.getString(1), inpPlaylistName.getText().toString());
                Intent intent = new Intent(AllSongsPlaylistActivity.this, AllPlaylistsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtras(extraStuff);
                startActivity(intent);
                AllSongsPlaylistActivity.this.finish();
            }
        });

        // Deleting the playlist
        deletePlaylist.setOnClickListener(v ->
        {
            db.deletePlaylist(currentPlaylist.getString(1));
            Intent intent = new Intent(AllSongsPlaylistActivity.this, AllPlaylistsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtras(extraStuff);
            startActivity(intent);
            AllSongsPlaylistActivity.this.finish();
        });

    }
    // Order by menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.order_by_song_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.ordDefault:
                order = "default";
                refreshListview();
                return true;
            case R.id.ordTitle:
                order = "title";
                refreshListview();
                return true;
            case R.id.ordArtist:
                order = "artist";
                refreshListview();
                return true;
            case R.id.ordGenre:
                order = "genre";
                refreshListview();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Refresh the current listview
    private void refreshListview()
    {
        cursor = db.readSongsFromPlaylist(currentPlaylist.getString(1), order);
        cursorAdapter.swapCursor(cursor);
    }
}