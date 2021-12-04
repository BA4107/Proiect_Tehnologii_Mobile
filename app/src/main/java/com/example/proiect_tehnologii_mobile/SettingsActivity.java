package com.example.proiect_tehnologii_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    // Variables
    Button deleteAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //findViewByID
        deleteAll = findViewById(R.id.btnDeleteAllSongs);

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
    }


}