package com.example.psychologyconsultationfinalproject;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database version
    public static final int DATABASE_VERSION = 2;

    // Database name
    public static final String DATABASE_NAME = "psychology_consultation_db";

    // Table names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_CONSULTATIONS = "consultations";

    // Common column names
    public static final String KEY_ID = "id";

    // Users table column names
    public static final String KEY_USER_FULL_NAME = "full_name";
    public static final String KEY_USER_EMAIL = "email";
    public static final String KEY_USER_PASSWORD = "password";
    public static final String KEY_USER_BATCH = "batch";
    public static final String KEY_USER_GENDER = "gender";

    // Consultations table column names
    public static final String KEY_CONSULTATION_USER_ID = "user_id";
    public static final String KEY_CONSULTATION_SESSION = "session";
    public static final String KEY_CONSULTATION_DATE = "date";
    public static final String KEY_CONSULTATION_TOPIC = "topic";

    // Create Users table
    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS +
            "(" + KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_USER_FULL_NAME + " TEXT," +
            KEY_USER_EMAIL + " TEXT," +
            KEY_USER_PASSWORD + " TEXT," +
            KEY_USER_BATCH + " TEXT," +
            KEY_USER_GENDER + " TEXT" + ")";

    // Create Consultations table
    public static final String CREATE_TABLE_CONSULTATIONS = "CREATE TABLE " + TABLE_CONSULTATIONS +
            "(" + KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_CONSULTATION_USER_ID + " INTEGER," +
            KEY_CONSULTATION_SESSION + " TEXT," +
            KEY_CONSULTATION_DATE + " TEXT," +
            KEY_CONSULTATION_TOPIC + " TEXT," +
            "FOREIGN KEY(" + KEY_CONSULTATION_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + "))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_CONSULTATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONSULTATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);
    }
}
