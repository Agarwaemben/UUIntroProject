package com.example.daniel.myapplication;

import android.app.Fragment;

import java.util.Collections;
import java.util.List;

public class Vraag implements QuestionListener {
    private String vraag; //string met de vraag
    private String antwoord; // string met het antwoord
    private DBElement vraagElement; //element die alle info bevat over het element in de vraag
    private DBElement antwoordElement; // element die alle info bevat over het element in het antwoord

    private QuestionFragment vraagtypeFragment; // fragment die het type vraag definieert
    private List<String> distractors; // lijst met distractors

    //getters en setters
    public String getVraag(){return vraag;}
    public String getAntwoord(){return antwoord;}
    public DBElement getVraagInfo(){return vraagElement;}
    public DBElement getAntwoordInfo(){return antwoordElement;}
    public List<String> getDistractors(){return distractors;}
    public QuestionFragment getVraagtypeFragment(){return vraagtypeFragment;}

    //constructor
    public Vraag(String vraag, String antwoord, QuestionFragment vraagtypeFragment){
        this.vraag = vraag;
        this.antwoord = antwoord;
        this.vraagtypeFragment = vraagtypeFragment;
    }

    //checkt of het gegeven antwoord overeenkomt met het juiste antwoord
    public boolean CheckAnswer(String answer){
        return (answer.equalsIgnoreCase(this.antwoord));
    }

    //returnt een lijst met geshuffeld distractors + antwoord
    public List<String> getOptions(){
        List<String> newList = distractors;
        newList.add(antwoord);
        Collections.shuffle(newList);
        return newList;
    }
}
