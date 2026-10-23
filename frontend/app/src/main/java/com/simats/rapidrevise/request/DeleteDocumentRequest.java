package com.simats.rapidrevise.request;

public class DeleteDocumentRequest {

    private int id;

    public DeleteDocumentRequest(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}