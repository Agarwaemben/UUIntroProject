package com.topoteam.topo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;
import android.util.Log;

public class TopoHelper extends SQLiteOpenHelper
{
    private static String TAG = "DataBaseHelper"; // Tag voor logcat
    //locatie van de database op het apparaat
    private static String DB_PATH = "";
    private static String DB_NAME ="TopoBase.db";// Database naam
    private SQLiteDatabase mDataBase;
    private final Context mContext;
    private Cursor cursor;

    private List<VraagGenerator> vraagGenerators = new ArrayList<>(Arrays.asList(
            new MCVNederland(), new AVNederland(), new OVNederland()/*,new MCVHoofdstadNederland(), new AVHoofdstadNederland(), new OVHoofdstadNederland(),
            new MCVEuropa(), new MCVHoofdstadEuropa(), new AVEuropa(), new AVHoofdstadEuropa(), new OVEuropa(), new OVHoofdstadEuropa(),
            new MCVWereld(), new MCVHoofdstadWereld(), new AVWereld(), new AVHoofdstadWereld(), new OVWereld(), new OVHoofdstadWereld()*/)
    );

    public TopoHelper(Context context)
    {
        super(context, DB_NAME, null, 1);
        //path aanpassen aan android versie (gaf problemen met bepaalde versies van Android)
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = "/data/data/com.topoteam.topo/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.mContext = context;

        try
        {
            createDataBase();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        openDataBase();
    }

    public void createDataBase() throws IOException
    {
        //Als er geen database is --> lege database aanmaken en de inhoud van TopoBase.db hierin kopieren (uit de assets folder).

        boolean mDataBaseExist = checkDataBase();
        if(!mDataBaseExist)
        {
            this.getReadableDatabase();
            this.close();
            try
            {
                copyDataBase();
                Log.e(TAG, "createDatabase database created");
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    //checken of de database bestaat op /data/data/com.topoteam.topo/databases/TopoBase.db
    private boolean checkDataBase()
    {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    //methode om de database te kopieren
    private void copyDataBase() throws IOException
    {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer))>0)
        {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    //Methode om de database te openen zodat er mee gewerkt kan worden
    public boolean openDataBase() throws SQLException
    {
        String mPath = DB_PATH + DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    @Override
    public synchronized void close()
    {
        if(mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // methode om een lijst met geselecteerde elementen uit de database te returnen
    public ArrayList<DBElement> getTopodata(String TABLE, String Soort) {
        try
        {
            copyDataBase();
            Log.e(TAG, "createDatabase database created");
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        // creer lege lijst met elementen
        ArrayList<DBElement> values = new ArrayList<DBElement>();
        String query = "SELECT * FROM "+ TABLE + " WHERE Soort = '" + Soort + "'"; // creer query

        cursor = mDataBase.rawQuery(query, null); // voer de query uit en sla het op in een cursor

        int cindex_Plaats, cindex_X, cindex_Y, cindex_Provincie, cindex_Land, cindex_Hoofdstad, cindex_Type; // indexes voor columns
        cursor.moveToFirst(); // ga naar de eerste rij

        // krijg alle indexes van de columns
        cindex_Plaats = cursor.getColumnIndex("Plaats");
        cindex_Provincie = cursor.getColumnIndex("Provincie");
        cindex_Hoofdstad = cursor.getColumnIndex("Hoofdstad");
        cindex_Type = cursor.getColumnIndex("Soort");
        cindex_X = cursor.getColumnIndex("x");
        cindex_Y = cursor.getColumnIndex("y");

        // main loop
        // loop over alle entries
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            // creer een nieuw element met de informatie uit 1 entry en voeg het toe aan de lijst
            values.add(new DBElement(cursor.getString(cindex_Plaats), cursor.getInt(cindex_X), cursor.getInt(cindex_Y), cursor.getString(cindex_Provincie),
                    "Nederland" , cursor.getInt(cindex_Hoofdstad), cursor.getString(cindex_Type)));
        }

        // return de lijst
        return values;
    }

    // functie om een volledige vragenlijst te genereren
    public List<Vraag> generateQuestionList(List<String> soort, List<String> soort_Vraag,String table){
        // creer vragenlijst
        List<Vraag> questionList = new ArrayList<>();

        // creer elementen lijst
        List<DBElement> elements = new ArrayList<>();

        // initialiseer randomiser
        Random r = new Random();
        int kaart = R.drawable.nederland;

        // loop door de geselecteerde soorten
        for(int i = 0; i < soort.size(); i++){
            // get de elementenlijst voor die soort
            List<DBElement> rawinfo = getTopodata(table, soort.get(i));

            // voeg de elementen toe aan de volledige elementenlijst
            for(DBElement item : rawinfo){
                elements.add(item);
            }
        }

        // voor elk element in de elementenlijst
        for(DBElement element : elements){
            // creer lijst voor mogelijke vraaggenerators
            List<VraagGenerator> mogelijkeVragen = new ArrayList<>();
            // creer lijst voor distractorelementen
            List<DBElement> distractorElements = new ArrayList<>();

            // selecteeer 3 distractorelementen
            int n = 0;
            while(n < 3){
                DBElement e = getRandomElement(elements);
                if(!e.getNaam().equals(element.getNaam()) && notDistractor(distractorElements, e)){
                    distractorElements.add(e);
                    n++;
                }
            }

            // loop door alle vraaggenerators
            for(VraagGenerator v:vraagGenerators) {
                // check voor geschiktheid voor de geselecteerde opties
                if (v.AllowsType(mContext, element.getType()) && v.isVraagType(mContext, soort_Vraag)) {
                    // voeg vraaggenerator toe aan mogelijke vraaggenerators
                    mogelijkeVragen.add(v);
                }
            }

            // genereer een vraag met 1 van de mogelijke vragen
            // voeg de vraag toe aan de vragenlijst
            questionList.add(mogelijkeVragen.get(r.nextInt(mogelijkeVragen.size())).genereerVraag(element, distractorElements, kaart));
        }

        // shuffle de vragenlijst
        Collections.shuffle(questionList);

        // return de vragenlijst
        return questionList;
    }

    // methode om een random element uit een elementenlijst te returnen
    private DBElement getRandomElement(List<DBElement> elements){
        Random r = new Random();
        return elements.get(r.nextInt(elements.size()));
    }

    private boolean notDistractor(List<DBElement> distractorlijst, DBElement nieuw_element){
        for(DBElement e : distractorlijst){
            if(e.getNaam()==nieuw_element.getNaam()){
                return false;
            }
        }
        return true;
    }
}
