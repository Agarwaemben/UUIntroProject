package com.topoteam.topo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

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

    public ArrayList<DBElement> getTopodata(String TABLE, Integer SELECTIE) {
        ArrayList<DBElement> values = new ArrayList<DBElement>();
        String query = "SELECT * from"+ TABLE + " WHERE Selectie" + SELECTIE;
        Cursor cursor = mDataBase.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do {
                values.add(new DBElement(cursor.getString(cursor
                        .getColumnIndex("naam")), cursor.getInt(cursor.getColumnIndex("locatieX")), cursor.getInt(cursor.getColumnIndex("locatieY")),cursor.getString(cursor
                        .getColumnIndex("provincie")),cursor.getString(cursor
                        .getColumnIndex("land")),cursor.getString(cursor
                        .getColumnIndex("continent")),cursor.getInt(cursor
                        .getColumnIndex("hoofdstadvan"))));
            } while (cursor.moveToNext());

        }
        return values;
    }
}