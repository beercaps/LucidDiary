package com.luciddreamfactory.luciddiary.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.luciddreamfactory.luciddiary.databases.LucidDiaryDbHelper;
import com.luciddreamfactory.luciddiary.model.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinwetzel on 03.09.16.
 */
public class TagDAO {

    private static final String TAG = "TagsDAO";

    private SQLiteDatabase database;
    private LucidDiaryDbHelper lucidDiaryDbHelper;

    private String[] columns = {
            LucidDiaryDbHelper.C_TAG_ID,
            LucidDiaryDbHelper.C_TAG_NAME
    };

    public TagDAO(Context context) {
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

    public Tag createTag(String tagName) {
        long insertId;

        ContentValues values = new ContentValues();
        values.put(LucidDiaryDbHelper.C_TAG_NAME, tagName);

        insertId = database.insert(LucidDiaryDbHelper.T_TAG, null, values);
        Log.d(TAG, "createTag Tag created");

        Cursor cursor = database.query(LucidDiaryDbHelper.T_TAG, columns, LucidDiaryDbHelper.C_TAG_ID+"="+insertId, null, null, null ,null);
        cursor.moveToFirst();
        Tag tag = cursorToTag(cursor);
        cursor.close();

        Log.d(TAG, "createTag: "+ tag.toString());

        return tag;
    }

    public Tag createTag(Tag tag) {
        long insertID;
        ContentValues values = new ContentValues();

        values.put(LucidDiaryDbHelper.C_TAG_NAME, tag.getTagName());

        insertID = database.insert(LucidDiaryDbHelper.T_TAG, null, values);

        Cursor cursor = database.query(LucidDiaryDbHelper.T_TAG, columns, LucidDiaryDbHelper.C_TAG_ID+"="+ insertID, null, null, null, null);
        cursor.moveToFirst();
        Tag insertedTag = cursorToTag(cursor);
        cursor.close();;
        Log.d(TAG, "createTag: "+tag.toString());

        return insertedTag;
    }

    public List<Tag> createTag(List<Tag> tagList) {
        ArrayList<Long> insertIDList = new ArrayList<>();
        ContentValues values = new ContentValues();
        ArrayList<Tag> insertedTagList = new ArrayList<>();
        String inOperator;

        for (Tag tag: tagList) {
            values.put(LucidDiaryDbHelper.C_TAG_NAME,tag.getTagName());
            insertIDList.add(database.insert(LucidDiaryDbHelper.T_TAG, null, values));
            values.clear();
        }

            inOperator = getINparameterStringT(insertedTagList);

            Cursor cursor = database.query(LucidDiaryDbHelper.T_TAG, columns, LucidDiaryDbHelper.C_TAG_ID+" IN ("+ inOperator+")", null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
            insertedTagList.add(cursorToTag(cursor));
                cursor.moveToNext();
            }
        cursor.close();

        return insertedTagList;
    }

    private Tag cursorToTag(Cursor cursor) {
        int columnIndexID = cursor.getColumnIndex(LucidDiaryDbHelper.C_TAG_ID);
        int columnIndexTagName = cursor.getColumnIndex(LucidDiaryDbHelper.C_TAG_NAME);

        Tag tag = new Tag();
        tag.setTagID(cursor.getLong(columnIndexID));
        tag.setTagName(cursor.getString(columnIndexTagName));

        return tag;
    }

    public Tag searchTag(long tagID) {
        Tag tag = null;
        Cursor cursor = database.query(LucidDiaryDbHelper.T_TAG, columns, LucidDiaryDbHelper.C_TAG_ID+"="+tagID, null, null, null, null);
        if (cursor.moveToFirst()) {
            tag = cursorToTag(cursor);
            cursor.close();
            Log.d(TAG, "searchTag ID" + tag.getTagID() + " Inhalt " + tag.toString());
        } else {
            throw new IllegalArgumentException("Invalid TAG ID "+tagID);
        }

        return tag;
    }

    public List<Tag> searchTag(List<Long> tagIDList) {
        List<Tag> queryTagList = new ArrayList<>();
        String inOperator = getINparameterStringL(tagIDList);
        Cursor cursor = database.query(LucidDiaryDbHelper.T_TAG, columns, LucidDiaryDbHelper.C_TAG_ID+"IN("+inOperator+")", null, null, null, LucidDiaryDbHelper.C_TAG_NAME);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            queryTagList.add(cursorToTag(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return queryTagList;
    }

    public List<Tag> getAllTags() {
        List<Tag> tagList = new ArrayList<>();
        Cursor cursor = database.query(LucidDiaryDbHelper.T_TAG, columns, null, null, null, null, LucidDiaryDbHelper.C_TAG_NAME);
        cursor.moveToFirst();
        Tag tag;

        while (!cursor.isAfterLast()) {
            tag = cursorToTag(cursor);
            tagList.add(tag);
            Log.d(TAG, "getAllTags ID "+tag.getTagID()+" Inhalt "+tag.toString());
            cursor.moveToNext();
        }
        cursor.close();
        return tagList;
    }

    private String getINparameterStringT(List<Tag> idList) {
        //prepare String for IN Parameter
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <idList.size() ; i++) {
            sb.append("'");
            sb.append(idList.get(i).getTagID());
            sb.append("'");
            //beim letzten durchlauf darf kein komma mehr angehängt werden
            if (i < idList.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    private String getINparameterStringL(List<Long> idList) {
        //prepare String for IN Parameter
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <idList.size() ; i++) {
            sb.append("'");
            sb.append(idList.get(i));
            sb.append("'");
            //beim letzten durchlauf darf kein komma mehr angehängt werden
            if (i < idList.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }



}
