package com.example.proiect_tehnologii_mobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper
{
    // Variables
    private final Context context;
    private static final String DATABASE_NAME = "Spotify.db";
    private static final int DATABASE_VERSION = 1;

    // "song" table variables
    private static final String TABLE_SONG = "song";
    private static final String COLUMN_SONG_ID ="_id";
    private static final String COLUMN_TITLE = "song_title";
    private static final String COLUMN_ARTIST = "song_artist";
    private static final String COLUMN_DURATION = "song_duration";
    private static final String COLUMN_TYPE = "song_type";
    private static final String COLUMN_LINK = "youtube_link";

    // "playlist" table variables
    private static final String TABLE_PLAYLIST = "playlist";
    private static final String COLUMN_PLAYLIST_ID = "_playlist_id";
    private static final String COLUMN_NAME = "playlist_name";


    // Constructor
    public MyDatabaseHelper(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // OnCreate method
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query="CREATE TABLE " + TABLE_SONG + "(" +
                COLUMN_SONG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG);
        onCreate(db);
    }

    // Method for adding songs into the database
    public void addSongs(String title, String artist, String type, String link)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_ARTIST, artist);
        cv.put(COLUMN_TYPE, type);
        cv.put(COLUMN_LINK, link);

        long result = db.insert(TABLE_SONG, null, cv);
        if(result == -1)
        {
            Toast.makeText(context, "Failed to insert", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Successfully inserted", Toast.LENGTH_SHORT).show();
        }
    }

    // Method for selecting all the data from the database
    public Cursor readAllData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_SONG;
        Cursor cursor = null;
        if (db != null)
        {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    // TODO Delete this
    // Method for selecting one row from the database
    public Cursor readOneRow(long id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_SONG + " WHERE " + COLUMN_SONG_ID + " = " + (int) id;
        Cursor cursor = null;
        if(db != null)
        {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    // Method for modifying the info of a song from the database
    public void updateData(String row_id, String title, String artist, String type , String link)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_ARTIST, artist);
        cv.put(COLUMN_TYPE, type);
        cv.put(COLUMN_LINK, link);

        long result = db.update(TABLE_SONG, cv, "_id=?", new String[]{row_id});

        if(result == -1)
        {
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();
        }
    }

    // Method for deleting one song
    public void deleteOneEntry(String row_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(TABLE_SONG, "_id=?", new String[]{row_id});

        if(result == -1)
        {
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
        }
    }

    // Method for deleting the entire "song" table
    public void deleteAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_SONG);

        // TODO Toast messages
    }
}
