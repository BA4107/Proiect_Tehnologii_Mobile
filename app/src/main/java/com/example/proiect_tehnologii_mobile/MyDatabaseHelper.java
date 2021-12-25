package com.example.proiect_tehnologii_mobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
    private static final String COLUMN_ID_PLAY = "_id";
    private static final String COLUMN_NAME = "playlist_name";
    private static final String COLUMN_SPLAY_ID = "song_id";


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
        // Creating the table "song"
        String query="CREATE TABLE " + TABLE_SONG + "(" +
                COLUMN_SONG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_ARTIST + " TEXT, " +
                COLUMN_DURATION + " INTEGER, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_LINK +" TEXT);";
        db.execSQL(query);

        // Creating the table "playlist"
        query = "CREATE TABLE " + TABLE_PLAYLIST + "(" +
                COLUMN_ID_PLAY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " STRING, " +
                COLUMN_SPLAY_ID + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYLIST);
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

    // Method for adding playlists
    // TODO Chech if this shit works
    public void addPlaylists(String name, int song_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_SPLAY_ID, song_id);

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
    public Cursor readAllSongs()
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

    // Method for getting playlist names
    public Cursor readAllPlaylists() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PLAYLIST + " GROUP BY " + COLUMN_NAME;
        Cursor cursor = null;

        if (db != null)
        {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    // Method for selecting all songs from a playlist
    public Cursor readSongsFromPlaylist(String playlistName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PLAYLIST + " WHERE " +
                COLUMN_NAME + " = '" + playlistName + "'";
        ArrayList <Integer> playlistSongs = new ArrayList();
        // Cursor for getting all the song ids in a list
        Cursor cursor = null;
        if (db != null)
        {
            cursor = db.rawQuery(query, null);
        }
        while(cursor.moveToNext())
        {
            playlistSongs.add(cursor.getInt(2));
        }

        // Complicated way to go through the list and add the song in a cursor but this is all I found
        Cursor allSongs = readAllSongs();
        MergeCursor playlistCursor = null;
        // Create a MatrixCursor filled with the rows you want to add.
        MatrixCursor matrixCursor = new MatrixCursor(new String[] {COLUMN_SONG_ID,
                COLUMN_TITLE, COLUMN_ARTIST, COLUMN_DURATION, COLUMN_TYPE,
                COLUMN_LINK});
        if (db != null)
        {
            while(allSongs.moveToNext())
            {
                for(Integer song_id : playlistSongs)
                {
                    if (allSongs.getInt(0) == song_id)
                    {
                        matrixCursor.addRow(new Object[] {allSongs.getInt(0),
                                allSongs.getString(1), allSongs.getString(2),
                                allSongs.getInt(3), allSongs.getString(4),
                                allSongs.getString(5)});
                    }
                }
            }
            playlistCursor = new MergeCursor(new Cursor[] {matrixCursor, null});
        }
        allSongs.close();
        return playlistCursor;
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
        db.execSQL("DELETE FROM " + TABLE_PLAYLIST);
    }

    //TODO Delete playlist method
    // TODO Update playlist name
}
