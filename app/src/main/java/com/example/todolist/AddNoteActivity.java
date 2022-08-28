package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextTextNote;
    private RadioButton radioButtonLow;
    private RadioButton radioButtonMedium;
    private Button buttonSave;

    private final Database database = Database.getInstance();

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
        String text = editTextTextNote.getText().toString().trim();
        if(!text.isEmpty()) {
            int priority = getPriority();
            int id = database.getNotes().size();
            Note note = new Note(id, text, priority);

            finish();
            database.add(note);
        } else {
            Toast.makeText(this,
                    R.string.warning,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private int getPriority() {
        int priority;

        if(radioButtonLow.isChecked()) {
            priority = 0;
        } else if(radioButtonMedium.isChecked()) {
            priority = 1;
        } else {
            priority = 2;
        }
        return priority;
    }

    private void initViews() {
        editTextTextNote = findViewById(R.id.editTextTextNote);
        radioButtonLow = findViewById(R.id.radioButtonLow);
        radioButtonMedium = findViewById(R.id.radioButtonMedium);
        buttonSave = findViewById(R.id.buttonSave);
    }
}