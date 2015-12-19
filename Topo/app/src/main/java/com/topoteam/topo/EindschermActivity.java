package com.topoteam.topo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class EindschermActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eindscherm_layout);
    }

    public void retryButton(View view) {
        Intent retryInt = new Intent(EindschermActivity.this, SelectionActivity.class);
        startActivity(retryInt);
    }

    public void quitButton(View view) {
        Intent quitInt = new Intent(EindschermActivity.this, MainActivity.class);
        startActivity(quitInt);
    }
}
