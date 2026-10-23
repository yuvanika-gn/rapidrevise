package com.simats.rapidrevise.response;

import com.simats.rapidrevise.model.Note;

public class NoteResponse {
    String status;
    Note data;

    public Note getData() {
        return data;
    }
}
