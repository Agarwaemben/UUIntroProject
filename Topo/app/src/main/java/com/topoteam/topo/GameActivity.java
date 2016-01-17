package com.topoteam.topo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameActivity extends AppCompatActivity implements QuestionListener {
    SharedPreferences preferences;

    List<Vraag> vragenlijst; // lijst met alle vragen
    Vraag huidigeVraag; // huidige vraag`
    int huidigeVraagInt, score; // huidigevraag nummer, score
    TopoHelper topoHelper; // connectie met database
    QuestionFragment huidigeFragment; // huidige vraag fragment

    SoundPool sound;
    AudioAttributes aa;
    int maxStreams, volume;
    int soundGoed, soundFout;

    boolean soundEffects, repeatQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        maxStreams = 5;

        if(Build.VERSION.SDK_INT>=21) {
            aa = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();
            sound = new SoundPool.Builder().setMaxStreams(maxStreams).setAudioAttributes(aa).build();
        }
        else{
            sound = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC ,1);
        }

        soundEffects = preferences.getBoolean("pref_key_soundeffects", true);
        soundGoed = sound.load(this, R.raw.goed, 1);
        soundFout = sound.load(this, R.raw.fout, 1);
        volume = 1;

        repeatQuestion = preferences.getBoolean("pref_key_repeatq", false);

        vragenlijst = new ArrayList<>();
        //krijg vragen data
        List<String> soort = new ArrayList<>();
        soort.add("Stad");
        vragenlijst = topoHelper.generateQuestionList(soort, "Kaart_Nederland");


        //initialiseer variabelen
        huidigeVraagInt = 0;
        huidigeVraag = vragenlijst.get(huidigeVraagInt);
        score = 0;

        //initaliseer user interface
        updateUserInterface();
    }

    public void endQuestion(boolean result, boolean hintShown){
        if(result){
            if(soundEffects){sound.play(soundGoed, volume, volume, 1,0,1);}
            score++;
        } else {
            if(soundEffects){sound.play(soundFout, volume, volume, 1,0,1);}
            if(repeatQuestion){vragenlijst.add(huidigeVraag);
            }
        }

        huidigeVraagInt++;
        if(huidigeVraagInt < vragenlijst.size()){
            huidigeVraag = vragenlijst.get(huidigeVraagInt);
            updateUserInterface();
        } else{
            System.out.println("endgame");
            endGame();
        }
    }

    private void endGame(){
        Intent intent = new Intent(this, EindschermActivity.class);
        //geeft extra informatie mee aan de volgende intent
        intent.putExtra(getString(R.string.Score), score);
        intent.putExtra(getString(R.string.Aantal_vragen),vragenlijst.size());

        startActivity(intent);
    }

    private void updateUserInterface(){
        // update het vraagfragment
        // prepareer fragmentmanager
        LinearLayout fragment_container = (LinearLayout) findViewById(R.id.container_Vraagfragment);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        huidigeFragment = huidigeVraag.getVraagtypeFragment(); // get nieuwe fragment
        fragmentTransaction.replace(fragment_container.getId(), (Fragment) huidigeFragment); //vervang het oude fragment met het nieuwe fragment
        fragmentTransaction.commit(); // voer de verandering uit

        //update de scorelabels
        setTextViewText(R.id.textview_Score, Integer.toString(score)); // update scorelabel
        setTextViewText(R.id.textview_Vraagcounter, "Vraag: " + Integer.toString(huidigeVraagInt + 1) + "/" + Integer.toString(vragenlijst.size())); // update vraagcounterlabel
    }

    // methode om tekst van textview te setten
    private void setTextViewText(int id, String text){
        TextView v = (TextView) findViewById(id);
        v.setText(text);
    }

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
    public DBElement getAntwoordElement() {
        return huidigeVraag.getAntwoordElement();
    }

    @Override
    public DBElement getVraagElement() {
        return huidigeVraag.getVraagElement();
    }

    @Override
    public boolean isShowAntwoordLocatie() {
        return huidigeVraag.isShowAntwoordLocatie();
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
    public boolean isShowVraagLocatie() {
        return huidigeVraag.isShowVraagLocatie();
    }

    @Override
    public boolean CheckAnswer(int x, int y){
        return huidigeVraag.CheckAnswer(x,y);
    }

    @Override
    public List<DBElement> getDistractorElementen() {
        return huidigeVraag.getDistractorElementen();
    }
}
