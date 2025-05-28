package com.example.shop.payload.response;

import java.util.List;

// src/main/java/com/example/shop/payload/response/JwtResponse.java
public class JwtResponse {
    private String token;
    private List<String> roles;

    public JwtResponse(String token, List<String> roles) {
        this.token = token;
        this.roles = roles;
    }
    public String getToken()  { return token;  }
    public List<String> getRoles() { return roles; }
}
