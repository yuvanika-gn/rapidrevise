package com.simats.rapidrevise.model;

import com.google.gson.annotations.SerializedName;

public class ImageMaterial {

    private int id;

    @SerializedName("image_path")
    private String image_path;

    public int getId() {
        return id;
    }

    public String getImage_path() {
        return image_path;
    }
}
