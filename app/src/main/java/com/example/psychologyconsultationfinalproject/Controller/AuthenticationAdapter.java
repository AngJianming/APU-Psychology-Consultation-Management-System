package com.example.psychologyconsultationfinalproject.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.psychologyconsultationfinalproject.DatabaseHelper;
import com.example.psychologyconsultationfinalproject.Model.UserModel;

public class AuthenticationAdapter {
    private DatabaseHelper dbHelper;

    public AuthenticationAdapter(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Register a new user
    public long registerUser(UserModel user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("full_name", user.getFullName());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        values.put("batch", user.getBatch());
        values.put("gender", user.getGender());

        // Inserting Row
        long userId = db.insert("users", null, values);
        db.close(); // Closing database connection
        return userId;
    }

    // Login user and return UserModel if successful
    public boolean loginUser(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {"id"};
        String selection = "email=? AND password=?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);
        boolean result = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return result;
    }

    // Get user data based on email
    public UserModel getUserDataByEmail(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {DatabaseHelper.KEY_ID, DatabaseHelper.KEY_USER_FULL_NAME, DatabaseHelper.KEY_USER_BATCH};
        String selection = DatabaseHelper.KEY_USER_EMAIL + "=?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null, null);

        UserModel user = null;

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DatabaseHelper.KEY_ID);
            int fullNameIndex = cursor.getColumnIndex(DatabaseHelper.KEY_USER_FULL_NAME);
            int batchIndex = cursor.getColumnIndex(DatabaseHelper.KEY_USER_BATCH);

            // Check if column indices are valid
            if (idIndex >= 0 && fullNameIndex >= 0 && batchIndex >= 0) {
                user = new UserModel();
                user.setId(cursor.getInt(idIndex));
                user.setFullName(cursor.getString(fullNameIndex));
                user.setBatch(cursor.getString(batchIndex));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return user;
    }

    // Get user data based on user ID
    public UserModel getUserData(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {DatabaseHelper.KEY_ID, DatabaseHelper.KEY_USER_FULL_NAME, DatabaseHelper.KEY_USER_EMAIL, DatabaseHelper.KEY_USER_BATCH};
        String selection = DatabaseHelper.KEY_ID + "=?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null, null);

        UserModel user = null;

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DatabaseHelper.KEY_ID);
            int fullNameIndex = cursor.getColumnIndex(DatabaseHelper.KEY_USER_FULL_NAME);
            int emailIndex = cursor.getColumnIndex(DatabaseHelper.KEY_USER_EMAIL); // Add this line
            int batchIndex = cursor.getColumnIndex(DatabaseHelper.KEY_USER_BATCH);

            // Check if column indices are valid
            if (idIndex >= 0 && fullNameIndex >= 0 && batchIndex >= 0 && emailIndex >= 0) {
                user = new UserModel();
                user.setId(cursor.getInt(idIndex));
                user.setFullName(cursor.getString(fullNameIndex));
                user.setEmail(cursor.getString(emailIndex)); // Fix typo here
                user.setBatch(cursor.getString(batchIndex));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return user;
    }

}
