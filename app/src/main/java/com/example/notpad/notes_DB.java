package com.example.notpad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class notes_DB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notepad_db";
    private static final String TABLE_NOTES = "notes";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";

    public notes_DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create database table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_NOTES_TABLE);
    }

    //update database if need
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    //add new note in database
    public void addNote(notes_model notes_model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, notes_model.getTitle());
        values.put(KEY_DESCRIPTION, notes_model.getDescription());
        db.insert(TABLE_NOTES, null, values);
        db.close();
    }

    //retrieve all data from database
    public List<notes_model> getAllNotes() {
        List<notes_model> notesList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NOTES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                notes_model note = new notes_model();
                note.id = cursor.getInt(0);
                note.title = cursor.getString(1);
                note.description = cursor.getString(2);
                notesList.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return notesList;
    }

    //update the current notes
    public void updateNote(notes_model note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getTitle());
        values.put(KEY_DESCRIPTION, note.getDescription());

        // Update the row
        db.update(TABLE_NOTES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }

    // delete note current notes
    public void deleteNote(int noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_ID + " = ?", new String[]{String.valueOf(noteId)});
        db.close();
    }

}
