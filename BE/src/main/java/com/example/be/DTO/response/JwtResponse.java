package com.example.api.dto.response;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtResponse {
    String token;
    private String type = "Bearer";
    private String userName;
    private String name;
    private Collection<? extends GrantedAuthority> roles;

    public JwtResponse() {
    }


    public JwtResponse(String token, String userName, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.userName = userName;
        this.roles = authorities;
    }

    public JwtResponse(String token) {
        this.token = token;
    }

    public JwtResponse(String token, String username, Collection<? extends GrantedAuthority> authorities, String name) {
        this.token = token;
        this.userName = username;
        this.roles = authorities;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Collection<? extends GrantedAuthority> roles) {
        this.roles = roles;
    }
}