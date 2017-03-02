package com.example.marko.managingofgate;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;

    // /data/data/com.example.marko.managingofgate/databases/

    private static String DB_PATH = "/data/data/com.example.marko.managingofgate/databases/";

    //Context.getFilesDir().getPath()

    private static final String DB_NAME = "gate.sql";

    private SQLiteDatabase myDataBase;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }

    public void createDataBase () throws IOException {
        boolean dbExist = checkDataBase();

        Log.d("database", "dbExist: " + dbExist);

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

    public boolean checkDataBase () {
        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;


            File file = new File(myPath);
            if (file.exists() && !file.isDirectory()) {
                Log.d("database", "!file.isDirectory: " +!file.isDirectory() + "");
                checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
            }

        }catch(SQLiteException e){
           // Log.d("database" , "does't exist yet.");
            Log.d("database", "checkDataBase: " + e.toString() + "");
        }

        if(checkDB != null){
            checkDB.close();
        }

        Log.d("database" , "true or false: " + checkDB);
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

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
