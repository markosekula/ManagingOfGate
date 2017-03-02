package com.example.marko.managingofgate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

public class DataDB {
    DatabaseHelper databaseHelper;
    String name;

    public String getNameDB (Context context) {
        databaseHelper = new DatabaseHelper(context);

        try {
            databaseHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (databaseHelper.checkDataBase()){
            databaseHelper.openDataBase();
            SQLiteDatabase db = databaseHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT nameObject FROM gate_object", null);
            while(cursor.moveToNext()){
                name = cursor.getString(0);
            }

            databaseHelper.close();
            return name;

        } else {
            return "ERROR";
        }
    }
}
