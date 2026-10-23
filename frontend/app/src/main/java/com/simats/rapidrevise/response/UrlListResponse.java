package com.simats.rapidrevise.response;

import com.simats.rapidrevise.model.UrlMaterial;
import java.util.List;

public class UrlListResponse {

    private String status;
    private List<UrlMaterial> data;

    public String getStatus() {
        return status;
    }

    public List<UrlMaterial> getData() {
        return data;
    }
}