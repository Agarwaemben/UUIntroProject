package com.topoteam.topo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class MultipleChoiceFragment extends Fragment implements QuestionFragment{
    QuestionListener qListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        throw new UnsupportedOperationException("No layout for this fragment");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            qListener = (QuestionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement QuestionListener");
        }
    }

    @Override
    public void showQuestion() {
        List<String> options = qListener.getOptions();


    }

    @Override
    public void showHint() {

    }
}
