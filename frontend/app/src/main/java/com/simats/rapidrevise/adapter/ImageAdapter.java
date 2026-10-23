package com.simats.rapidrevise.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.simats.rapidrevise.FullImageActivity;
import com.simats.rapidrevise.ImageViewerActivity;
import com.simats.rapidrevise.R;
import com.simats.rapidrevise.model.ImageMaterial;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<ImageMaterial> imageList;
    private Context context;


    public interface OnImageLongClickListener {
        void onLongClick(ImageMaterial image, int position);
    }

    private OnImageLongClickListener listener;
    public ImageAdapter(Context context,
                        List<ImageMaterial> imageList,
                        OnImageLongClickListener listener) {

        this.context = context;
        this.imageList = imageList;
        this.listener = listener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ImageMaterial image = imageList.get(position);

        String fullUrl = "http://192.168.0.2/rapidrevise/" + image.getImage_path();

        Log.d("IMAGE_URL", fullUrl);

        Glide.with(context)
                .load(fullUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imgPreview);

        // Open image viewer on click
        holder.itemView.setOnClickListener(v -> {

            ArrayList<String> imageUrls = new ArrayList<>();

            for (ImageMaterial img : imageList) {
                imageUrls.add("http://192.168.0.2/rapidrevise/" + img.getImage_path());
            }

            Intent intent = new Intent(context, ImageViewerActivity.class);
            intent.putStringArrayListExtra("images", imageUrls);
            intent.putExtra("position", position);

            context.startActivity(intent);
        });

        // Detect long press for delete
        holder.itemView.setOnLongClickListener(v -> {

            if (listener != null) {
                listener.onLongClick(image, position);
            }

            return true;
        });
    }


    @Override
    public int getItemCount() {
        return imageList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPreview;

        ViewHolder(View itemView) {
            super(itemView);
            imgPreview = itemView.findViewById(R.id.imgPreview);
        }
    }
}
