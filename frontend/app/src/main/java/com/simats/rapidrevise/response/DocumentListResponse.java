package com.simats.rapidrevise.response;

import com.simats.rapidrevise.model.DocumentMaterial;
import java.util.List;

public class DocumentListResponse {

    private String status;
    private List<DocumentMaterial> data;

    public String getStatus() {
        return status;
    }

    public List<DocumentMaterial> getData() {
        return data;
    }
}