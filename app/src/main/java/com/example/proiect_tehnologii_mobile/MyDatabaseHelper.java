package com.example.proiect_tehnologii_mobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper
{
    // Variables
    private Context context;
    private static final String DATABASE_NAME = "Spotify.db";
    private static final int DATABASE_VERSION = 1;

    // "song" table variables
    private static final String TABLE_NAME = "song";
    private static  final String COLUMN_ID ="_id";
    private static final String COLUMN_TITLE = "song_title";
    private static final String COLUMN_ARTIST = "song_artist";
    private static final String COLUMN_DURATION = "song_duration";
    private static final String COLUMN_TYPE = "song_type";
    private static final String COLUMN_LINK = "youtube_link";


    // Constructor
    public MyDatabaseHelper(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Methods
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query="CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_ARTIST + " TEXT, " +
                COLUMN_DURATION + " INTEGER, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_LINK +" TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
