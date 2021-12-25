package com.example.proiect_tehnologii_mobile;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GenericPlaylistAdapter extends CursorAdapter
{
    // Variables
    TextView genericName;
    // Constructor
    public GenericPlaylistAdapter(Context context, Cursor cursor)
    {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.generic_playlist, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        genericName = view.findViewById(R.id.txtGenericPlaylistName);

        String name = cursor.getString(cursor.getColumnIndexOrThrow("playlist_name"));

        genericName.setText(name);
    }
}
