package com.topoteam.topo;

import android.app.Fragment;

import java.util.Collections;
import java.util.List;

public class Vraag {
    private String vraag; //string met de vraag
    private String antwoord; // string met het antwoord
    private DBElement vraagElement; //element die alle info bevat over het element in de vraag
    private DBElement antwoordElement; // element die alle info bevat over het element in het antwoord
    private List<DBElement> distractorElementen;

    private int kaart;
    private Boolean TekenAlleLocaties;
    private boolean showVraagLocatie;
    private boolean showAntwoordLocatie;
    private boolean showDistractorLocatie;
    private boolean showName;
    private boolean showLetter;

    private QuestionFragment vraagtypeFragment; // fragment die het type vraag definieert
    private List<String> distractors; // lijst met distractors

    //getters en setters
    public String getVraag(){return vraag;}
    public String getAntwoord(){return antwoord;}
    public DBElement getVraagInfo(){return vraagElement;}
    public DBElement getAntwoordInfo(){return antwoordElement;}
    public List<String> getDistractors(){return distractors;}
    public QuestionFragment getVraagtypeFragment(){return vraagtypeFragment;}
    public int getKaart(){return kaart;}

    public boolean isShowVraagLocatie() {
        return showVraagLocatie;
    }

    public void setShowVraagLocatie(boolean showVraagLocatie) {
        this.showVraagLocatie = showVraagLocatie;
    }

    public boolean isShowAntwoordLocatie() {
        return showAntwoordLocatie;
    }

    public void setShowAntwoordLocatie(boolean showAntwoordLocatie) {
        this.showAntwoordLocatie = showAntwoordLocatie;
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

    public DBElement getAntwoordElement() {
        return antwoordElement;
    }

    public List<DBElement> getDistractorElementen() {
        return distractorElementen;
    }

    public DBElement getVraagElement() {
        return vraagElement;
    }

    //constructor
    public Vraag(String vraag, String antwoord, QuestionFragment vraagtypeFragment, List<String> distractors, int kaart, Boolean TekenAlleLocaties){
        this.vraag = vraag;
        this.antwoord = antwoord;
        this.vraagtypeFragment = vraagtypeFragment;
        this.distractors = distractors;
        this.kaart = kaart;
        this.TekenAlleLocaties = TekenAlleLocaties;
    }

    //checkt of het gegeven antwoord overeenkomt met het juiste antwoord
    public boolean CheckAnswer(String answer){
        return (answer.equalsIgnoreCase(this.antwoord));
    }
    public boolean CheckAnswer(int x, int y){
        for (int i = 0; i < 10; i++){
            if((antwoordElement.getLocatieX() == x-5+i) && (antwoordElement.getLocatieY() == y-5+i)){return true;}
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
