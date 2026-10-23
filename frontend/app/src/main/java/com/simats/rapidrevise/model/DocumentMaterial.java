package com.simats.rapidrevise.model;

import com.google.gson.annotations.SerializedName;

public class DocumentMaterial {

    private int id;

    @SerializedName("document_path")
    private String documentPath;

    public int getId() {
        return id;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public String getFileName() {
        if (documentPath == null) return "";
        return documentPath.substring(documentPath.lastIndexOf("/") + 1);
    }
}