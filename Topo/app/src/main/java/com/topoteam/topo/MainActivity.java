package com.topoteam.topo;

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
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*  ImageView v = (ImageView)findViewById(R.id.main_logo);
        v.setImageResource(R.drawable.titel2);*/
    }
    //Optionsmenu verwijderd, omdat we eigen 'Instellingen' knop hebben.

    // start selectionactivity
    public void SelectieScherm(View view)
    {
        Intent selectie = new Intent(this, SelectionActivity.class);
        startActivity(selectie);
    }

    //start infoactivity
    public void InfoScherm(View view)
    {
        Intent info = new Intent(this,InfoActivity.class);
        startActivity(info);
    }

    // start settingsactivity
    public void InstellingenScherm(View view)
    {
        Intent instellingen = new Intent(this,SettingsActivity.class);
        startActivity(instellingen);
    }

}
