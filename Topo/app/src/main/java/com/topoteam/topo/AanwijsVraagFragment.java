package com.topoteam.topo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class AanwijsVraagFragment extends VraagFragment implements QuestionFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        kaartView.setOnTouchListener(onTouchListener);
        return v;
    }

    View.OnTouchListener onTouchListener = new View.OnTouchListener(){
        public boolean onTouch(View v, MotionEvent event){
            int posX, posY;
            posX = (int)event.getX();
            posY = (int)event.getY();

            if(event.getAction()==MotionEvent.ACTION_UP){
                onAnswerGiven(posX, posY);
            }

            return true;
        }
    };

    private void onAnswerGiven(int x, int y){
        boolean result = questionListener.CheckAnswer(x, y);
        questionListener.endQuestion(result, hintShown);
    }

}
