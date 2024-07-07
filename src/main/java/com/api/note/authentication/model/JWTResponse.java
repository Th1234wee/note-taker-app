package com.api.note.authentication.model;

public class JWTResponse {
    private String token;

    public JWTResponse(String token){
        super();
        this.token = token;
    }

    public String getToken(){
        return token;
    }
}
