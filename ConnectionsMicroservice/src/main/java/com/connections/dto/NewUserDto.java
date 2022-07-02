package com.connections.dto;

public class NewUserDto {

    private String uuid;

    private String username;

    public NewUserDto() {/*dto*/}

    public String getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "NewUserDto{" +
                "id='" + uuid + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
