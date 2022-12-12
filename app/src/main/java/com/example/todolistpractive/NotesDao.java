package com.example.todolistpractive;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM NOTES")
    LiveData<List<Note>> getNotes();

    @Insert
    void add(Note note);

    @Query("DELETE FROM NOTES WHERE id = :id")
    void remove(int id);
}
