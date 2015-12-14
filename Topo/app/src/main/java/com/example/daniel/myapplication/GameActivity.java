package com.example.daniel.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.daniel.myapplication.R;

public class GameActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);

        ImageView imageView = (ImageView) findViewById(R.id.Kaart_Nederland);
        imageView.setImageResource(R.drawable.nederland);
//lijst met vragen en fragmentcontainer , vraag bevat een container met type

    }
}

