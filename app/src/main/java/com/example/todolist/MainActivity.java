package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes;
    private FloatingActionButton buttonAddNote;

    private NotesAdapter notesAdapter;

    private final Database database = Database.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        notesAdapter = new NotesAdapter();
        recyclerViewNotes.setAdapter(notesAdapter);
        notesAdapter.setOnNoteClickListener(note -> {
        });

        buttonAddNote.setOnClickListener(l -> {
            Intent intent = AddNoteActivity.newIntent(this);
            startActivity(intent);
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        0,
                        ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Note note = database.getNotes().get(position);
                database.remove(note.getId());
                showNotes();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);
    }


    @Override
    protected void onResume() {
        super.onResume();
        showNotes();
    }

    private void showNotes() {
        notesAdapter.setNotes(database.getNotes());
    }


    private void initViews() {
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        buttonAddNote = findViewById(R.id.buttonAddNote);
    }

}