package com.simats.rapidrevise.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simats.rapidrevise.R;
import com.simats.rapidrevise.model.MaterialItem;
import com.simats.rapidrevise.model.Note;

import java.util.List;
import android.net.Uri;
import android.content.Intent;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder>{

    Context context;
    List<MaterialItem> list;

    public LibraryAdapter(Context context,List<MaterialItem> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_library,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position){

        MaterialItem item = list.get(position);

        holder.title.setText(item.getName());
        holder.type.setText(item.getType());

        holder.itemView.setOnClickListener(v -> {

            String type = item.getType();

            if(type.equalsIgnoreCase("note")){

                Intent intent = new Intent(context, com.simats.rapidrevise.NoteDetailActivity.class);

                intent.putExtra("note_id", item.getId());
                intent.putExtra("note_id", item.getId());
                intent.putExtra("title", item.getName()); // optional

                context.startActivity(intent);
            }
            else if(type.equalsIgnoreCase("document")){

                String file = item.getFile();

                if(file != null && !file.isEmpty()){

                    String url = "http://192.168.0.2/rapidrevise/" + file;

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(url), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                    context.startActivity(intent);

                }else{
                    Toast.makeText(context,"Document not available",Toast.LENGTH_SHORT).show();
                }
            }
            else if(type.equalsIgnoreCase("url")){

                String url = item.getUrl();

                if(url != null && !url.isEmpty()){

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(intent);

                }else{

                    Toast.makeText(context,
                            "URL not available",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title,type;

        public ViewHolder(View itemView){
            super(itemView);

            title = itemView.findViewById(R.id.tvTitle);
            type = itemView.findViewById(R.id.tvType);
        }
    }
}