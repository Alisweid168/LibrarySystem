package com.example.assessment.librarysystem.models;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}