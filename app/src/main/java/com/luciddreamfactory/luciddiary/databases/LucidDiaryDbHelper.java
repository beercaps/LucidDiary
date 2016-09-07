package com.luciddreamfactory.luciddiary.databases;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kevinwetzel on 30.08.16.
 */
public class LucidDiaryDbHelper extends SQLiteOpenHelper{

    private static final String TAG = "LucidDiaryDbHelper";

    public static String DB_NAME = "myDiary";
    public static final int DB_VERSION = 8;


    //----------------------------------------------------------------------------------------//
    //                                          Dream                                         //
    //----------------------------------------------------------------------------------------//
    public static final String T_DREAM = "t_dream"; //primary key
    public static final String C_DREAM_ID = "_id";
    public static final String C_DREAM_TITLE = "c_title";
    public static final String C_DREAM_CONTENT = "c_content";
    public static final String C_DREAM_DATE = "c_date";
    public static final String C_DREAM_WITHOUT_DATE = "c_without_date";
    public static final String C_DREAM_WITHOUT_TIME = "c_without_time";
    public static final String C_DREAM_COLOR = "c_color";

    //----------------------------------------------------------------------------------------//
    //                                          Tag                                           //
    //----------------------------------------------------------------------------------------//
    public static final String T_TAG = "t_tag";
    public static final String C_TAG_ID = "_id";
    public static final String C_TAG_NAME = "t_tag_name";

    //----------------------------------------------------------------------------------------//
    //                                          Audio                                         //
    //----------------------------------------------------------------------------------------//
    public static final String T_AUDIO = "t_audio";
    public static final String C_AUDIO_ID = "_id";
    public static final String C_AUDIO_DREAM_ID = "dream_id";               //foreign key
    public static final String C_AUDIO_LENGTH = "c_length";
    public static final String C_AUDIO_TITLE = "c_audio_title";
    public static final String C_AUDIO_PATH = "c_audio_path";

    //----------------------------------------------------------------------------------------//
    //                                          Drawing                                       //
    //----------------------------------------------------------------------------------------//
    public static final String T_DRAWING = "t_drawing";
    public static final String C_DRAWING_ID = "_id";
    public static final String C_DRAWING_DREAM_ID = "dream_id";
    //TODO implement rest

    //----------------------------------------------------------------------------------------//
    //                                   Dream-Tag-Association                                //
    //----------------------------------------------------------------------------------------//
    public static final String T_DREAM_TAG_ASSOC = "t_dream_tag_association";
    public static final String C_DREAM_TAG_ASSOC_DREAM_ID = "c_dream_id";   //foreign primary key
    public static final String C_DREAM_TAG_ASSOC_TAG_ID = "c_tag_id";       //foreign primary key

    public static final String SQL_CREATE_DREAM =
            "CREATE TABLE "+ T_DREAM +
               "(" +
                    C_DREAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    C_DREAM_TITLE + " TEXT, "+
                    C_DREAM_CONTENT+ " TEXT, "+
                    C_DREAM_DATE + " INTEGER, "+
                    C_DREAM_WITHOUT_DATE + " INTEGER, "+
                    C_DREAM_WITHOUT_TIME + " INTEGER, "+
                    C_DREAM_COLOR + " INTEGER "+
                    " );";

    public static final String SQL_CREATE_TAG =
            "CREATE TABLE "+ T_TAG+
                    "("+
                        C_TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        C_TAG_NAME + " TEXT "+
                        " );";

    public static final String SQL_CREATE_AUDIO =
            "CREATE TABLE "+ T_AUDIO+
                    "(" +
                    C_AUDIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    C_AUDIO_LENGTH + " INTEGER, "+
                    C_AUDIO_TITLE + " TEXT,"+
                    C_AUDIO_PATH + " TEXT "+
                    "FOREIGN KEY( "+C_AUDIO_DREAM_ID+" ) REFERENCES "+ T_DREAM+" ( "+C_DREAM_ID+" )"+
                    ");";

    //TODO implement rest
    public static final String SQL_CREATE_DRAWING =
            "CREATE TABLE "+ T_DRAWING+
                    "(" +
                    C_DRAWING_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT "+
                    "FOREIGN KEY( "+C_DRAWING_ID+" ) REFERENCES "+ T_DREAM+" ( "+C_DREAM_ID+" )"+
                    ");";

    public static final String SQL_CREATE_DREAM_TAG_ASSOCIATION =
            "CREATE TABLE "+ T_DREAM_TAG_ASSOC+
                    "( " +
                    C_DREAM_TAG_ASSOC_DREAM_ID +" INTEGER, "+
                    C_DREAM_TAG_ASSOC_TAG_ID+ " INTEGER, "+

                    "PRIMARY KEY( "+ C_DREAM_TAG_ASSOC_DREAM_ID+", "+
                                     C_DREAM_TAG_ASSOC_TAG_ID + "), "+
                    "FOREIGN KEY( "+ C_DREAM_TAG_ASSOC_DREAM_ID + " ) REFERENCES "+ T_DREAM + "("+C_DREAM_ID+"),"+
                    "FOREIGN KEY( "+ C_DREAM_TAG_ASSOC_TAG_ID + " ) REFERENCES "+ T_TAG + "("+C_TAG_ID+")"+
                    " );";



    public LucidDiaryDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(TAG, "LucidDiaryDbHelper hat die Datenbank "+getDatabaseName()+" erzeugt");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try{
            sqLiteDatabase.execSQL(SQL_CREATE_TAG);
            Log.d(TAG, "Die Datenbanktabellen werden mit SQL-Befehl: "+ SQL_CREATE_TAG + "angelegt");
          //  sqLiteDatabase.execSQL(SQL_CREATE_AUDIO);
          //  Log.d(TAG, "Die Datenbanktabellen werden mit SQL-Befehl: "+ SQL_CREATE_AUDIO + "angelegt");
          //  sqLiteDatabase.execSQL(SQL_CREATE_DRAWING);
          //  Log.d(TAG, "Die Datenbanktabellen werden mit SQL-Befehl: "+ SQL_CREATE_DRAWING + "angelegt");
            sqLiteDatabase.execSQL(SQL_CREATE_DREAM);
            Log.d(TAG, "Die Datenbanktabellen werden mit SQL-Befehl: "+ SQL_CREATE_DREAM + "angelegt");
            sqLiteDatabase.execSQL(SQL_CREATE_DREAM_TAG_ASSOCIATION);
            Log.d(TAG, "Die Datenbanktabellen werden mit SQL-Befehl: "+ SQL_CREATE_DREAM_TAG_ASSOCIATION + "angelegt");
        }catch (SQLException e){
            Log.e(TAG, "Fehler beim Anlegen der Tabellen: "+ e.getMessage() );
        }



        
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        try {
            Log.d(TAG, "onUpgrade: DROP TABLES");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + T_DREAM_TAG_ASSOC);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + T_DREAM);
          //  sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + T_DRAWING);
          //  sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + T_AUDIO);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + T_TAG);
            onCreate(sqLiteDatabase);
        }catch (SQLException e){
            e.printStackTrace();
        }


    }
}
