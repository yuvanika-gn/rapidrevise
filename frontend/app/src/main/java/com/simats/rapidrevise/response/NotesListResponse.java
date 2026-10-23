package com.simats.rapidrevise.response;

import com.simats.rapidrevise.model.Note;

import java.util.List;

public class NotesListResponse {

    private String status;
    private List<Note> data;

    public String getStatus() { return status; }
    public List<Note> getData() { return data; }
}

