package com.simats.rapidrevise.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.simats.rapidrevise.R;
import com.simats.rapidrevise.model.KeyPoint;

import java.util.List;

public class KeyPointsAdapter extends RecyclerView.Adapter<KeyPointsAdapter.ViewHolder> {

    private List<KeyPoint> list;

    public KeyPointsAdapter(List<KeyPoint> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).isHighlight() ? 1 : 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layout = (viewType == 1) ?
                R.layout.item_key_point_highlight :
                R.layout.item_key_point;

        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        KeyPoint item = list.get(position);

        // ✅ SET TITLE FROM AI
        holder.tvTitle.setText(item.getTitle());

        // ✅ SET DESCRIPTION
        String text = item.getText();
        if (text == null) text = "No content";

        holder.tvKeyPoint.setText(text);

        // ⭐ Highlight logic
        if (item.isHighlight()) {
            holder.btnStar.setVisibility(View.VISIBLE);
        } else {
            holder.btnStar.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvKeyPoint;
        ImageView btnStar;

        ViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvKeyPoint = itemView.findViewById(R.id.tvKeyPoint);
            btnStar = itemView.findViewById(R.id.btnStar);
        }
    }
}