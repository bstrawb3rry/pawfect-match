package com.uvt.bachelor.pawfectmatch.model;

public class AuthResponse {
    private final String jwt;
    private final Long ownerId;

    public AuthResponse(String jwt, Long ownerId) {
        this.jwt = jwt;
        this.ownerId = ownerId;
    }

    public String getJwt() {
        return jwt;
    }

    public Long getOwnerId() {
        return ownerId;
    }
}
