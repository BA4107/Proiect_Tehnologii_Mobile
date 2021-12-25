package com.example.proiect_tehnologii_mobile;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    // Variables
    Button deleteAll;

    //OnCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");

        //findViewByID
        deleteAll = findViewById(R.id.btnDeleteAllSongs);

        // Button methods
        deleteAll.setOnClickListener(v ->
        {
            MyDatabaseHelper myDb = new MyDatabaseHelper(SettingsActivity.this);
            myDb.deleteAllData();
        });

        // GoBack to MainActivity
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}