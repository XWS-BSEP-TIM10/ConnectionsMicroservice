package com.connections.dto;

public class NewUserDto {

    private String username;

    public NewUserDto() {}

    public NewUserDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
