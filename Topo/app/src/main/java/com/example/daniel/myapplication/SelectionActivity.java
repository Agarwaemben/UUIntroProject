package com.example.daniel.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.daniel.myapplication.R;

public class SelectionActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_layout);
    }
    public void GameScherm(View view)
    {
        Intent spel = new Intent(this,GameActivity.class);
        startActivity(spel);
    }

}
