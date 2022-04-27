package com.connections.dto;

public class ConnectionsResponseDto {

    private String id;
    private boolean success;
    private String message;

    public ConnectionsResponseDto() {
    }

    public ConnectionsResponseDto(String id, boolean success, String message) {
        this.id = id;
        this.success = success;
        this.message = message;
    }

    public ConnectionsResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
