package com.codulate.auth.dto;

import java.io.Serializable;

public class AuthRespDto implements Serializable {

    private final String jwt;

    public AuthRespDto(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
