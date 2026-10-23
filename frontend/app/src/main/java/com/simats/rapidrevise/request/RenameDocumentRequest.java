package com.simats.rapidrevise.request;

public class RenameDocumentRequest {

    private int id;
    private String new_name;

    public RenameDocumentRequest(int id, String new_name) {
        this.id = id;
        this.new_name = new_name;
    }

}
