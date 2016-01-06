package com.topoteam.topo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.lang.Boolean;

import javafx.scene.control.CheckBox;

public class SelectionActivity extends Activity {

    Spinner spinnerRegio;
    CheckBox checkBoxLanden, checkBoxSteden, checkBoxProvincies, checkBoxWateren, checkBoxGebergtes,
            checkBoxMeerkVraag, checkBoxAanwVraag, checkBoxInvulVraag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_layout);

        addItemsToSelectionSpinner();
        addListenerToSpinner();
        initializeCheckboxes();
    }

    //Spinnner

    //Verbind de string-array in strings.xml met de spinner voor de keuze van regio's
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

                (TextView)FindViewById(R.id.onderdeel_textview).setEnabled();
                //Als de user een keuze heeft gemaakt slaan we die op
                String itemSelected = parent.getItemAtPosition(position).toString();
                
                /*Als de user Nederland selecteert mag hij niet Landen of Gebergtes selecteren.
                Omdat de gebruiker meerdere keren de spinner kan kiezen, maken we de onderdeel
                selectie elke keer leeg om verkeerde combinaties te voorkomen. */
                if (itemSelected.equals("Nederland")) {
                    checkBoxSteden.setEnabled(true);
                    checkBoxProvincies.setEnabled(true);
                    checkBoxLanden.setEnabled(false);
                    checkBoxWateren.setEnabled(true);
                    checkBoxGebergtes.setEnabled(false);

                    emptyCheckBox();
                }

                //Als de user Europa of Wereld selecteert mag hij niet Provincies selecteren
                else {
                    checkBoxSteden.setEnabled(true);
                    checkBoxProvincies.setEnabled(false);
                    checkBoxLanden.setEnabled(true);
                    checkBoxWateren.setEnabled(true);
                    checkBoxGebergtes.setEnabled(true);

                    emptyCheckBox();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    //Einde spinner

    //Onderdelen checkboxen
    public void onOnderdeelCheckBoxClick (View v) {

        //Identificeer op welke checkbox geklikt word, zet dan de check status naar het omgekeerde.
        switch (v.getId()) {
            case R.id.checkBox_steden:
                checkBoxSteden.toggle();
                break;
            case R.id.checkBox_provincies:
                checkBoxLanden.toggle();
                break;
            case R.id.checkBox_landen:
                checkBoxLanden.toggle();
                break;
            case R.id.checkBox_wateren:
                checkBoxWateren.toggle();
                break;
            case R.id.checkBox_gebergten:
                checkBoxGebergtes.toggle();
                break;
            default:
                break;
        }

        //Als tenminste één onderdeel is aangevinkt, wordt de vraagselectie geactiveerd.
        if (checkBoxSteden.isChecked() || checkBoxProvincies.isChecked() || checkBoxLanden.isChecked() ||
                checkBoxWateren.isChecked() || checkBoxGebergtes.isChecked()) {
            checkBoxMeerkVraag.setEnabled(true);
            checkBoxAanwVraag.setEnabled(true);
            checkBoxInvulVraag.setEnabled(true);
        }

        /*Als de laatste click event ervoor zorgde dat er geen checkboxen gechecked waren, worden gedeactiveerd.
        * Omdat het doorgaan naar het oefenspel afhankelijk is van of een vraagsoort geselecteerd is, moeten we deze nu resetten.*/
        else {
            checkBoxMeerkVraag.setChecked(false);
            checkBoxAanwVraag.setChecked(false);
            checkBoxInvulVraag.setChecked(false);

            checkBoxMeerkVraag.setEnabled(false);
            checkBoxAanwVraag.setEnabled(false);
            checkBoxInvulVraag.setEnabled(false);
        }
    }
    //Einde onderdelen checkboxen

    //Vragen checkboxen
    public void onVraagCheckBoxClicked (View v) {
        switch (v.getId()) {
            case R.id.checkBox_meerkeuzeVraag:
                checkBoxMeerkVraag.toggle();
                break;
            case R.id.checkBox_aanwijsVraag:
                checkBoxAanwVraag.toggle();
                break;
            case R.id.checkBox_invulVraag:
                checkBoxInvulVraag.toggle();
                break;
            default:
                break;
        }

        if (checkBoxMeerkVraag.isChecked() || checkBoxAanwVraag.isChecked() || checkBoxInvulVraag.isChecked()) {
            (TextView)findViewById(R.id.vraag_textview).setEnabled();
            (Button)findViewById(R.id.start_spel_button).setEnabled();
        }



    }
    //Einde Vragen checkboxen

    public void initializeCheckboxes() {
        checkBoxLanden = (CheckBox) findViewById(R.id.checkBox_landen);
        checkBoxGebergtes = (CheckBox) findViewById(R.id.checkBox_gebergten);
        checkBoxProvincies = (CheckBox) findViewById(R.id.checkBox_provincies);
        checkBoxSteden = (CheckBox) findViewById(R.id.checkBox_steden);
        checkBoxWateren = (CheckBox) findViewById(R.id.checkBox_wateren);

        checkBoxMeerkVraag = (CheckBox) findViewById(R.id.checkBox_meerkeuzeVraag)
        checkBoxAanwVraag = (CheckBox) findViewById(R.id.checkBox_aanwijsVraag);
        checkBoxInvulVraag = (CheckBox) findViewById(R.id.checkBox_invulVraag);
    }

    public void emptyCheckBox () {
        checkBoxSteden.setChecked(false);
        checkBoxProvincies.setChecked(false);
        checkBoxLanden.setChecked(false);
        checkBoxWateren.setChecked(false);
        checkBoxGebergtes.setChecked(false);
    }

    //Methode om door te gaan naar het oefenspel
    public void onStartSpelClick (View view) {

        Intent intent = new Intent(this,GameActivity.class);

        intent.putExtra("Regio", spinnerRegio.getSelectedItem().toString());
        intent.putExtra("Steden", checkBoxSteden.isChecked());
        intent.putExtra("Provincies", checkBoxProvincies.isChecked());
        intent.putExtra("Landen", checkBoxLanden.isChecked());
        intent.putExtra("Wateren", checkBoxWateren.isChecked());
        intent.putExtra("Gebergtes", checkBoxGebergtes.isChecked());
        intent.putExtra("Meerkeuze", checkBoxMeerkVraag.isChecked());
        intent.putExtra("Aanwijs", checkBoxAanwVraag.isChecked());
        intent.putExtra("Invul", checkBoxInvulVraag.isChecked());

        startActivity(intent);
    }
}
