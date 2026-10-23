package com.simats.rapidrevise.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simats.rapidrevise.EditNoteActivity;
import com.simats.rapidrevise.NoteDetailActivity;
import com.simats.rapidrevise.R;
import com.simats.rapidrevise.model.Note;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private List<Note> noteList;
    private OnNoteLongClickListener longClickListener;

    // Interface for long press
    public interface OnNoteLongClickListener {
        void onNoteLongClick(Note note, int position);
    }

    // Constructor
    public NotesAdapter(List<Note> noteList,
                        OnNoteLongClickListener listener) {
        this.noteList = noteList;
        this.longClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Note note = noteList.get(position);

        holder.tvTitle.setText(note.getTitle());
        holder.tvContent.setText(note.getContent());

        // 🔥 CLICK → OPEN FULL NOTE
        holder.itemView.setOnClickListener(v -> {

            android.content.Intent intent =
                    new android.content.Intent(v.getContext(), com.simats.rapidrevise.NoteDetailActivity.class);

            intent.putExtra("title", note.getTitle());
            intent.putExtra("content", note.getContent());

            v.getContext().startActivity(intent);
        });

        // ✅ LONG PRESS (ALREADY WORKING)
        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onNoteLongClick(note, position);
            }
            return true;
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), NoteDetailActivity.class);
            intent.putExtra("note_id", note.getId());
            intent.putExtra("title", note.getTitle());
            intent.putExtra("content", note.getContent());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvContent;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvContent = itemView.findViewById(R.id.tvNoteContent);
        }
    }
}
