package com.simats.rapidrevise.response;

public class ProfileResponse {

    private String status;
    private String name;
    private String email;
    private int subjects;
    private int documents;
    private int images;

    public String getStatus() { return status; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getSubjects() { return subjects; }
    public int getDocuments() { return documents; }
    public int getImages() { return images; }
}
