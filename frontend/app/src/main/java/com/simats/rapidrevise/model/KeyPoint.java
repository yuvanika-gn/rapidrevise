package com.simats.rapidrevise.model;

import com.google.gson.annotations.SerializedName;

public class KeyPoint {

    private String title;

    @SerializedName("point")   // 🔥 MUST
    private String text;

    private boolean highlight;

    public KeyPoint(String title, String text, boolean highlight) {
        this.title = title;
        this.text = text;
        this.highlight = highlight;
    }
    int importance;
    String difficulty;

    public int getImportance() { return importance; }
    public String getDifficulty() { return difficulty; }

    public String getTitle() { return title; }
    public String getText() { return text; }
    public boolean isHighlight() { return highlight; }
}