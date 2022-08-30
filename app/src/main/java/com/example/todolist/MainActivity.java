package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes;
    private FloatingActionButton buttonAddNote;

    private final Database database = Database.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

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

    private void showNotes() {
        recyclerViewNotes.removeAllViews();
        for (Note note: database.getNotes()) {
            View view = getLayoutInflater().inflate(R.layout.note_item,
                    recyclerViewNotes,
                    false);
            recyclerViewNotes.addView(view);

            TextView textView = view.findViewById(R.id.textViewNote);
            textView.setText(note.getText());

            int idResColor;

            switch (note.getPriority()) {
                case 0:
                    idResColor = android.R.color.holo_green_light;
                    break;
                case 1:
                    idResColor = android.R.color.holo_orange_light;
                    break;
                default:
                    idResColor = android.R.color.holo_red_light;
            }
            int idColor = ContextCompat.getColor(this, idResColor);
            textView.setBackgroundColor(idColor);

            view.setOnClickListener(l -> {
                database.remove(note.getId());
                showNotes();
            });
        }
    }


    private void initViews() {
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        buttonAddNote = findViewById(R.id.buttonAddNote);
    }

    private void showNotes() {
        linearLayoutNotes.removeAllViews();
        for (Note note: database.getNotes()) {
            View view = getLayoutInflater().inflate(R.layout.note_item,
                    linearLayoutNotes,
                    false);
            linearLayoutNotes.addView(view);
            TextView textView = view.findViewById(R.id.textViewNote);
            textView.setText(note.getText());

            int colorResId;
            switch (note.getPriority()) {
                case 0:
                    colorResId = android.R.color.holo_green_light;
                    break;
                case 1:
                    colorResId = android.R.color.holo_orange_light;
                    break;
                default:
                    colorResId = android.R.color.holo_red_light;
                    break;
            }
            int color = ContextCompat.getColor(this, colorResId);
            textView.setBackgroundColor(color);
        }
    }
}