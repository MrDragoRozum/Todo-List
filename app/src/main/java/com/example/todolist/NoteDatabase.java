package com.example.todolist;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance = null;

    private final static String DB_NAME = "notes.db";

    public static NoteDatabase getInstance(Application aplApplication) {
        if(instance == null) {
            instance = Room.databaseBuilder(
                    aplApplication,
                    NoteDatabase.class,
                    DB_NAME
            ).build();
        }
        return instance;
    }
}
