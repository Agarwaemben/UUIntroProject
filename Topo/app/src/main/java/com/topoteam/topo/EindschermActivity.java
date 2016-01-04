package com.topoteam.topo;

import android.content.Intent;
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
        int score = gameactivity.getIntExtra("score", 0);
        int aantal_vragen = gameactivity.getIntExtra("aantal_vragen", 1);

        //laat de score zien op het scherm
        TextView laatste_score = (TextView)findViewById(R.id.goed_beantwoord);
        laatste_score.setText("Je hebt"+ score+"van de"+aantal_vragen+"goed beantwoord");

    }

    public void retryButton(View view) {
        Intent retryInt = new Intent(EindschermActivity.this, SelectionActivity.class);
        startActivity(retryInt);
    }

    public void quitButton(View view) {
        finish();
        System.exit(0);
    }


    public void homeButton()
    {
        Intent home = new Intent(EindschermActivity.this, MainActivity.class);
        startActivity(home);
    }

}
