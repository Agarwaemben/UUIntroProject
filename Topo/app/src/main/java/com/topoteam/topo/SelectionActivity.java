package com.topoteam.topo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

public class SelectionActivity extends Activity {

    Spinner spinnerRegio;
    CheckBox checkBoxLanden, checkBoxSteden, checkBoxProvincies, checkBoxWateren, checkBoxGebergtes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_layout);

        addItemsToSelectionSpinner();
        addListenerToSpinner();
        initializeCheckboxes();
    }

    //Verbind de strings in strings.xml met de spinner voor de keuze van regio's
    public void addItemsToSelectionSpinner(){
        spinnerRegio = (Spinner) findViewById(R.id.regio_selectie_spinner);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.regio_keuzes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegio.setAdapter(adapter);
    }

    //Handelt een click-event
    public void addListenerToSpinner() {
        spinnerRegio = (Spinner) findViewById(R.id.regio_selectie_spinner);

        spinnerRegio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Als de user een keuze heeft gemaakt slaan we die op
                String itemSelected = parent.getItemAtPosition(position).toString();


                //Als de user Nederland selecteert mag hij niet Landen of Gebergtes selecteren
                if (itemSelected.equals("Nederland")) {
                    checkBoxLanden.setVisibility(View.INVISIBLE);
                    checkBoxGebergtes.setVisibility(View.INVISIBLE);
                    checkBoxSteden.setVisibility(View.VISIBLE);
                    checkBoxWateren.setVisibility(View.VISIBLE);
                    checkBoxProvincies.setVisibility(View.VISIBLE);

                }

                //Als de user Europa of Wereld selecteert mag hij niet Provincies selecteren
                else {
                    checkBoxLanden.setVisibility(View.VISIBLE);
                    checkBoxGebergtes.setVisibility(View.VISIBLE);
                    checkBoxSteden.setVisibility(View.VISIBLE);
                    checkBoxWateren.setVisibility(View.VISIBLE);
                    checkBoxProvincies.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void initializeCheckboxes() {
        checkBoxLanden = (CheckBox) findViewById(R.id.checkBox_landen);
        checkBoxGebergtes = (CheckBox) findViewById(R.id.checkBox_gebergten);
        checkBoxProvincies = (CheckBox) findViewById(R.id.checkBox_provincies);
        checkBoxSteden = (CheckBox) findViewById(R.id.checkBox_steden);
        checkBoxWateren = (CheckBox) findViewById(R.id.checkBox_wateren);
    }

    //Methode om door te gaan naar het oefenspel
    //TODO hoe moeten we de selectie overbrengen?
    /*public void onStartSpelClick (View view) {
        Intent goToGameActivity = new Intent(this,GameActivity.class);
        //wat moet hier gebeuren?
        goToGameActivity.putExtra();
        startActivity(goToGameActivity);

    }*/
}
