package com.simats.rapidrevise.request;

public class DeleteNoteRequest {

    private int note_id;
    private int user_id;

    public DeleteNoteRequest(int note_id, int user_id) {
        this.note_id = note_id;
        this.user_id = user_id;
    }
}
