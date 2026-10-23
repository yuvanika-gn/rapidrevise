package com.simats.rapidrevise.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simats.rapidrevise.R;
import com.simats.rapidrevise.model.Formula;

import java.util.List;

public class FormulaAdapter extends RecyclerView.Adapter<FormulaAdapter.ViewHolder> {

    private List<Formula> list;
    private Context context;

    public FormulaAdapter(Context context, List<Formula> list) {
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvFormulaName, tvEquation, tvDescription;
        ImageView btnCopy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFormulaName = itemView.findViewById(R.id.tvFormulaName);
            tvEquation = itemView.findViewById(R.id.tvEquation);
            tvDescription = itemView.findViewById(R.id.tvFormulaDescription);
            btnCopy = itemView.findViewById(R.id.btnCopy);
        }
    }

    @NonNull
    @Override
    public FormulaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_formula, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FormulaAdapter.ViewHolder holder, int position) {

        Formula formula = list.get(position);

        holder.tvFormulaName.setText(formula.getName());
        holder.tvEquation.setText(formula.getEquation());
        holder.tvDescription.setText(formula.getDescription());

        // 🔥 COPY BUTTON
        holder.btnCopy.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager)
                    context.getSystemService(Context.CLIPBOARD_SERVICE);

            ClipData clip = ClipData.newPlainText("Formula", formula.getEquation());
            clipboard.setPrimaryClip(clip);

            Toast.makeText(context, "Formula copied", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
