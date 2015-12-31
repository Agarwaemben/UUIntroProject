package com.topoteam.topo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class OpenQuestionFragment extends VraagFragment implements QuestionFragment {
    EditText inputfield; // inputveld

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        // creer inputveld
        inputfield = new EditText(getActivity());
        inputfield.setLayoutParams(l_layout_params);

        //creer antwoordknop
        Button answer = new Button(getActivity());
        answer.setText("OK");
        answer.setLayoutParams(l_layout_params);
        answer.setOnClickListener(buttonClickHandler); // zet onclicklistener voor antwoordknop

        // voeg de views toe aan de layout
        l_answercontainer.addView(inputfield);
        l_answercontainer.addView(answer);

        return v;
    }

    View.OnClickListener buttonClickHandler = new View.OnClickListener(){
        public void onClick(View v){
            // krijg de invoer in het invoerveld en check het antwoord
            boolean result = questionListener.CheckAnswer(inputfield.getText().toString());
            questionListener.endQuestion(result);
        }
    };


}
