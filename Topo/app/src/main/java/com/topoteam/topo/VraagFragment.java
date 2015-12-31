package com.topoteam.topo;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class VraagFragment extends Fragment implements QuestionFragment{
    QuestionListener questionListener; // connectie met activity
    LinearLayout l_answercontainer; // container voor antwoordinput
    LinearLayout.LayoutParams l_layout_params; // layoutparams
    ImageView kaartView; // kaart
    TextView vraagTextView;
    DBElement vraagElement;
    DBElement antwoordElement;
    List<DBElement> distractorElementen;
    boolean hintShown = false; boolean showHint = false; // hintshown

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vraag, container, false);

        l_answercontainer = (LinearLayout)v.findViewById(R.id.ll_ButtonContainer); // zet de referentie van de answercontainer
        l_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f); // set layoutparams

        // set de tekst van de vraag
        vraagTextView = (TextView) v.findViewById(R.id.textview_Vraag);
        vraagTextView.setText(questionListener.getVraag());

        // set de kaart
        kaartView = (ImageView) v.findViewById(R.id.kaart);
        kaartView.setImageBitmap(BitmapConverter.decodeSampledBitmapFromResource(getResources(), questionListener.getKaart(), 300, 300));

        vraagElement = questionListener.getVraagElement();
        antwoordElement = questionListener.getAntwoordElement();
        distractorElementen = questionListener.getDistractorElementen();

        updateUserInterface();

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            questionListener = (QuestionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement QuestionListener");
        }
    }

    public void updateUserInterface(){
        Bitmap bitmap = ((BitmapDrawable)kaartView.getDrawable()).getBitmap();
        Canvas c = new Canvas(bitmap);

        if (questionListener.isShowVraagLocatie()){
            drawElement(c, vraagElement.getLocatieX(), vraagElement.getLocatieY());
            if (questionListener.isShowName()){
                drawText(c, vraagElement.getLocatieX()+20, vraagElement.getLocatieY()+20, vraagElement.getNaam());
            }
        }

        if (questionListener.isShowAntwoordLocatie()){
            drawElement(c, antwoordElement.getLocatieX(), antwoordElement.getLocatieY());
            if (questionListener.isShowName()){
                drawText(c, antwoordElement.getLocatieX() + 20, antwoordElement.getLocatieY() + 20, antwoordElement.getNaam());
            }
        }

        if(showHint) {
            c.drawRect(10, 10, 30, 30, new Paint());
        }

        kaartView.setImageBitmap(bitmap);
    }

    public void onShowHint(){
        hintShown = true; // hint is laten zien
        showHint = !showHint; // laat het zien als het nog niet is laten zien, en andersom
        updateUserInterface(); //update de interface met de nieuwe waarde van showhint
    }

    protected void drawElement(Canvas c, int x, int y){
        c.drawRect(x - 5, y - 5, x + 5, y + 5, new Paint());
    }

    protected void drawText(Canvas c, int x, int y, String text){
        c.drawText(text, x, y, new Paint());
    }
}
