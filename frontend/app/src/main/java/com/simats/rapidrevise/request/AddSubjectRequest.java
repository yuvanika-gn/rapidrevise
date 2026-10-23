package com.simats.rapidrevise.request;

public class AddSubjectRequest {
    private int user_id;
    private String subject_name;

    public AddSubjectRequest(int user_id, String subject_name) {
        this.user_id = user_id;
        this.subject_name = subject_name;
    }
}
