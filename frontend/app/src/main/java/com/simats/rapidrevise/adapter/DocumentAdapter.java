package com.simats.rapidrevise.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simats.rapidrevise.R; 
import com.simats.rapidrevise.model.DocumentMaterial;

import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ViewHolder> {

    private Context context;
    private List<DocumentMaterial> documentList;

    public interface OnDocumentLongClickListener {
        void onLongClick(DocumentMaterial document, int position);
    }

    private OnDocumentLongClickListener listener;

    public DocumentAdapter(Context context,
                           List<DocumentMaterial> documentList,
                           OnDocumentLongClickListener listener) {
        this.context = context;
        this.documentList = documentList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_document_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DocumentMaterial document = documentList.get(position);

        holder.tvFileName.setText(document.getFileName());

        // ✅ FULL URL (IMPORTANT)
        String fileUrl = "http://192.168.0.2/rapidrevise/" + document.getDocumentPath();

        holder.itemView.setOnClickListener(v -> {


            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(fileUrl), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);

            } catch (Exception e) {
                Toast.makeText(context, "Cannot open file", Toast.LENGTH_SHORT).show();
            }

        });

        // ✅ LONG CLICK → OPTIONS
        holder.itemView.setOnLongClickListener(v -> {

            if (listener != null) {
                listener.onLongClick(document, position);
            }

            return true;
        });
    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvFileName;
        ImageView icon;

        ViewHolder(View itemView) {
            super(itemView);

            tvFileName = itemView.findViewById(R.id.tvFileName);
            icon = itemView.findViewById(R.id.imgDocIcon);
        }
    }
}