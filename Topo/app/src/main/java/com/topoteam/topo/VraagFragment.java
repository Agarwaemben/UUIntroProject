package com.topoteam.topo;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
    DBElement element;
    List<DBElement> distractorElementen;
    int originalWidth, originalHeight, scaledWidth, scaledHeight, imageBoundsLeft, imageBoundsRight;
    double scaleFactorX, scaleFactorY;
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
        Bitmap b = BitmapConverter.decodeSampledBitmapFromResource(getResources(), questionListener.getKaart(), 300, 300);
        kaartView.setImageBitmap(b);

        // bereken de scalefactors van de imageview
        setScaleFactors(b);

        // get de elementen
        element = questionListener.getElement();
        distractorElementen = questionListener.getDistractorElementen();

        // teken de user interface
        updateUserInterface();

        return v;
    }

    // implementeer de connectie met de gameactivity
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


    // functie die wordt uitgevoerd als het fragment verwijderd wordt
    @Override
    public void onDestroyView(){
        super.onDestroyView();

        // ruim de bitmap op om geheugen vrij te maken
        Bitmap bitmap = ((BitmapDrawable)kaartView.getDrawable()).getBitmap();
        bitmap.recycle();
    }

    // teken de userinterface
    public void updateUserInterface(){
        // krijg de bitmap van de kaart construeer een canvas
        Bitmap bitmap = ((BitmapDrawable)kaartView.getDrawable()).getBitmap();
        Canvas c = new Canvas(bitmap);

        // teken de elementen die nodig zijn
        if (questionListener.isShowElementLocation()){
            drawElement(c, element.getLocatieX(), element.getLocatieY());
            if (questionListener.isShowName()){
                drawText(c, element.getLocatieX()+20, element.getLocatieY()+20, element.getNaam());
            }
        }

        // laat de hint zien als dat nodig is
        if(showHint) {
            //
        }

        // set de bewerkte bitmap
        kaartView.setImageBitmap(bitmap);
    }

    public void onShowHint(){
        hintShown = true; // hint is laten zien
        showHint = !showHint; // laat het zien als het nog niet is laten zien, en andersom
        updateUserInterface(); //update de interface met de nieuwe waarde van showhint
    }

    // functie om een element te tekenen
    protected void drawElement(Canvas c, int x, int y){
        // bereken de geschaalde positie van x en y
        int scaledX = (int)(x/scaleFactorX);
        int scaledY = (int)(y/scaleFactorY);

        // teken het element
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        c.drawRect(scaledX - 10, scaledY -10 , scaledX + 15, scaledY + 15,p);
    }

    // functie om een string text te tekenen
    protected void drawText(Canvas c, int x, int y, String text){
        // bereken de geschaalde positie van x en y
        int scaledX = (int)(x/scaleFactorX);
        int scaledY = (int)(y/scaleFactorY);

        // teken de tekst
        c.drawText(text, scaledX, scaledY, new Paint());
    }

    // bereken de originele breedte van de kaart
    public int getOriginalWidth(){
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inTargetDensity = DisplayMetrics.DENSITY_DEFAULT;
        Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                questionListener.getKaart(), o);
        return bmp.getWidth();
    }

    // bereken de originele hoogte van de kaart
    public int getOriginalHeight(){
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inTargetDensity = DisplayMetrics.DENSITY_DEFAULT;
        Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                questionListener.getKaart(), o);
        return bmp.getHeight();
    }

    // bereken de scalefactors van de bitmap
    protected void setScaleFactors(Bitmap scaledBitmap) {
        originalWidth = getOriginalWidth();
        originalHeight = getOriginalHeight();

        int scaledHeight = scaledBitmap.getHeight();
        int scaledWidth = scaledBitmap.getWidth();

        scaleFactorX = (double) originalWidth / scaledWidth;
        scaleFactorY = (double) originalHeight / scaledHeight;
    }
}
