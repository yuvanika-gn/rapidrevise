package com.simats.rapidrevise.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simats.rapidrevise.model.SelectedFile;

import java.util.List;

public class SelectedFilesAdapter extends RecyclerView.Adapter<SelectedFilesAdapter.ViewHolder>{

    private List<SelectedFile> fileList;

    public SelectedFilesAdapter(List<SelectedFile> fileList){
        this.fileList=fileList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        View view= LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position){

        holder.textView.setText(fileList.get(position).getName());
    }

    @Override
    public int getItemCount(){
        return fileList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public ViewHolder(View itemView){
            super(itemView);
            textView=itemView.findViewById(android.R.id.text1);
        }
    }
}
