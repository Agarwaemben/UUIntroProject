package com.topoteam.topo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

//import java.lang.Boolean;
//import javafx.scene.control.CheckBox;

public class SelectionActivity extends Activity {

    Spinner spinnerRegio;
    CheckBox checkBoxLanden, checkBoxSteden, checkBoxProvincies, checkBoxWateren, checkBoxGebergtes,
            checkBoxMeerkVraag, checkBoxAanwVraag, checkBoxInvulVraag;
    Button buttonStartSpel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_layout);

        addItemsToSelectionSpinner();
        addListenerToSpinner();
        initializeViews();
    }

    //Verbindt de string-array in strings.xml met de spinner voor de keuze van regio's
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

                ((TextView) findViewById(R.id.onderdeel_textview)).setEnabled(true);
                //Als de user een keuze heeft gemaakt slaan we die op
                String itemSelected = parent.getItemAtPosition(position).toString();

                /*Als de user Nederland selecteert mag hij niet Landen of Gebergtes selecteren.
                Om verkeerde mogelijkheden te voorkomen deactiveren / unchecken we de checkboxen */
                if (itemSelected.equals("Nederland")) {
                    checkBoxSteden.setEnabled(true);
                    checkBoxProvincies.setEnabled(true);
                    checkBoxLanden.setEnabled(false);
                    checkBoxWateren.setEnabled(true);
                    checkBoxGebergtes.setEnabled(false);

                    emptyOnderdeelCheckBoxen();
                    emptyVraagCheckBoxen();
                    buttonStartSpel.setEnabled(false);
                } else {
                    checkBoxSteden.setEnabled(true);
                    checkBoxProvincies.setEnabled(false);
                    checkBoxLanden.setEnabled(true);
                    checkBoxWateren.setEnabled(true);
                    checkBoxGebergtes.setEnabled(true);

                    emptyOnderdeelCheckBoxen();
                    emptyVraagCheckBoxen();
                    buttonStartSpel.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //De methode die wordt aangeroepen als er op een onderdeelkeuze wordt geklikt
    public void onOnderdeelCheckBoxClick (View v) {

        //Identificeer op welke checkbox geklikt word, zet dan de check status naar het omgekeerde.
        switch (v.getId()) {
            case R.id.checkBox_steden:
                checkBoxSteden.setChecked(checkBoxSteden.isChecked());
                break;
            case R.id.checkBox_provincies:
                checkBoxProvincies.setChecked(checkBoxProvincies.isChecked());
                break;
            case R.id.checkBox_landen:
                checkBoxLanden.setChecked(checkBoxLanden.isChecked());
                break;
            case R.id.checkBox_wateren:
                checkBoxWateren.setChecked(checkBoxWateren.isChecked());
                break;
            case R.id.checkBox_gebergtes:
                checkBoxGebergtes.setChecked(checkBoxGebergtes.isChecked());
                break;
        }

        //Als tenminste één onderdeel is aangevinkt, wordt de vraagselectie geactiveerd.
        if (checkBoxSteden.isChecked() || checkBoxProvincies.isChecked() || checkBoxLanden.isChecked() ||
                checkBoxWateren.isChecked() || checkBoxGebergtes.isChecked()) {

            ((TextView)findViewById(R.id.vraag_textview)).setEnabled(true);

            checkBoxMeerkVraag.setEnabled(true);
            checkBoxAanwVraag.setEnabled(true);
            checkBoxInvulVraag.setEnabled(true);
        }

        /*Als er geen checkboxen gechecked zijn, worden de vraag checkboxen gedeactiveerd.
         * Omdat het doorgaan naar het oefenspel afhankelijk is van of een vraagsoort geselecteerd is, moeten we deze nu resetten.*/
        else {
            ((TextView)findViewById(R.id.vraag_textview)).setEnabled(false);
            emptyVraagCheckBoxen();
            buttonStartSpel.setEnabled(false);
        }
    }

    //De methode die wordt aangeroepen als er op een vraagkeuze wordt geklikt
    public void onVraagCheckBoxClick (View v) {

        //Identificeren van welke checkbox was aangeklikt
        switch (v.getId()) {
            case R.id.checkBox_meerkeuzeVraag:
                checkBoxMeerkVraag.setChecked(checkBoxMeerkVraag.isChecked());
                break;
            case R.id.checkBox_aanwijsVraag:
                checkBoxAanwVraag.setChecked(checkBoxAanwVraag.isChecked());
                break;
            case R.id.checkBox_invulVraag:
                checkBoxInvulVraag.setChecked(checkBoxInvulVraag.isChecked());
                break;
        }

        //Doorgaan naar laatste deel van deze activity
        if (checkBoxMeerkVraag.isChecked() || checkBoxAanwVraag.isChecked() || checkBoxInvulVraag.isChecked()) {
            buttonStartSpel.setEnabled(true);
        }

        else { buttonStartSpel.setEnabled(false); }
    }

    public void initializeViews() {
        checkBoxLanden = (CheckBox) findViewById(R.id.checkBox_landen);
        checkBoxGebergtes = (CheckBox) findViewById(R.id.checkBox_gebergtes);
        checkBoxProvincies = (CheckBox) findViewById(R.id.checkBox_provincies);
        checkBoxSteden = (CheckBox) findViewById(R.id.checkBox_steden);
        checkBoxWateren = (CheckBox) findViewById(R.id.checkBox_wateren);

        checkBoxMeerkVraag = (CheckBox) findViewById(R.id.checkBox_meerkeuzeVraag);
        checkBoxAanwVraag = (CheckBox) findViewById(R.id.checkBox_aanwijsVraag);
        checkBoxInvulVraag = (CheckBox) findViewById(R.id.checkBox_invulVraag);

        buttonStartSpel = (Button)findViewById(R.id.start_spel_button);
    }

    public void emptyOnderdeelCheckBoxen () {
        checkBoxSteden.setChecked(false);
        checkBoxProvincies.setChecked(false);
        checkBoxLanden.setChecked(false);
        checkBoxWateren.setChecked(false);
        checkBoxGebergtes.setChecked(false);
    }

    public void emptyVraagCheckBoxen () {
        checkBoxMeerkVraag.setChecked(false);
        checkBoxAanwVraag.setChecked(false);
        checkBoxInvulVraag.setChecked(false);

        checkBoxMeerkVraag.setEnabled(false);
        checkBoxAanwVraag.setEnabled(false);
        checkBoxInvulVraag.setEnabled(false);
    }

    //Methode om door te gaan naar het oefenspel
    public void onStartSpelClick (View view) {

        Intent intent = new Intent(this,GameActivity.class);

        intent.putExtra(getString(R.string.Regio), spinnerRegio.getSelectedItem().toString());
        intent.putExtra(getString(R.string.Steden), checkBoxSteden.isChecked());
        intent.putExtra(getString(R.string.Provincies), checkBoxProvincies.isChecked());
        intent.putExtra(getString(R.string.Landen), checkBoxLanden.isChecked());
        intent.putExtra(getString(R.string.Wateren), checkBoxWateren.isChecked());
        intent.putExtra(getString(R.string.Gebergtes), checkBoxGebergtes.isChecked());
        intent.putExtra(getString(R.string.Meerkeuze), checkBoxMeerkVraag.isChecked());
        intent.putExtra(getString(R.string.Aanwijs), checkBoxAanwVraag.isChecked());
        intent.putExtra(getString(R.string.Invul), checkBoxInvulVraag.isChecked());

        startActivity(intent);
    }
}
