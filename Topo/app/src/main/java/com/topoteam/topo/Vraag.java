package com.topoteam.topo;

import android.app.Fragment;

import java.util.Collections;
import java.util.List;

public class Vraag {
    private String vraag; //string met de vraag
    private String antwoord; // string met het antwoord
    private int antwoordX, antwoordY;
    private DBElement element; //element die alle info bevat over het element in de vraag
    private List<DBElement> distractorElementen;

    private int kaart;
    private boolean showElementLocatie;
    private boolean showDistractorLocatie;
    private boolean showName;
    private boolean showLetter;

    private QuestionFragment vraagtypeFragment; // fragment die het type vraag definieert
    private List<String> distractors; // lijst met distractors

    //getters en setters
    public String getVraag(){return vraag;}
    public String getAntwoord(){return antwoord;}
    public List<String> getDistractors(){return distractors;}
    public QuestionFragment getVraagtypeFragment(){return vraagtypeFragment;}
    public int getKaart(){return kaart;}

    public boolean isShowElementLocatie() {
        return showElementLocatie;
    }

    public void setShowElementLocatie(boolean showElementLocatie) {
        this.showElementLocatie = showElementLocatie;
    }

    public boolean isShowName() {
        return showName;
    }

    public void setShowName(boolean showName) {
        this.showName = showName;
    }

    public boolean isShowDistractorLocatie() {
        return showDistractorLocatie;
    }

    public void setShowDistractorLocatie(boolean showDistractorLocatie) {
        this.showDistractorLocatie = showDistractorLocatie;
    }

    public boolean isShowLetter() {
        return showLetter;
    }

    public void setShowLetter(boolean showLetter) {
        this.showLetter = showLetter;
    }

    public DBElement getElement() {
        return element;
    }

    public List<DBElement> getDistractorElementen() {
        return distractorElementen;
    }

    //constructor
    public Vraag(String vraag, String antwoord, QuestionFragment vraagtypeFragment, List<String> distractors, int kaart, DBElement element, List<DBElement> distractorElementen){
        this.vraag = vraag;
        this.antwoord = antwoord;
        this.vraagtypeFragment = vraagtypeFragment;
        this.distractors = distractors;
        this.kaart = kaart;
        this.element = element;
        this.distractorElementen = distractorElementen;
    }

    public Vraag(String vraag, int antwoordX, int antwoordY, QuestionFragment vraagtypeFragment, List<String> distractors, int kaart, DBElement element, List<DBElement> distractorElementen){
        this.vraag = vraag;
        this.antwoord = "loc";
        this.antwoordX = antwoordX;
        this.antwoordY = antwoordY;
        this.vraagtypeFragment = vraagtypeFragment;
        this.distractors = distractors;
        this.kaart = kaart;
        this.element = element;
        this.distractorElementen = distractorElementen;
    }

    //checkt of het gegeven antwoord overeenkomt met het juiste antwoord
    public boolean CheckAnswer(String answer){
        return (answer.equalsIgnoreCase(this.antwoord));
    }
    public boolean CheckAnswer(int x, int y){
        for (int i = 0; i < 100; i++){
            for(int j = 0; j < 100; j++){
                if(x+i-50 == element.getLocatieX() && y+j-50 == element.getLocatieY()){return true;}
            }
        }
        return false;
    }


    //returnt een lijst met geshuffeld distractors + antwoord
    public List<String> getOptions(){
        List<String> newList = distractors;
        newList.add(antwoord);
        Collections.shuffle(newList);
        return newList;
    }
}
