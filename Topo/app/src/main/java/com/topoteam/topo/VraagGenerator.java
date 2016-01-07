package com.topoteam.topo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

abstract class VraagGenerator{
    private List<String> types;

    public VraagGenerator(){

    }

    public boolean AllowsType(String type){
        for (String t:types
             ) {
            if (t.equals(type)){
                return true;
            }
        }

        return false;
    }

    public abstract Vraag genereerVraag(DBElement dbElement, List<DBElement> otherElements);
}

class MultipleChoiceVraag1 extends VraagGenerator{
    private List<String> types = new ArrayList<String>(Arrays.asList("steden", "rivieren"));

    public Vraag genereerVraag(DBElement dbElement, List<DBElement> distractorElements) {
        List<String> distractors = new ArrayList<>();
        for (DBElement e:distractorElements){distractors.add(e.getNaam());}

        return new Vraag("Welke stad is getekend", dbElement.getNaam(), new MultipleChoiceFragment(), distractors, R.drawable.nederland);
    }
}