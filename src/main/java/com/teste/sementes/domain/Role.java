package com.teste.sementes.domain;

public enum Role {
    USER("user");

    private String role;

    Role(String user) {

    }

    void UserRole(String role){
        this.role = role;
    }

    public String getRole(String role){
        return role;
    }
}
