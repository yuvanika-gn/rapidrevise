package com.simats.rapidrevise.request;

public class DeleteSubjectRequest {

    private int subject_id;
    private int user_id;

    public DeleteSubjectRequest(int subject_id, int user_id) {
        this.subject_id = subject_id;
        this.user_id = user_id;
    }
}
