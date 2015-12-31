package com.topoteam.topo;

import java.util.List;

// definieert de functies die een klasse moet hebben om met vraagfragmenten te kunnnen communiceren
public interface QuestionListener {
    boolean CheckAnswer(String answer);
    String getVraag();
    String getAntwoord();
    List<String> getDistractors();
    List<String> getOptions();
    int getKaart();
    void endQuestion(boolean result);
    boolean isShowVraagLocatie();
    boolean isShowAntwoordLocatie();
    boolean isShowName();
    boolean isShowDistractorLocatie();
    boolean isShowLetter();
    DBElement getVraagElement();
    DBElement getAntwoordElement();
    List<DBElement> getDistractorElementen();
}
