package com.example.proiect_tehnologii_mobile;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import java.util.Objects;

public class AllSongsActivity extends AppCompatActivity
{
    // Variables
    MyDatabaseHelper db = new MyDatabaseHelper(AllSongsActivity.this);
    ListView allSongs;
    GenericSongAdapter cursorAdapter;
    String order = "default";
    Cursor cursor;

    // OnCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_songs);
        setTitle("All Songs");

        // findViewById
        allSongs = findViewById(R.id.viewAllSongs);

        // Showing all songs from database
        cursor = db.readAllSongs(order);
        cursorAdapter = new GenericSongAdapter(this, cursor);
        allSongs.setAdapter(cursorAdapter);

        //OnClick event for items in listview
        allSongs.setOnItemClickListener((parent, view, position, id) ->
        {
            Intent intent = new Intent(this, GenericVideoActivity.class);
            Bundle extraStuff = new Bundle();
            extraStuff.putInt("key", position);
            extraStuff.putString("order", order);
            intent.putExtras(extraStuff);
            startActivity(intent);
        });

        // GoBack to MainActivity
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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
        cursor = db.readAllSongs(order);
        cursorAdapter.swapCursor(cursor);
    }
}