package com.simats.rapidrevise.response;

import com.simats.rapidrevise.model.MaterialItem;

import java.util.List;



public class SearchResponse {

    private String status;
    private List<MaterialItem> data;

    public String getStatus() {
        return status;
    }

    public List<MaterialItem> getData() {
        return data;
    }
}