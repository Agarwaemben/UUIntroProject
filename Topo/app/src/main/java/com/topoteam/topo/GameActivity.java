package com.topoteam.topo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity implements QuestionListener {
    private SharedPreferences preferences; // global app settings

    private List<Vraag> vragenlijst; // lijst met alle vragen
    private Vraag huidigeVraag; // huidige vraag
    private int huidigeVraagInt;
    private int score; // huidigevraag nummer, score
    private TopoHelper topoHelper; // connectie met database
    private QuestionFragment huidigeFragment; // huidige vraag fragment

    // variabelen voor de connectie met selectionactivity
    private String regio;
    private List<String> types;
    private List<String> vraagtypes;

    // variabelen om geluid mogelijk te maken
    private SoundPool sound;
    private AudioAttributes aa;
    private int maxStreams;
    private int volume;

    // referenties naar de geluidsbestanden
    private int soundGoed;
    private int soundFout;

    // settings voor het geluid
    private boolean soundEffects;
    private boolean repeatQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout); // set gamelayout

        // get de preference settings
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        // zet de maximaal aantal geluiden dat tegelijkertijd kan spelen
        maxStreams = 5;

        // initieer soundpool object
        if(Build.VERSION.SDK_INT>=21) {
            aa = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();
            sound = new SoundPool.Builder().setMaxStreams(maxStreams).setAudioAttributes(aa).build();
        }
        else{
            sound = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC ,1);
        }

        // get de setting voor geluidseffecten
        soundEffects = preferences.getBoolean("pref_key_soundeffects", true);

        // laad de geluidseffecten in het geheugen
        soundGoed = sound.load(this, R.raw.goed, 1);
        soundFout = sound.load(this, R.raw.fout, 1);
        volume = 1;

        // get de setting voor het herhalen van foute vragen
        repeatQuestion = preferences.getBoolean("pref_key_repeatq", false);

        // krijg de intent van de selectionactivity
        Intent i = getIntent();

        // creer lijsten voor de geselecteerde types
        types = new ArrayList<>();
        vraagtypes = new ArrayList<>();

        // get de regio
        regio = i.getStringExtra(getString(R.string.Regio));

        // get alle selecties voor soorten
        if(i.getBooleanExtra(getString(R.string.Steden), false)){types.add(getString(R.string.Steden));}
        if(i.getBooleanExtra(getString(R.string.Provincies), false)){types.add(getString(R.string.Provincies));}
        if(i.getBooleanExtra(getString(R.string.Wateren), false)){types.add(getString(R.string.Wateren));}
        if(i.getBooleanExtra(getString(R.string.Landen), false)){types.add(getString(R.string.Landen));}
        if(i.getBooleanExtra(getString(R.string.Gebergtes), false)){types.add(getString(R.string.Gebergtes));}

        // get alle selecties voor vraagsoorten
        if(i.getBooleanExtra(getString(R.string.Meerkeuze), false)){vraagtypes.add(getString(R.string.Meerkeuze));}
        if(i.getBooleanExtra(getString(R.string.Aanwijs), false)){vraagtypes.add(getString(R.string.Aanwijs));}
        if(i.getBooleanExtra(getString(R.string.Invul), false)){vraagtypes.add(getString(R.string.Invul));}

        // creer de vragenlijst

        // creer database object
        topoHelper = new TopoHelper(this);

        try {
            topoHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            topoHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        // genereer vragenlijst
        vragenlijst = topoHelper.generateQuestionList(types,vraagtypes,regio);

        //initialiseer variabelen
        huidigeVraagInt = 0;
        huidigeVraag = vragenlijst.get(huidigeVraagInt);
        score = 0;

        //initaliseer user interface
        updateUserInterface();
    }

    public void endQuestion(boolean result, boolean hintShown){
        // als de vraag goed beantwoord is
        if(result){
            // geluidseffect
            if(soundEffects){sound.play(soundGoed, volume, volume, 1,0,1);}
            // score omhoog
            score++;
        } else {
            // als de vraag fout is
            if(soundEffects){sound.play(soundFout, volume, volume, 1,0,1);} // geluidseffect
            if(repeatQuestion){vragenlijst.add(huidigeVraag); // herhaal de vraag
            }
        }

        huidigeVraagInt++; // ga naar de volgende vraag
        if(huidigeVraagInt < vragenlijst.size()){ // als er nog een volgende vraag is
            huidigeVraag = vragenlijst.get(huidigeVraagInt); // zet de huidigevraag naar de nieuwe vraag
            updateUserInterface(); // update de user interface
        } else{
            // als er geen nieuwe vragen meer zijn, beeindig het spel
            endGame();
        }
    }

    // functie om het spel te beeindigen
    private void endGame(){
        // creer intent voor eindscherm activity
        Intent intent = new Intent(this, EindschermActivity.class);

        //geeft extra informatie mee aan de intent
        intent.putExtra(getString(R.string.Score), score);
        intent.putExtra(getString(R.string.Aantal_vragen), vragenlijst.size());

        // start de eindschermactivity
        startActivity(intent);
    }

    private void updateUserInterface(){
        // update het vraagfragment
        // prepareer fragmentmanager
        LinearLayout fragment_container = (LinearLayout) findViewById(R.id.container_Vraagfragment);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        huidigeFragment = huidigeVraag.getVraagtypeFragment(); // get nieuwe fragment
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE); // verwijder alle oude fragmenten
        fragmentTransaction.replace(fragment_container.getId(), (Fragment) huidigeFragment); //vervang het oude fragment met het nieuwe fragment
        fragmentTransaction.commit(); // voer de verandering uit

        //update de scorelabels
        setTextViewText(R.id.textview_Score, Integer.toString(score)); // update scorelabel
        setTextViewText(R.id.textview_Vraagcounter, "Vraag: " + Integer.toString(huidigeVraagInt + 1) + "/" + Integer.toString(vragenlijst.size())); // update vraagcounterlabel

        //update de kaart label
        setTextViewText(R.id.textview_Settings, "Kaart: " + regio);

        // event handler voor de hintknop
        Button b = (Button)findViewById(R.id.button_Hint);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                huidigeFragment.onShowHint();
            }
        });
        
        Button b2 = (Button)findViewById(R.id.button_Pas);
        b2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onPas();
            }
        });
        
        Button b3 = (Button)findViewById(R.id.button_VorigeGoed);
        b3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onVorigeGoed();
            }
        });
    }

    // methode om tekst van textview te setten
    private void setTextViewText(int id, String text){
        TextView v = (TextView) findViewById(id);
        v.setText(text);
    }
    
    private void onPas(){
        //
    }
    
    private void onVorigeGoed(){
        //
    }

    //getters en setters voor de vraagobjecten
    @Override
    public boolean CheckAnswer(String answer) {
        return huidigeVraag.CheckAnswer(answer);
    }

    @Override
    public String getVraag() {
        return huidigeVraag.getVraag();
    }

    @Override
    public String getAntwoord() {
        return huidigeVraag.getAntwoord();
    }

    @Override
    public List<String> getDistractors() {
        return huidigeVraag.getDistractors();
    }

    @Override
    public List<String> getOptions() {
        return huidigeVraag.getOptions();
    }

    @Override
    public int getKaart() {
        return huidigeVraag.getKaart();
    }

    @Override
    public boolean isShowDistractorLocatie() {
        return huidigeVraag.isShowDistractorLocatie();
    }

    @Override
    public boolean isShowLetter() {
        return huidigeVraag.isShowLetter();
    }

    @Override
    public boolean isShowName() {
        return huidigeVraag.isShowName();
    }

    @Override
    public boolean CheckAnswer(int x, int y){
        return huidigeVraag.CheckAnswer(x, y);
    }

    @Override
    public List<DBElement> getDistractorElementen() {
        return huidigeVraag.getDistractorElementen();
    }

    @Override
    public DBElement getElement() {
        return huidigeVraag.getElement();
    }

    @Override
    public boolean isShowElementLocation() {
        return huidigeVraag.isShowElementLocatie();
    }
}
