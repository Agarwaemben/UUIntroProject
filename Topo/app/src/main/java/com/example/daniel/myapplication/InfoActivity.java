package com.example.daniel.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.daniel.myapplication.R;

public class InfoActivity extends Activity {

    TopoHelper Helper = new TopoHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_layout);
        Button laad;
        laad = (Button) findViewById(R.id.button4);
        laad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getStad;
                getStad = "";
                Helper.openDataBase();
                Cursor C = Helper.returndata();
                Toast.makeText(getBaseContext(), "Database open ",Toast.LENGTH_LONG ).show();
                if (C.moveToFirst()) {
                    do {
                        getStad = C.getString(0);
                    }while (C.moveToNext());
                }
                Helper.close();
                Toast.makeText(getBaseContext(), "Stad: " + getStad,Toast.LENGTH_LONG ).show();
            }
        });
    }
}
