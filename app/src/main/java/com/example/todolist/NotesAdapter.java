package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    ArrayList<Note> notes = new ArrayList<>();

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.note_item,
                parent,
                false
        );
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.textViewNotes.setText(note.getText());

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
                break;
        }

        int idColor = ContextCompat.getColor(holder.itemView.getContext(), idResColor);
        holder.textViewNotes.setBackgroundColor(idColor);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewNotes;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNotes = itemView.findViewById(R.id.textViewNote);
        }
    }
}
