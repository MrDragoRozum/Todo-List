package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayoutNotes;
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
        linearLayoutNotes.removeAllViews();
        for (Note note: database.getNotes()) {
            View view = getLayoutInflater().inflate(R.layout.note_item, linearLayoutNotes, false);
            linearLayoutNotes.addView(view);

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
        }
    }


    private void initViews() {
        linearLayoutNotes = findViewById(R.id.linearLayoutNotes);
        buttonAddNote = findViewById(R.id.buttonAddNote);
    }
}