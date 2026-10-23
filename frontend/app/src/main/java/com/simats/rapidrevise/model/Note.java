package com.simats.rapidrevise.model;

//public class Note {
//
//    private String title;
//    private String content;
//
//    public Note(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getContent() {
//        return content;
//    }
//}


public class Note {

    private int id;
    private String title;
    private String content;
    private String created_at;
    int importance;
    String difficulty;

    public int getImportance() { return importance; }
    public String getDifficulty() { return difficulty; }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getCreated_at() { return created_at; }
}


