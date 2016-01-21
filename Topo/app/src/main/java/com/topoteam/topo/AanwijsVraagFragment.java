package com.topoteam.topo;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AanwijsVraagFragment extends VraagFragment implements QuestionFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        //set de ontouchlistener om kliks op de kaart te registreren
        kaartView.setOnTouchListener(onTouchListener);

        return v;
    }

    private View.OnTouchListener onTouchListener = new View.OnTouchListener(){
        public boolean onTouch(View v, MotionEvent event){
            // krijg de coordinaten van de geklikte positie
            float[] coords = getPointerCoords((ImageView) v, event);

            // als er geklikt is
            if(event.getAction()==MotionEvent.ACTION_UP){
                // check het antwoord met de berekende coordinaten
                onAnswerGiven((int)coords[0], (int)coords[1]);
            }

            return true;
        }
    };

    // wordt uitgevoerd als er een antwoord gegeven is
    private void onAnswerGiven(int x, int y){
        // check het antwoord en beeindig de vraag met het gegeven resultaat
        boolean result = questionListener.CheckAnswer(x, y);
        questionListener.endQuestion(result, hintShown);
    }

    // functie om de coordinaten te berekenen van een klikevent
    private float[] getPointerCoords(ImageView view, MotionEvent event){
        // get de x en y van de positie uit het event
        int index = event.getActionIndex();
        float[] coords = new float[] { event.getX(index), event.getY(index) };

        // schaal de punten van de imageview naar de punten op de bitmap
        Matrix matrix = new Matrix();
        view.getImageMatrix().invert(matrix);
        matrix.postTranslate(view.getScrollX(), view.getScrollY());
        matrix.mapPoints(coords);

        // schaal de geschaalde bitmap naar de originele bitmap
        coords[0]*=scaleFactorX;
        coords[1]*=scaleFactorY;

        return coords;
    }

}
