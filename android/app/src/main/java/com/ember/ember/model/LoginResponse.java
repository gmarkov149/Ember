package com.ember.ember.model;

public class LoginResponse {
    private String status;
    private UserDetails data;

    public UserDetails getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }
}
