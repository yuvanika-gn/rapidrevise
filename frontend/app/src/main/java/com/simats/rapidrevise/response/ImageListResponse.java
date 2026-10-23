package com.simats.rapidrevise.response;

import com.simats.rapidrevise.model.ImageMaterial;
import java.util.List;

public class ImageListResponse {

    private String status;
    private List<ImageMaterial> data;

    public String getStatus() {
        return status;
    }

    public List<ImageMaterial> getData() {
        return data;
    }
}
