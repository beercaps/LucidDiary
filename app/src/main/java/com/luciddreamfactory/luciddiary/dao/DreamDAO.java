package com.luciddreamfactory.luciddiary.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.luciddreamfactory.luciddiary.databases.LucidDiaryDbHelper;
import com.luciddreamfactory.luciddiary.model.Dream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by kevinwetzel on 02.09.16.
 */
public class DreamDAO {

    private static final String TAG = "DreamDAO";

    private SQLiteDatabase database;
    private LucidDiaryDbHelper lucidDiaryDbHelper;

    private String[] columns = {
    LucidDiaryDbHelper.C_DREAM_ID,
    LucidDiaryDbHelper.C_DREAM_TITLE,
    LucidDiaryDbHelper.C_DREAM_CONTENT,
    LucidDiaryDbHelper.C_DREAM_DATE,
    LucidDiaryDbHelper.C_DREAM_WITHOUT_DATE,
    LucidDiaryDbHelper.C_DREAM_WITHOUT_TIME,
    LucidDiaryDbHelper.C_DREAM_COLOR,
    };


    public DreamDAO(Context context) {
        this.lucidDiaryDbHelper = new LucidDiaryDbHelper(context);
    }

    public void open(){
        database = lucidDiaryDbHelper.getWritableDatabase();
        Log.d(TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close() {
        lucidDiaryDbHelper.close();
        Log.d(TAG, "Datenbank mit Hilfe des BooxDbHelpers geschlossen.");
    }

    public Dream createDream(String title, String content, Date date, boolean withoutDate, boolean withoutTime, long dreamDrawingID, int color) {
        long insertId;

        ContentValues values = new ContentValues();
                values.put(LucidDiaryDbHelper.C_DREAM_TITLE,title);
                values.put(LucidDiaryDbHelper.C_DREAM_CONTENT,content);
                values.put(LucidDiaryDbHelper.C_DREAM_DATE,date.getTime());
                values.put(LucidDiaryDbHelper.C_DREAM_WITHOUT_DATE, (withoutDate)?1:0); //convert true/false to 1/0
                values.put(LucidDiaryDbHelper.C_DREAM_WITHOUT_TIME, (withoutTime)?1:0); //convert true/false to 1/0
                values.put(LucidDiaryDbHelper.C_DREAM_COLOR,color);

                //values.put(LucidDiaryDbHelper.C_DREAM_AUDIO_ID,audioID);
                //values.put(LucidDiaryDbHelper.C_DREAM_DRAWING_ID,dreamDrawingID);

                insertId = database.insert(LucidDiaryDbHelper.T_DREAM, null, values);
                Log.d(TAG, "createDream: dream created");

        Cursor cursor = database.query(LucidDiaryDbHelper.T_DREAM, columns, LucidDiaryDbHelper.C_DREAM_ID +"="+insertId, null, null, null, null);
        cursor.moveToFirst();
        Dream dream = cursorToDream(cursor);
        cursor.close();

        Log.d(TAG, "createDream: "+dream.toString());

        return dream;

    }

    private Dream cursorToDream(Cursor cursor) {

        int columnIndexID = cursor.getColumnIndex(LucidDiaryDbHelper.C_DREAM_ID);
        int columnIndexTitle = cursor.getColumnIndex(LucidDiaryDbHelper.C_DREAM_TITLE);
        int columnIndexContent = cursor.getColumnIndex(LucidDiaryDbHelper.C_DREAM_CONTENT);
        int columnIndexDate = cursor.getColumnIndex(LucidDiaryDbHelper.C_DREAM_DATE);
        int columnIndexWithoutDate = cursor.getColumnIndex(LucidDiaryDbHelper.C_DREAM_WITHOUT_DATE);
        int columnIndexWithoutTime = cursor.getColumnIndex(LucidDiaryDbHelper.C_DREAM_WITHOUT_TIME);
        int columnIndexColor = cursor.getColumnIndex(LucidDiaryDbHelper.C_DREAM_COLOR);

        Dream dream = new Dream();
        dream.setDreamID(columnIndexID);
        dream.setTitle(cursor.getString(columnIndexTitle));
        dream.setContent(cursor.getString(columnIndexContent));
        cursor.getInt(columnIndexDate);
        dream.setDate(new Date(cursor.getLong(columnIndexDate)));
        dream.setWihtoutDate((cursor.getInt(columnIndexWithoutDate) == 1) ? true : false); //convert 1/0 to true/false
        dream.setWihtoutTime((cursor.getInt(columnIndexWithoutTime) == 1) ? true : false); //convert 1/0 to true/false
        dream.setColor(cursor.getInt(columnIndexColor));
        //dream.setAudio();  //TODO arraylist audio
        //dream.setDrawings(); //TODO arraylist drawing

        return dream;
    }

    public Dream createDream(Dream dream) {
        ContentValues values = new ContentValues();
        long insertID;

        values.put(LucidDiaryDbHelper.C_DREAM_TITLE,dream.getTitle());
        values.put(LucidDiaryDbHelper.C_DREAM_CONTENT,dream.getContent());
        values.put(LucidDiaryDbHelper.C_DREAM_DATE,dream.getDate().getTime());
        values.put(LucidDiaryDbHelper.C_DREAM_WITHOUT_DATE, (dream.isWihtoutDate()) ? 1 : 0);  //convert true/false to 1/0
        values.put(LucidDiaryDbHelper.C_DREAM_WITHOUT_TIME, (dream.isWihtoutTime()) ? 1 : 0);  //convert true/false to 1/0
        values.put(LucidDiaryDbHelper.C_DREAM_COLOR, dream.getColor());

        //TODO insert into AUdio/Drawing Table
       // values.put(LucidDiaryDbHelper.C_DREAM_AUDIO_ID,);
       // values.put(LucidDiaryDbHelper.C_DREAM_DRAWING_ID,);

       insertID =  database.insert(LucidDiaryDbHelper.T_DREAM, null, values);

        Cursor cursor = database.query(LucidDiaryDbHelper.T_DREAM, columns, LucidDiaryDbHelper.C_DREAM_ID + "="+ insertID, null, null, null, null);
        cursor.moveToFirst();
        Dream insertedDream = cursorToDream(cursor);
        cursor.close();
        Log.d(TAG, "createDream: "+ dream.toString());
        return  insertedDream;
    }

    public Dream searchDream(int dreamID) {
        Dream dream = null;
        Cursor cursor = database.query(LucidDiaryDbHelper.T_DREAM, columns, LucidDiaryDbHelper.C_DREAM_ID + "=" +dreamID, null, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            dream = cursorToDream(cursor);
            cursor.close();
            Log.d(TAG, "searchDream ID: "+ dream.getDreamID()+ "Inhalt"+ dream.toString());
        }

        return dream;
    }

    public List<Dream> getAllDreams() {
        List<Dream> dreamList = new ArrayList<>();
        Cursor cursor = database.query(LucidDiaryDbHelper.T_DREAM, columns, null, null, null, null , LucidDiaryDbHelper.C_DREAM_DATE);
        cursor.moveToFirst();
        Dream dream;

        while (!cursor.isAfterLast()) {
            dream = cursorToDream(cursor);
            dreamList.add(dream);
            Log.d(TAG, "getAllDreams ID: "+ dream.getDreamID() + " Inhalt "+ dream.toString());
            cursor.moveToNext();
        }
        cursor.close();
        return dreamList;
    }
}
