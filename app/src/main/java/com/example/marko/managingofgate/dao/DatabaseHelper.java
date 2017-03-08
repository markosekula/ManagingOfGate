package com.example.marko.managingofgate.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;
    private static String DB_PATH;
    private static final String DB_NAME = "gate.db";
    private SQLiteDatabase myDataBase;
    private final static int DB_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;

        DB_PATH = context.getFilesDir().getParentFile().getPath() + "/databases/";
        System.out.println("DB_PATH: " + DB_PATH);

    }

    public void createDataBase () throws IOException {
        boolean dbExist = checkDataBase();

        if(dbExist){
            Log.d("database", "exist");
        }else{
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error creating source database");
            }
        }
    }

    public boolean checkDataBase() {
        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch(SQLiteException e){
            System.out.println("Database doesn't exist");
        }

        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null;
    }

    public void copyDataBase() throws IOException {
        InputStream myInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException{
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("DATABASE" , "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d("DATABASE" , "oldVersion: " + oldVersion);
        Log.d("DATABASE" , "newVersion: " + newVersion);

        if (oldVersion < DB_VERSION) {

            String ALTER_TABLE1 = "ALTER TABLE gate_object ADD COLUMN numberObject int";

            sqLiteDatabase.execSQL(ALTER_TABLE1);
        }
    }

}
