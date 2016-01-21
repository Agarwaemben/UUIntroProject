package com.topoteam.topo;

import java.util.List;

// definieert de functies die een klasse moet hebben om met vraagfragmenten te kunnnen communiceren
interface QuestionListener {
    boolean CheckAnswer(String answer);
    boolean CheckAnswer(int x, int y);
    String getVraag();
    String getAntwoord();
    List<String> getDistractors();
    List<String> getOptions();
    int getKaart();
    void endQuestion(boolean result, boolean hintShown);
    boolean isShowName();
    boolean isShowElementLocation();
    boolean isShowDistractorLocatie();
    boolean isShowLetter();
    DBElement getElement();
    List<DBElement> getDistractorElementen();
}
