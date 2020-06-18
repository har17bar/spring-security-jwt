package com.codulate.auth.dto;

import java.io.Serializable;

public class AuthReqDto implements Serializable {


    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //need default constructor for JSON Parsing
    public AuthReqDto()
    {

    }

    public AuthReqDto(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }
}
