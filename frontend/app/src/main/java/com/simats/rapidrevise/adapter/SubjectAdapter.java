package com.simats.rapidrevise.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simats.rapidrevise.R;
import com.simats.rapidrevise.response.SubjectResponse;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    // DATA
    private List<SubjectResponse> subjectList;

    // CLICK INTERFACES
    public interface OnSubjectClickListener {
        void onClick(int subjectId, String subjectName);
    }

    public interface OnSubjectLongClickListener {
        void onLongClick(int subjectId, String subjectName);
    }

    private OnSubjectClickListener clickListener;
    private OnSubjectLongClickListener longClickListener;

    // CONSTRUCTOR
    public SubjectAdapter(List<SubjectResponse> subjectList,
                          OnSubjectClickListener clickListener,
                          OnSubjectLongClickListener longClickListener) {
        this.subjectList = subjectList;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_library, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {

        SubjectResponse subject = subjectList.get(position);

        holder.tvFileName.setText(subject.getSubject_name());
        holder.tvFileDetails.setText("Subject • Notes");
        holder.iconFile.setImageResource(R.drawable.ic_book_svg);

        // 👉 NORMAL CLICK (OPEN SUBJECT)
        holder.itemView.setOnClickListener(v ->
                clickListener.onClick(
                        subject.getId(),
                        subject.getSubject_name()
                )
        );

        // 👉 LONG CLICK (DELETE SUBJECT)
        holder.itemView.setOnLongClickListener(v -> {
            longClickListener.onLongClick(
                    subject.getId(),
                    subject.getSubject_name()
            );
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    static class SubjectViewHolder extends RecyclerView.ViewHolder {

        ImageView iconFile;
        TextView tvFileName, tvFileDetails;

        SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            iconFile = itemView.findViewById(R.id.iconFile);
            tvFileName = itemView.findViewById(R.id.tvFileName);
            tvFileDetails = itemView.findViewById(R.id.tvFileDetails);
        }
    }
}
