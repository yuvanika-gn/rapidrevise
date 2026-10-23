package com.simats.rapidrevise.response;

public class LoginResponse {

    private String status;
    private String message;
    private int user_id;
    private String fullname;
    private String email;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }
}
