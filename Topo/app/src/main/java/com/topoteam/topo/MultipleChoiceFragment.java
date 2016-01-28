package com.topoteam.topo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MultipleChoiceFragment extends VraagFragment implements QuestionFragment {
    List<Button> buttons;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        List<String> options = questionListener.getOptions(); // sla alle opties voor de vraag op
        buttons = new ArrayList<>();

        // *bugfix* haal alle duplicates uit de options list
        Set<String> hs = new HashSet<>();
        hs.addAll(options);
        options.clear();
        options.addAll(hs);

        //voor elk mogelijk antwoord(optie)
        for(int i=0; i<options.size(); i++){
            Button b = new Button(getActivity()); // creer een nieuwe button

            b.setText(options.get(i)); // zet de tekst voor de button
            b.setTextSize(8);
            b.setTag(R.id.answer_key, options.get(i)); // voeg 1 van de opties toe aan de button
            b.setOnClickListener(buttonClickHandler); // zet de event handler voor de button
            buttons.add(b);

            l_answercontainer.addView(b, l_layout_params); // voeg de button toe aan de layout
        }

        return v;
    }

    // event handler voor de antwoordbuttons
    private View.OnClickListener buttonClickHandler = new View.OnClickListener(){
        public void onClick(View v){
            boolean result = questionListener.CheckAnswer((String)v.getTag(R.id.answer_key)); // check het antwoord dat gegeven is
            questionListener.endQuestion(result, hintShown);
        }
    };

    public void onShowHint(){
        if(!showHint){
            List<Button> distractors = new ArrayList<>();
            for(Button b : buttons){
                if(!questionListener.CheckAnswer((String) b.getTag(R.id.answer_key))){
                    distractors.add(b);
                }
            }
            Random r = new Random();
            distractors.remove(r.nextInt(distractors.size()));
            for(Button b : distractors){
                b.setEnabled(false);
            }
            showHint=true;
        }
    }
}
