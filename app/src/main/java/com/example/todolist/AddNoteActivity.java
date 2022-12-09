package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextNote;
    private RadioButton radioButtonLow;
    private RadioButton radioButtonMedium;
    private Button buttonSave;

    private NoteDatabase noteDatabase;
    private Handler handler = new Handler(Looper.getMainLooper());



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initViews();
        
        buttonSave.setOnClickListener(l -> saveNote());
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddNoteActivity.class);
    }

    private void saveNote() {
        String text = editTextNote.getText().toString().trim();
        if(!text.isEmpty()) {
            int priority = getPriority();
            Note note = new Note(text, priority);
            Thread thread = new Thread(() -> {
                noteDatabase.notesDao().add(note);
                handler.post(this::finish);}
            );
            thread.start();
        } else {
            Toast.makeText(this,
                    R.string.error_fields_empty,
                    Toast.LENGTH_SHORT).show();
        }
     }

     private int getPriority() {
        if(radioButtonLow.isChecked()) {
            return 0;
        }
        if(radioButtonMedium.isChecked()) {
            return 1;
        }
        return 2;
     }
    
    private void initViews() {
        editTextNote = findViewById(R.id.editTextNote);
        radioButtonLow = findViewById(R.id.radioButtonLow);
        radioButtonMedium = findViewById(R.id.radioButtonMedium);
        buttonSave = findViewById(R.id.buttonSave);
        noteDatabase = NoteDatabase.getInstance(getApplication());
    }
}