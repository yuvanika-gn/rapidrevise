package com.simats.rapidrevise.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simats.rapidrevise.R;
import com.simats.rapidrevise.model.ProgressModel;

import java.util.List;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ViewHolder> {

    List<ProgressModel> list;

    public ProgressAdapter(List<ProgressModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_progress, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProgressModel model = list.get(position);

        holder.tvSubject.setText(model.getSubject_name());

        holder.tvTotal.setText("Total Activity: " + model.getTotal_activity());

        holder.tvConcepts.setText("Concepts: " + model.getConcepts());
        holder.tvFlashcards.setText("Flashcards: " + model.getFlashcards());
        holder.tvFormulas.setText("Formulas: " + model.getFormulas());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSubject, tvTotal, tvConcepts, tvFlashcards, tvFormulas;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvConcepts = itemView.findViewById(R.id.tvConcepts);
            tvFlashcards = itemView.findViewById(R.id.tvFlashcards);
            tvFormulas = itemView.findViewById(R.id.tvFormulas);
        }
    }
}
