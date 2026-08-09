package com.example.logbook2023_3_60_057;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventDB extends SQLiteOpenHelper {

    public EventDB(Context context) {
        super(context, "EventDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE events ("
                + "ID TEXT PRIMARY KEY,"
                + "name TEXT,"
                + "email TEXT,"
                + "phone TEXT,"
                + "dob TEXT,"
                + "present_address TEXT,"
                + "permanent_address TEXT"
                + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertEvent(String ID, String name, String email, String phone, String dob, String present, String permanent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID", ID);
        values.put("name", name);
        values.put("email", email);
        values.put("phone", phone);
        values.put("dob", dob);
        values.put("present_address", present);
        values.put("permanent_address", permanent);
        db.insert("events", null, values);
        db.close();
    }

    public void updateEvent(String id, String name, String email, String phone, String dob, String present, String permanent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        values.put("phone", phone);
        values.put("dob", dob);
        values.put("present_address", present);
        values.put("permanent_address", permanent);
        db.update("events", values, "ID=?", new String[]{id});
        db.close();
    }

    public void deleteEvent(String ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("events", "ID=?", new String[]{ID});
        db.close();
    }

    public Cursor selectEvents(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery(query, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
