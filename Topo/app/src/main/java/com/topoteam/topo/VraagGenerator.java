package com.topoteam.topo;

import android.view.ViewDebug;

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
//MCV = Multiple Choice Vraag
//AV = Aanwijs Vraag
//OV = Open Vraag
class MCVNederland extends VraagGenerator{
    private List<String> types = new ArrayList<String>(Arrays.asList("Stad", "Water", "Gebied", "Provincie"));

    public Vraag genereerVraag(DBElement dbElement, List<DBElement> distractorElements) {
        List<String> distractors = new ArrayList<>();
        for (DBElement e:distractorElements){distractors.add(e.getNaam());}

        return new Vraag("Welk(e) stad/water/gebied/provinvie is getekend?", dbElement.getNaam(), new MultipleChoiceFragment(), distractors, R.drawable.nederland, false);
    }
}

class AVNederland extends VraagGenerator {
    private List<String> types = new ArrayList<String>(Arrays.asList("Stad", "Water", "Gebied", "Provincie"));

    public Vraag genereerVraag(DBElement dbElement, List<DBElement> distractorElements) {
        List<Integer> distractors = new ArrayList<>();
        for (DBElement e : distractorElements) {distractors.add(e.getLocatieX(),e.getLocatieY());
        }

        return new Vraag("Waar ligt"+dbElement.getNaam()+"?", Integer.toString(dbElement.getLocatieX())+" "+Integer.toString(dbElement.getLocatieY()), new AanwijsVraagFragment(), null, R.drawable.nederland, true);
    }
}

class OVNederland extends VraagGenerator {
    private List<String> types = new ArrayList<String>(Arrays.asList("Stad", "Water", "Gebied", "Provincie"));

    public Vraag genereerVraag(DBElement dbElement, List<DBElement> distractorElements) {
        return new Vraag("Welk(e) stad/water/gebied/provinvie is getekend?", dbElement.getNaam(), new OpenQuestionFragment(), null, R.drawable.nederland, false);
    }
}

class MCVHoofdstadNederland extends VraagGenerator{
    private List<String> types = new ArrayList<String>(Arrays.asList("Stad"));

    public Vraag genereerVraag(DBElement dbElement, List<DBElement> distractorElements) {
        List<String> distractors = new ArrayList<>();
        for (DBElement e:distractorElements){distractors.add(e.getNaam());}

        return new Vraag("Wat is de hoofdstad van "+dbElement.getProvincie()+"?", dbElement.getNaam(), new MultipleChoiceFragment(), distractors, R.drawable.nederland, false);
    }
}

class AVHoofdstadNederland extends VraagGenerator {
    private List<String> types = new ArrayList<String>(Arrays.asList("Stad"));

    public Vraag genereerVraag(DBElement dbElement, List<DBElement> distractorElements) {
        List<Integer> distractors = new ArrayList<>();
        for (DBElement e : distractorElements) {distractors.add(e.getLocatieX(),e.getLocatieY());
        }

        return new Vraag("Waar ligt "+dbElement.getNaam()+"?", Integer.toString(dbElement.getLocatieX())+" "+Integer.toString(dbElement.getLocatieY()), new AanwijsVraagFragment(), null, R.drawable.nederland, true);
    }
}

class OVHoofdstadNederland extends VraagGenerator {
    private List<String> types = new ArrayList<String>(Arrays.asList("Stad"));

    public Vraag genereerVraag(DBElement dbElement, List<DBElement> distractorElements) {
        return new Vraag("Welke Hoofdstad is getekend?", dbElement.getNaam(), new OpenQuestionFragment(), null, R.drawable.nederland, false);
    }
}

class MCVEuropa extends VraagGenerator{
    private List<String> types = new ArrayList<String>(Arrays.asList("Stad", "Water", "Land", "Gebied", "Gebergte"));

    public Vraag genereerVraag(DBElement dbElement, List<DBElement> distractorElements) {
        List<String> distractors = new ArrayList<>();
        for (DBElement e:distractorElements){distractors.add(e.getNaam());}

        return new Vraag("Welk(e) Hoofdstad/water/land/gebied/gebergte is getekend?", dbElement.getNaam(), new MultipleChoiceFragment(), distractors, R.drawable.europa, false);
    }
}
class MCVHoofdstadEuropa extends VraagGenerator{
    private List<String> types = new ArrayList<String>(Arrays.asList("Stad"));

    public Vraag genereerVraag(DBElement dbElement, List<DBElement> distractorElements) {
        List<String> distractors = new ArrayList<>();
        for (DBElement e:distractorElements){distractors.add(e.getNaam());}

        return new Vraag("Van welk land is "+dbElement.getNaam()+" de hoofdstad?", dbElement.getLand(), new MultipleChoiceFragment(), distractors, R.drawable.europa, false);
    }
}

class AVEuropa extends VraagGenerator {
    private List<String> types = new ArrayList<String>(Arrays.asList("Stad", "Water", "Land", "Gebied", "Gebergte"));

    public Vraag genereerVraag(DBElement dbElement, List<DBElement> distractorElements) {
        List<Integer> distractors = new ArrayList<>();
        for (DBElement e : distractorElements) {distractors.add(e.getLocatieX(),e.getLocatieY());
        }

        return new Vraag("Waar ligt"+dbElement.getNaam()+"?", Integer.toString(dbElement.getLocatieX())+" "+Integer.toString(dbElement.getLocatieY()), new AanwijsVraagFragment(), null, R.drawable.europa, true);
    }
}

class AVHoofdstadEuropa extends VraagGenerator {
    private List<String> types = new ArrayList<String>(Arrays.asList("Stad"));

    public Vraag genereerVraag(DBElement dbElement, List<DBElement> distractorElements) {
        List<Integer> distractors = new ArrayList<>();
        for (DBElement e : distractorElements) {distractors.add(e.getLocatieX(),e.getLocatieY());
        }

        return new Vraag("Waar ligt"+dbElement.getNaam()+"?", Integer.toString(dbElement.getLocatieX())+" "+Integer.toString(dbElement.getLocatieY()), new AanwijsVraagFragment(), null, R.drawable.europa, true);
    }
}

class OVEuropa extends VraagGenerator {
    private List<String> types = new ArrayList<String>(Arrays.asList("Stad", "Water", "Land", "Gebied", "Gebergte"));

    public Vraag genereerVraag(DBElement dbElement, List<DBElement> distractorElements) {
        return new Vraag("Welk(e) stad/water/land/gebied/gebergte is getekend?", dbElement.getNaam(), new OpenQuestionFragment(), null, R.drawable.europa, false);
    }
}

class OVHoofdstadEuropa extends VraagGenerator {
    private List<String> types = new ArrayList<String>(Arrays.asList("Stad"));

    public Vraag genereerVraag(DBElement dbElement, List<DBElement> distractorElements) {
        return new Vraag("Wat is de Hoofdstad van "+dbElement.getLand()+"?", dbElement.getNaam(), new OpenQuestionFragment(), null, R.drawable.europa, false);
    }
}

class MCVWereld extends VraagGenerator{
    private List<String> types = new ArrayList<String>(Arrays.asList("Stad", "Water", "Land", "Gebied", "Gebergte"));

    public Vraag genereerVraag(DBElement dbElement, List<DBElement> distractorElements) {
        List<String> distractors = new ArrayList<>();
        for (DBElement e:distractorElements){distractors.add(e.getNaam());}

        return new Vraag("Welk(e) stad/water/land/gebied/gebergte is getekend?", dbElement.getNaam(), new MultipleChoiceFragment(), distractors, R.drawable.wereld, false);
    }
}

class MCVHoofdstadWereld extends VraagGenerator{
    private List<String> types = new ArrayList<String>(Arrays.asList("Stad"));

    public Vraag genereerVraag(DBElement dbElement, List<DBElement> distractorElements) {
        List<String> distractors = new ArrayList<>();
        for (DBElement e:distractorElements){distractors.add(e.getNaam());}

        return new Vraag("Van welk land is "+dbElement.getNaam()+" de hoofdstad?", dbElement.getLand(), new MultipleChoiceFragment(), distractors, R.drawable.wereld, false);
    }
}

class AVWereld extends VraagGenerator {
    private List<String> types = new ArrayList<String>(Arrays.asList("Stad", "Water", "Land", "Gebied", "Gebergte"));

    public Vraag genereerVraag(DBElement dbElement, List<DBElement> distractorElements) {
        List<Integer> distractors = new ArrayList<>();
        for (DBElement e : distractorElements) {distractors.add(e.getLocatieX(),e.getLocatieY());
        }

        return new Vraag("Waar ligt "+dbElement.getNaam()+"?",Integer.toString(dbElement.getLocatieX())+" "+Integer.toString(dbElement.getLocatieY()), new AanwijsVraagFragment(), null, R.drawable.wereld, true);
    }
}

class AVHoofdstadWereld extends VraagGenerator {
    private List<String> types = new ArrayList<String>(Arrays.asList("Stad"));

    public Vraag genereerVraag(DBElement dbElement, List<DBElement> distractorElements) {
        List<Integer> distractors = new ArrayList<>();
        for (DBElement e : distractorElements) {distractors.add(e.getLocatieX(),e.getLocatieY());
        }

        return new Vraag("Waar ligt "+dbElement.getNaam()+"?",Integer.toString(dbElement.getLocatieX())+" "+Integer.toString(dbElement.getLocatieY()), new AanwijsVraagFragment(), null, R.drawable.wereld, true);
    }
}

class OVWereld extends VraagGenerator {
    private List<String> types = new ArrayList<String>(Arrays.asList("Stad", "Water", "Land", "Gebied", "Gebergte"));

    public Vraag genereerVraag(DBElement dbElement, List<DBElement> distractorElements) {
        return new Vraag("Welk(e) stad/water/land/gebied/gebergte is getekend?", dbElement.getNaam(), new OpenQuestionFragment(), null, R.drawable.wereld, false);
    }
}

class OVHoofdstadWereld extends VraagGenerator {
    private List<String> types = new ArrayList<String>(Arrays.asList("Stad"));

    public Vraag genereerVraag(DBElement dbElement, List<DBElement> distractorElements) {
        return new Vraag("Wat is de Hoofdstad van "+dbElement.getLand()+"?", dbElement.getNaam(), new OpenQuestionFragment(), null, R.drawable.wereld, false);
    }
}