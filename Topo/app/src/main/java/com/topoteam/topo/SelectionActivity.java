package com.topoteam.topo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

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

    public void Checkboxes_Checked(View view){

        boolean checked = ((CheckBox)view.isChecked);

        //kijkt welke checkbox er is gecheckt
        switch (view.getId()){

            case R.id.checkBox_provincies:
                if (checked)
                    //overhoor provincies
                else
                    // geen provincies
                break;

            case  R.id.checkBox_steden
                if(checked)
                    //overhoor steden
                else
                    //geen steden
                break;
            case R.id.checkBox_gebergten
                if (checked)
                    //overhoor gebergten
                else
                    // geen gebergten
                break;

            case R.id.checkBox_wateren
                if (checked)
                    //overhoor wateren
                else
                    // geen wateren
                break;




        }

    }

}
