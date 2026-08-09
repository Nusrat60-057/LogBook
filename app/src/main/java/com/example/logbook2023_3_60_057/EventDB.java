package com.example.logbook2023_3_60_057;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventDB extends SQLiteOpenHelper {

    public EventDB(Context context) {
        super(context, "EventDB.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Users table
        db.execSQL("CREATE TABLE users ("
                + "user_id TEXT PRIMARY KEY,"
                + "name TEXT,"
                + "email TEXT,"
                + "phone TEXT,"
                + "password TEXT"
                + ")");

        // Events table with USER_ID foreign key
        db.execSQL("CREATE TABLE events ("
                + "ID TEXT PRIMARY KEY,"
                + "USER_ID TEXT,"
                + "name TEXT,"
                + "email TEXT,"
                + "phone TEXT,"
                + "dob TEXT,"
                + "present_address TEXT,"
                + "permanent_address TEXT,"
                + "FOREIGN KEY(USER_ID) REFERENCES users(user_id)"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS events");
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public void insertEvent(String ID, String userID, String name, String email, String phone, String dob, String present, String permanent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID", ID);
        values.put("USER_ID", userID);
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

    public void insertUser(String userId, String name, String email, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("name", name);
        values.put("email", email);
        values.put("phone", phone);
        values.put("password", password);
        db.insert("users", null, values);
        db.close();
    }

    public boolean isUserValid(String userId, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE user_id=? AND password=?", new String[]{userId, password});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }

    public boolean userExists(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE user_id=?", new String[]{userId});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Cursor selectEvents(String query, String[] args) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, args);
    }
}
