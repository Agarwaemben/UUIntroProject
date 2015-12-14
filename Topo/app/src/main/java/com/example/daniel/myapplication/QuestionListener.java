package com.example.daniel.myapplication;

import java.util.List;

// definieert de functies die een klasse moet hebben om met vraagfragmenten te kunnnen communiceren
public interface QuestionListener {
    boolean CheckAnswer(String answer);
    String getVraag();
    String getAntwoord();
    List<String> getDistractors();
    List<String> getOptions();
}
