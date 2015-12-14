package com.example.daniel.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.daniel.myapplication.R;

import java.util.List;

public class GameActivity extends Activity {
    List<Vraag> vragenlijst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
    }


}

