package com.example.psychologyconsultationfinalproject.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.psychologyconsultationfinalproject.DatabaseHelper;
import com.example.psychologyconsultationfinalproject.Model.ConsultationModel;

import java.util.ArrayList;
import java.util.List;

public class ConsultationAdapter {

    private DatabaseHelper dbHelper;

    public ConsultationAdapter(Context context) {
        dbHelper = new DatabaseHelper(context);
    }


    public boolean isConsultationExists(String date, String session) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {DatabaseHelper.KEY_ID};

        String selection =
                DatabaseHelper.KEY_CONSULTATION_DATE + "=? AND " +
                        DatabaseHelper.KEY_CONSULTATION_SESSION + "=?";
        String[] selectionArgs = {date, session};

        Cursor cursor = db.query(DatabaseHelper.TABLE_CONSULTATIONS, columns, selection, selectionArgs, null, null, null, null);

        boolean exists = cursor != null && cursor.getCount() > 0;

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return exists;
    }


    // Create a new consultation for the logged-in user
    public long createConsultation(ConsultationModel consultation) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_CONSULTATION_USER_ID, consultation.getUserId());
        values.put(DatabaseHelper.KEY_CONSULTATION_SESSION, consultation.getSession());
        values.put(DatabaseHelper.KEY_CONSULTATION_DATE, consultation.getDate());
        values.put(DatabaseHelper.KEY_CONSULTATION_TOPIC, consultation.getTopic());

        // Inserting Row
        long consultationId = db.insert(DatabaseHelper.TABLE_CONSULTATIONS, null, values);
        db.close(); // Closing database connection

        return consultationId;
    }

    // Get all consultations for a specific user
    public List<ConsultationModel> getConsultationsByUserId(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        List<ConsultationModel> consultations = new ArrayList<>();

        String[] columns = {DatabaseHelper.KEY_ID, DatabaseHelper.KEY_CONSULTATION_USER_ID,
                DatabaseHelper.KEY_CONSULTATION_SESSION, DatabaseHelper.KEY_CONSULTATION_DATE,
                DatabaseHelper.KEY_CONSULTATION_TOPIC};

        String selection = DatabaseHelper.KEY_CONSULTATION_USER_ID + "=?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(DatabaseHelper.TABLE_CONSULTATIONS, columns, selection, selectionArgs, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Check if the column index is valid
                int idIndex = cursor.getColumnIndex(DatabaseHelper.KEY_ID);
                int userIdIndex = cursor.getColumnIndex(DatabaseHelper.KEY_CONSULTATION_USER_ID);
                int sessionIndex = cursor.getColumnIndex(DatabaseHelper.KEY_CONSULTATION_SESSION);
                int dateIndex = cursor.getColumnIndex(DatabaseHelper.KEY_CONSULTATION_DATE);
                int topicIndex = cursor.getColumnIndex(DatabaseHelper.KEY_CONSULTATION_TOPIC);

                if (idIndex >= 0 && userIdIndex >= 0 && sessionIndex >= 0 && dateIndex >= 0 && topicIndex >= 0) {
                    ConsultationModel consultation = new ConsultationModel();
                    consultation.setId(cursor.getInt(idIndex));
                    consultation.setUserId(cursor.getInt(userIdIndex));
                    consultation.setSession(cursor.getString(sessionIndex));
                    consultation.setDate(cursor.getString(dateIndex));
                    consultation.setTopic(cursor.getString(topicIndex));

                    consultations.add(consultation);
                }
            }

            cursor.close();
        }

        db.close();

        return consultations;
    }
}
