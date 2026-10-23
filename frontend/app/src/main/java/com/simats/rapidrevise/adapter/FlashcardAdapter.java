package com.simats.rapidrevise.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.simats.rapidrevise.R;
import com.simats.rapidrevise.model.Flashcard;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.ViewHolder> {

    private List<Flashcard> list;

    // ✅ Maintain flip state per card
    private Map<Integer, Boolean> flipStates = new HashMap<>();

    public FlashcardAdapter(List<Flashcard> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_flashcard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Flashcard card = list.get(position);

        boolean isFlipped = flipStates.get(position) != null && flipStates.get(position);

        // ✅ SET CORRECT INITIAL STATE
        if (isFlipped) {
            // ANSWER (GREEN)
            holder.tvContent.setText(card.getAnswer());
            holder.tvLabel.setText("ANSWER");

            holder.cardLayout.setBackgroundResource(R.drawable.bg_flashcard_answer);
            holder.tvContent.setTextColor(Color.WHITE);
            holder.tvLabel.setTextColor(Color.parseColor("#CCFFFFFF"));

        } else {
            // QUESTION (WHITE)
            holder.tvContent.setText(card.getQuestion());
            holder.tvLabel.setText("QUESTION");

            holder.cardLayout.setBackgroundResource(R.drawable.bg_flashcard);
            holder.tvContent.setTextColor(Color.BLACK);
            holder.tvLabel.setTextColor(Color.GRAY);
        }

        // ✅ CLICK TO FLIP
        holder.itemView.setOnClickListener(v -> flipCard(holder, card, position));
    }

    private void flipCard(ViewHolder holder, Flashcard card, int position) {

        boolean isFlipped = flipStates.get(position) != null && flipStates.get(position);

        holder.itemView.animate()
                .rotationY(90)
                .setDuration(250)
                .withEndAction(() -> {

                    if (!isFlipped) {
                        // 👉 SHOW ANSWER (GREEN)
                        holder.tvContent.setText(card.getAnswer());
                        holder.tvLabel.setText("ANSWER");

                        holder.cardLayout.setBackgroundResource(R.drawable.bg_flashcard_answer);
                        holder.tvContent.setTextColor(Color.WHITE);
                        holder.tvLabel.setTextColor(Color.parseColor("#CCFFFFFF"));

                    } else {
                        // 👉 SHOW QUESTION (WHITE)
                        holder.tvContent.setText(card.getQuestion());
                        holder.tvLabel.setText("QUESTION");

                        holder.cardLayout.setBackgroundResource(R.drawable.bg_flashcard);
                        holder.tvContent.setTextColor(Color.BLACK);
                        holder.tvLabel.setTextColor(Color.GRAY);
                    }

                    // ✅ SAVE STATE
                    flipStates.put(position, !isFlipped);

                    holder.itemView.setRotationY(-90);
                    holder.itemView.animate().rotationY(0).setDuration(250).start();
                }).start();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void shuffleCards() {
        Collections.shuffle(list);
        flipStates.clear(); // reset states
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvContent, tvLabel;
        View cardLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            tvContent = itemView.findViewById(R.id.tvFlashcardContent);
            tvLabel = itemView.findViewById(R.id.tvLabel);
            cardLayout = itemView.findViewById(R.id.cardLayout); // ✅ IMPORTANT FIX
        }
    }
}