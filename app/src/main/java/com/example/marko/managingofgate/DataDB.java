package com.example.marko.managingofgate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.io.IOException;
import java.util.ArrayList;

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

            String query =  " SELECT nameObject FROM gate_object";

            Cursor cursor = db.rawQuery(query, null);
            System.out.println("cursor: " + cursor.getCount());

            while(cursor.moveToNext()){
                name = cursor.getString(0);
            }

            cursor.close();
            databaseHelper.close();


            System.out.println("name: " + name);
            return name;

        } else {
            return "ERROR";
        }

    }

    public ArrayList<GateObject> getExistObject (Context context) {
        databaseHelper = new DatabaseHelper(context);

        ArrayList<GateObject> arrayObject;

        try {
            databaseHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (databaseHelper.checkDataBase()){
            databaseHelper.openDataBase();
            SQLiteDatabase db = databaseHelper.getReadableDatabase();

            String query =  " SELECT id, nameObject, phoneNumber, isFill FROM gate_object WHERE isFill = 'true' ";

            Cursor cursor = db.rawQuery(query, null);

            arrayObject = new ArrayList<>();
            cursor.moveToFirst();

            GateObject gateObject = new GateObject();

            for (int i = 0; i <  cursor.getCount(); i++) {
                gateObject.setId(cursor.getInt(0));
                gateObject.setNameObject(cursor.getString(1));
                gateObject.setPhoneNumber(cursor.getString(2));

                boolean value = Boolean.parseBoolean(cursor.getString(3));
                gateObject.setIsFill(value);

                arrayObject.add(gateObject);

                gateObject = new GateObject();
                cursor.moveToNext();
            }

            cursor.close();
            db.close();

            return arrayObject;

        }
        else {
            return null;
        }
    }

    public ArrayList<GateObject> getAllObjects (Context context) {
        databaseHelper = new DatabaseHelper(context);

        ArrayList<GateObject> arrayObject;

        try {
            databaseHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (databaseHelper.checkDataBase()){
            databaseHelper.openDataBase();
            SQLiteDatabase db = databaseHelper.getReadableDatabase();

            String query =  " SELECT id, nameObject, phoneNumber, isFill FROM gate_object";

            Cursor cursor = db.rawQuery(query, null);

            arrayObject = new ArrayList<>();
            cursor.moveToFirst();

            GateObject gateObject = new GateObject();

            for (int i = 0; i <  cursor.getCount(); i++) {
                gateObject.setId(cursor.getInt(0));
                gateObject.setNameObject(cursor.getString(1));
                gateObject.setPhoneNumber(cursor.getString(2));

                boolean value = Boolean.parseBoolean(cursor.getString(3));
                gateObject.setIsFill(value);

                arrayObject.add(gateObject);

                gateObject = new GateObject();
                cursor.moveToNext();
            }

            cursor.close();
            db.close();

            return arrayObject;

        }
        else {
            return null;
        }
    }

    public void updateObject (Context context, String nameObject, String phone, Integer id) {
        databaseHelper = new DatabaseHelper(context);

        try {
            databaseHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (databaseHelper.checkDataBase()) {
            databaseHelper.openDataBase();
            SQLiteDatabase db = databaseHelper.getReadableDatabase();

            String query = "UPDATE gate_object SET nameObject = '"+ nameObject +"', phoneNumber = '"+ phone +"', isFill = 'true' WHERE id = "+ id +" ";
            Cursor cursor = db.rawQuery(query, null);


            String  sql = "UPDATE gate_object  SET " + " nameObject = '" + nameObject + "', " + "  phoneNumber = '"
                    + phone + "', " + "  isFill = '"
                     + nameObject + "'" + "  WHERE id = "
                    + id;


            cursor.close();
            db.close();

        }


    }

}
