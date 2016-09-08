package com.luciddreamfactory.luciddiary.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.luciddreamfactory.luciddiary.databases.LucidDiaryDbHelper;
import com.luciddreamfactory.luciddiary.model.Dream;
import com.luciddreamfactory.luciddiary.model.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinwetzel on 04.09.16.
 */
public class DreamTagAssocDAO {

    private static final String TAG = "DreamTagAssocDAO";

    private SQLiteDatabase database;
    private LucidDiaryDbHelper lucidDiaryDbHelper;
    private TagDAO tagDAO;
    private DreamDAO dreamDAO;

    private String[] columns = {
            LucidDiaryDbHelper.C_DREAM_TAG_ASSOC_DREAM_ID,
            LucidDiaryDbHelper.C_DREAM_TAG_ASSOC_TAG_ID
    };

    public DreamTagAssocDAO(Context context, LucidDiaryDbHelper lucidDiaryDbHelper, TagDAO tagDAO, DreamDAO dreamDAO) {
        this.lucidDiaryDbHelper = lucidDiaryDbHelper;
        this.tagDAO = tagDAO;
        this.dreamDAO = dreamDAO;
    }

    public void open(){
        database = lucidDiaryDbHelper.getWritableDatabase();
        Log.d(TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close() {
        lucidDiaryDbHelper.close();
        Log.d(TAG, "Datenbank mit Hilfe des BooxDbHelpers geschlossen.");
    }

    public void createDreamTagAssoc(long dreamID, long tagID) {
        ContentValues values = new ContentValues();
        values.put(LucidDiaryDbHelper.C_DREAM_TAG_ASSOC_DREAM_ID, dreamID);
        values.put(LucidDiaryDbHelper.C_DREAM_TAG_ASSOC_TAG_ID, tagID);

        try {
            database.insert(LucidDiaryDbHelper.T_DREAM_TAG_ASSOC, null,values);
        } catch (Exception e) {
            Log.d(TAG, "createDreamTagAssoc: ERROR! inserting dreamTagAssoc");
            e.printStackTrace();
        }
        Log.d(TAG, "createDreamTagAssoc: created Assoc");
    }

    private long cursorToLong(Cursor cursor, String column) {
        int columnIndex = cursor.getColumnIndex(column);

        return cursor.getLong(columnIndex);
    }

    public List<Long> searchDreamLong(long tagID) {
        List<Long> queryDreamIDList = new ArrayList<>();
        Cursor cursor = database.query(LucidDiaryDbHelper.T_DREAM_TAG_ASSOC, columns, LucidDiaryDbHelper.C_DREAM_TAG_ASSOC_TAG_ID+"="+tagID, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            queryDreamIDList.add(cursorToLong(cursor, LucidDiaryDbHelper.C_DREAM_TAG_ASSOC_DREAM_ID));
            cursor.moveToNext();
        }
        cursor.close();

        return queryDreamIDList;
    }

    public List<Dream> searchDreamDream(long tagID) {
        List<Long> queryDreamIDList = new ArrayList<>();
        List<Dream> queryDreamDreamList;
        Cursor cursor = database.query(LucidDiaryDbHelper.T_DREAM_TAG_ASSOC, columns, LucidDiaryDbHelper.C_DREAM_TAG_ASSOC_TAG_ID+"="+tagID, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            queryDreamIDList.add(cursorToLong(cursor, LucidDiaryDbHelper.C_DREAM_TAG_ASSOC_DREAM_ID));
            cursor.moveToNext();
        }
        cursor.close();

        dreamDAO.open();
        queryDreamDreamList = dreamDAO.searchDreams(queryDreamIDList);
        dreamDAO.close();

        return queryDreamDreamList;
    }

    public List<Long> searchTagLong(long dreamID) {
        List<Long> queryTagIDList = new ArrayList<>();
        Cursor cursor = database.query(LucidDiaryDbHelper.T_DREAM_TAG_ASSOC, columns, LucidDiaryDbHelper.C_DREAM_TAG_ASSOC_TAG_ID+"="+dreamID, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            queryTagIDList.add(cursorToLong(cursor, LucidDiaryDbHelper.C_DREAM_TAG_ASSOC_TAG_ID));
            cursor.moveToNext();
        }
        cursor.close();
        Log.d(TAG, "searchTagLong return List<Long> "+queryTagIDList.toString());
        return queryTagIDList;
    }

    public List<Tag> searchTagTag(long dreamID) {
        List<Long> queryTagIDList = new ArrayList<>();
        List<Tag> queryTagTagList;

        Cursor cursor = database.query(LucidDiaryDbHelper.T_DREAM_TAG_ASSOC, columns, LucidDiaryDbHelper.C_DREAM_TAG_ASSOC_DREAM_ID+"="+dreamID, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            queryTagIDList.add(cursorToLong(cursor, LucidDiaryDbHelper.C_DREAM_TAG_ASSOC_TAG_ID));
            cursor.moveToNext();
        }
        cursor.close();

        Log.d(TAG, "searchTagTag: query list<Tag> from tagDAO");
        tagDAO.open();
        queryTagTagList = tagDAO.searchTag(queryTagIDList);
        tagDAO.close();
        Log.d(TAG, "searchTagTag: return List<Tag> "+ queryTagTagList.toString());
        return queryTagTagList;

    }

}
