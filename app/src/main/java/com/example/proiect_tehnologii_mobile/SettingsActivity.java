package com.example.proiect_tehnologii_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    // Variables
    Button deleteAll;
    Button darkLight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");

        //findViewByID
        deleteAll = findViewById(R.id.btnDeleteAllSongs);
        darkLight = findViewById(R.id.btnDarkLight);

        // Button methods
        deleteAll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MyDatabaseHelper myDb = new MyDatabaseHelper(SettingsActivity.this);
                myDb.deleteAllData();
                // TODO Warning before deleting everything
            }
        });

        // DarkMode/LightMode Button
        darkLight.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        // TODO Dark/Light Themes
        // GoBack to MainActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}