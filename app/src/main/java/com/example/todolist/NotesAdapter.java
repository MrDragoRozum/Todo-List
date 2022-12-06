package com.example.todolistpractive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<Note> notes = new ArrayList<>();
    private OnNoteClickListener onNoteClickListener;

    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,
                parent,
                false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notes.get(position);

        holder.textView.setText(note.getText());

        int resIdColor;
        switch (note.getPriority()) {
            case 0:
                resIdColor = android.R.color.holo_green_light;
                break;
            case 1:
                resIdColor = android.R.color.holo_orange_light;
                break;
            default:
                resIdColor = android.R.color.holo_red_light;
        }
        int colorId = ContextCompat.getColor(holder.textView.getContext(), resIdColor);
        holder.textView.setBackgroundColor(colorId);

        holder.itemView.setOnClickListener(l -> {
            if(onNoteClickListener != null) {
                onNoteClickListener.noteClickListener(note);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewNote);
        }
    }

    interface OnNoteClickListener {
        void noteClickListener(Note note);
    }
}
