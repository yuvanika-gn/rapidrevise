package com.simats.rapidrevise;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.simats.rapidrevise.adapter.ImageViewerAdapter;

import java.util.ArrayList;

public class ImageViewerActivity extends AppCompatActivity {

    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        viewPager = findViewById(R.id.viewPagerImages);

        ArrayList<String> imageUrls = getIntent().getStringArrayListExtra("images");
        int position = getIntent().getIntExtra("position", 0);

        ImageViewerAdapter adapter = new ImageViewerAdapter(this, imageUrls);
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(position, false);
    }
}