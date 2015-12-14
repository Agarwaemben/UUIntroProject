package com.example.daniel.myapplication;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Selection;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TopoHelper myDbHelper = new TopoHelper(this);

        try {
            myDbHelper.createDataBase();
            Toast.makeText(getBaseContext(), "Database gemaakt", Toast.LENGTH_LONG).show();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
            Toast.makeText(getBaseContext(), "Database gereed", Toast.LENGTH_LONG).show();
        } catch (SQLException sqle) {
            throw sqle;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void SelectieScherm(View view)
    {
        Intent selectie = new Intent(this, SelectionActivity.class);
        startActivity(selectie);
    }

    public void InfoScherm(View view)
    {
        Intent info = new Intent(this,InfoActivity.class);
        startActivity(info);
    }

    public void InstellingenScherm(View view)
    {
        Intent instellingen = new Intent(this,SettingsActivity.class);
        startActivity(instellingen);
    }

    public void MiniGameScherm(View view)
    {
        Intent minigame = new Intent(this,MiniGameActivity.class);
        startActivity(minigame);
    }
}
