package com.simats.rapidrevise.request;

public class NoteRequest {

    private int user_id;
    private String subject_name;
    private String title;
    private String content;

    public NoteRequest(int user_id, String subject_name, String title, String content) {
        this.user_id = user_id;
        this.subject_name = subject_name;
        this.title = title;
        this.content = content;
    }
}
