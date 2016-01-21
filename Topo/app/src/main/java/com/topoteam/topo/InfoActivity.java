package com.topoteam.topo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class InfoActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_layout);
    }

    //zorgt ervoor dat de gebruiker naar de website gaat
    public void GoToWebsite(View view3){
        Intent websitebrowser = new Intent (Intent.ACTION_VIEW,Uri.parse("https://www.projects.science.uu.nl/INFOB1PICA/2015/03"));
        startActivity(websitebrowser);
    }
}