package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private FloatingActionButton buttonAddNote;
    private RecyclerView recyclerViewNotes;

    private NotesAdapter notesAdapter;

    private NoteDatabase noteDatabase;
    private static boolean RVACreationLimit = true;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();



        notesAdapter = new NotesAdapter();
        recyclerViewNotes.setAdapter(notesAdapter);

        noteDatabase = NoteDatabase.getInstance(getApplication());
//                notesAdapter.setOnNoteClickListener(n -> {});


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Thread thread = new Thread(() -> {
                            Note note = noteDatabase.notesDao().getNotes().get(position);
                            noteDatabase.notesDao().remove(note.getId());
                            handler.post(MainActivity.this::showNotes);
                        });
                        thread.start();
                    }
                });
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);


        buttonAddNote.setOnClickListener(l -> {
            Intent intent = AddNoteActivity.newIntent(this);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showNotes();
    }

    private void initViews() {
        buttonAddNote = findViewById(R.id.buttonAddNote);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
    }
    private void showNotes() {
        Thread thread = new Thread(() -> {
            List<Note> noteList = noteDatabase.notesDao().getNotes();
            handler.post(() -> notesAdapter.setNotes(noteList));
        });
        thread.start();
    }
}
