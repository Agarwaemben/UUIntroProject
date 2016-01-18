package com.topoteam.topo;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.view.ViewDebug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

// abstracte klasse voor vraaggenerators
abstract class VraagGenerator{
    // lijst van geaccepteerde types en vraagtype
    protected List<Integer> types;
    protected int vraagtype;

    // standaard constructor
    public VraagGenerator(){
        //
    }

    // functie om te bepalen of een bepaald type werkt met deze vraag
    public boolean AllowsType(Context c, String type){
        for (Integer t:types
             ) {
            if (c.getResources().getString(t).equalsIgnoreCase(type)){
                return true;
            }
        }
        return false;
    }

    // functie om te bepalen of een bepaald vraagtype werkt met deze vraag
    public boolean isVraagType(Context c, List<String> type){
        for(String s:type){
            if(s.equalsIgnoreCase(c.getResources().getString(vraagtype))){return true;}
        }
        return false;
    }

    // functie die vanuit een dbelement een vraagobject genereert
    public abstract Vraag genereerVraag(DBElement dbElement, List<DBElement> otherElements, int kaart);
}
//MCV = Multiple Choice Vraag
//AV = Aanwijs Vraag
//OV = Open Vraag
class MCVNederland extends VraagGenerator{
    // constructor die de types set
    public MCVNederland(){
        types = new ArrayList<>(Arrays.asList(R.string.Steden, R.string.Wateren, R.string.Gebergtes, R.string.Provincies));
        vraagtype = R.string.Meerkeuze;
    }

    // genereer een vraag
    public Vraag genereerVraag(DBElement dbElement, List<DBElement> distractorElements, int kaart) {
        List<String> distractors = new ArrayList<>();
        for (DBElement e:distractorElements){distractors.add(e.getNaam());}

        Vraag v = new Vraag("Welk(e) " + dbElement.getType().toLowerCase() + " is getekend?", dbElement.getNaam(), getNewFragment(), distractors, kaart, dbElement, distractorElements);
        v.setShowElementLocatie(true);
        return v;
    }

    protected VraagFragment getNewFragment(){
        return new MultipleChoiceFragment();
    }
}

class AVNederland extends VraagGenerator {
    public AVNederland(){
        types = new ArrayList<>(Arrays.asList(R.string.Steden, R.string.Wateren, R.string.Gebergtes, R.string.Provincies));
        vraagtype = R.string.Aanwijs;
    }

    public Vraag genereerVraag(DBElement dbElement, List<DBElement> distractorElements, int kaart) {
        Vraag v = new Vraag(String.format("Waar ligt %s?", dbElement.getNaam()), dbElement.getLocatieX(), dbElement.getLocatieY(), new AanwijsVraagFragment(), null, kaart, dbElement, distractorElements);
        return v;
    }
}

class OVNederland extends MCVNederland {
    public OVNederland(){
        super();
        vraagtype = R.string.Invul;
    }

    @Override
    protected VraagFragment getNewFragment(){
        return new OpenQuestionFragment();
    }
}

/*
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
}*/