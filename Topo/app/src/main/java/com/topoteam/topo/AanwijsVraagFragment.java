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
        return v;
    }

    View.OnTouchListener onTouchListener = new View.OnTouchListener(){
        public boolean onTouch(View v, MotionEvent event){
            float mx, my;
            mx = event.getX();
            my = event.getY();

            return true;
        }
    };

}