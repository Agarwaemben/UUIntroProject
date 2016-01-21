package com.topoteam.topo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class EindschermActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eindscherm_layout);

        // Vind Intent, zodat we de score kunnen pakken vanuit deze intent
        Intent gameactivity = getIntent();
        int score = gameactivity.getIntExtra(getString(R.string.Score), 0);
        int aantal_vragen = gameactivity.getIntExtra(getString(R.string.Aantal_vragen), 1);
        TextView laatste_score = (TextView) findViewById(R.id.goed_beantwoord);

        //laat de score zien op het scherm
        if(score > aantal_vragen/2)
        {
            laatste_score.setTextColor(Color.GREEN);
            laatste_score.setText("Niet slecht! Je hebt " + score + " van de " + aantal_vragen + " goed beantwoord. ");
        }

        else if (score == aantal_vragen)
        {
            laatste_score.setTextColor(Color.GREEN);
            laatste_score.setText("Perfect! Je hebt " + score + " van de " + aantal_vragen + " goed beantwoord. ");
        }

        else
        {
            laatste_score.setTextColor(Color.RED);
            laatste_score.setText("Blijf oefenen! Je hebt " + score + " van de " + aantal_vragen + " goed beantwoord. ");
        }
    }

    public void retryButton(View view) {
        finish();
        System.exit(0);
    }

    public void quitButton(View view) {
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void homeButton(View view)
    {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
