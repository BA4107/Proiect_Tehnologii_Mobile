package com.example.proiect_tehnologii_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Objects;

public class GenericVideoActivity extends AppCompatActivity
{

    // Variables
    TextView txtVideoTitle;
    ImageButton btnPrev, btnPause, btnNext, btnAddPlaylist, btnDelete, btnModify;
    Cursor cursor;

    // OnCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_video);
        setTitle("Video Player");

        // Grabbing the id
        Bundle extraStuff = getIntent().getExtras();
        long songPos = -1;
        if( extraStuff != null)
        {
            songPos = extraStuff.getLong("key");
        }

        // Reading the selected song
        MyDatabaseHelper db = new MyDatabaseHelper(GenericVideoActivity.this);
        cursor = db.readAllData();
        cursor.moveToPosition((int) songPos);

        // findViewById
        txtVideoTitle = findViewById(R.id.txtVideoTitle);
        btnPrev = findViewById(R.id.btnPrev);
        btnPause = findViewById(R.id.btnPause);
        btnNext = findViewById(R.id.btnNext);
        btnAddPlaylist = findViewById(R.id.btnAddToPlaylist);
        btnDelete = findViewById(R.id.btnDelete);
        btnModify = findViewById(R.id.btnModify);

        // Binding the data to the title and video
        txtVideoTitle.setText(cursor.getString(1));
        // TODO Youtube API

        // Button methods
        btnPrev.setOnClickListener(v ->
        {
            if (!cursor.isFirst())
            {
                cursor.moveToPrevious();
            }
            else
            {
                cursor.moveToLast();
            }
            txtVideoTitle.setText(cursor.getString(1));
            // TODO Change video as well
        });

        btnPause.setOnClickListener(v ->
        {
            // TODO Pause video if necessary
        });

        btnNext.setOnClickListener(v ->
        {
            if (!cursor.isLast())
            {
                cursor.moveToNext();
            }
            else
            {
                cursor.moveToFirst();
            }
            txtVideoTitle.setText(cursor.getString(1));
            // TODO Change video as well
        });

        btnAddPlaylist.setOnClickListener(v ->
        {
            // TODO Add to playlist menu
        });

        btnDelete.setOnClickListener(v ->
        {
            db.deleteOneEntry(cursor.getString(0));

            // GoBack to previous activity
            Intent intent = new Intent(GenericVideoActivity.this, AllSongsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            GenericVideoActivity.this.finish();
        });

        long finalSongPos = songPos;
        btnModify.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, ModifySongActivity.class);
            Bundle extraModStuff = new Bundle();
            extraStuff.putLong("key", finalSongPos);
            intent.putExtras(extraStuff);
            startActivity(intent);
        });

        // GoBack to MainActivity
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}