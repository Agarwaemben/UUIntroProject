package com.topoteam.topo;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBElement extends Activity{
    private String naam;
    private int locatieX;
    private int locatieY;
    private String provincie;
    private String land;
    private String continent;
    private int hoofdstadvan;
    TopoHelper Helper = new TopoHelper(this);

    public String getNaam(){return naam;}
    public String getProvincie(){return provincie;}
    public String getLand(){return land;}
    public String getContinent(){return continent;}

    public int getLocatieX(){return locatieX;}
    public int getLocatieY(){return locatieY;}
    public String getHoofdstadVan(){
        switch (hoofdstadvan){
            case 0: return "";
            case 1: return land;
            case 2: return provincie;
        }

        return "";
    }

    public int getTypeHoofdstadVan(){
        return hoofdstadvan;
    }

    public DBElement(String naam, int locatieX, int locatieY, String provincie, String land, String continent, int hoofdstadvan){
        this.naam = naam;
        this.locatieX = locatieX;
        this.locatieY = locatieY;
        this.provincie = provincie;
        this.land = land;
        this.continent = continent;
        this.hoofdstadvan = hoofdstadvan;
    }

    public DBElement(String DBResult){
        // construeer element uit 1 entry van de database
    }


}
