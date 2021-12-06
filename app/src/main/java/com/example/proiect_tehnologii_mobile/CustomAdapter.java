package com.example.proiect_tehnologii_mobile;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.SongViewHolder>
{
    // Variables
    private Context context;
    private ArrayList<String> id, title, artist, genre, link;

    @NonNull
    @Override
    public CustomAdapter.SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.SongViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    // Constructor

}
