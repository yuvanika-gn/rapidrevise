package com.simats.rapidrevise.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.simats.rapidrevise.R;
import com.simats.rapidrevise.model.UrlMaterial;

import java.util.List;

public class UrlAdapter extends RecyclerView.Adapter<UrlAdapter.ViewHolder> {

    private Context context;
    private List<UrlMaterial> urlList;



    public UrlAdapter(Context context, List<UrlMaterial> urlList) {
        this.context = context;
        this.urlList = urlList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_url, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        UrlMaterial url = urlList.get(position);

        holder.tvUrlName.setText(url.getUrlName());

        // ✅ NORMAL CLICK → OPEN URL
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url.getUrlLink()));
            context.startActivity(intent);
        });

        // 🔥 LONG PRESS → DELETE
        holder.itemView.setOnLongClickListener(v -> {

            new AlertDialog.Builder(context)
                    .setTitle("Delete")
                    .setMessage("Do you want to delete this URL?")
                    .setPositiveButton("Yes", (dialog, which) -> {

                        // 🔥 REMOVE FROM LIST
                        urlList.remove(position);

                        // 🔥 NOTIFY ADAPTER
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, urlList.size());

                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();

                        // ⚠️ OPTIONAL: CALL API TO DELETE FROM DB

                    })
                    .setNegativeButton("Cancel", null)
                    .show();

            return true; // VERY IMPORTANT
        });
    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvUrlName;

        public ViewHolder(View itemView) {
            super(itemView);

            tvUrlName = itemView.findViewById(R.id.tvUrlName);
        }
    }
}