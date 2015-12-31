package com.topoteam.topo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity implements QuestionListener {
    List<Vraag> vragenlijst; // lijst met alle vragen
    Vraag huidigeVraag; // huidige vraag
    int huidigeVraagInt, score; // huidigevraag nummer, score
    TopoHelper topoHelper; // connectie met database
    QuestionFragment huidigeFragment; // huidige vraag fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);

        vragenlijst = new ArrayList<>();
        //krijg vragen data
        List<String> distractorlijst1 = new ArrayList<>();
        List<String> distractorlijst2 = new ArrayList<>();

        distractorlijst1.add("Rotterdam");
        distractorlijst1.add("Den Haag");
        distractorlijst2.add("Duitsland");
        distractorlijst2.add("Frankrijk");

        Vraag v1 = new Vraag("Wat is de hoofdstad van Nederland", "Amsterdam", new OpenQuestionFragment(),distractorlijst1, R.drawable.nederland);
        Vraag v2 = new Vraag("Van welk land is Amsterdam hoofdstad", "Nederland", new MultipleChoiceFragment(), distractorlijst2, R.drawable.nederland);

        vragenlijst.add(v1);
        vragenlijst.add(v2);

        //initialiseer variabelen
        huidigeVraagInt = 0;
        huidigeVraag = vragenlijst.get(huidigeVraagInt);
        score = 0;

        //initaliseer user interface
        updateUserInterface();
    }

    public void endQuestion(boolean result){
        if(result){score++;}

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
        intent.putExtra("score", score);

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
    public List<DBElement> getDistractorElementen() {
        return huidigeVraag.getDistractorElementen();
    }
}
