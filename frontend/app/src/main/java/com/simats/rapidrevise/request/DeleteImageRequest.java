package com.simats.rapidrevise.request;

public class DeleteImageRequest {

    private int id;

    public DeleteImageRequest(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}