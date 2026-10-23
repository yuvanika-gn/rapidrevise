package com.simats.rapidrevise.request;

public class SignupRequest {

    private String fullname;
    private String email;
    private String password;

    public SignupRequest(String fullname, String email, String password) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
    }
}
