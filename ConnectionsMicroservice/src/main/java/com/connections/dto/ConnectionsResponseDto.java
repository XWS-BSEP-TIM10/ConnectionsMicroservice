package com.connections.dto;

public class ConnectionsResponseDto {

    private Long id;
    private boolean success;
    private String message;

    public ConnectionsResponseDto() {
    }

    public ConnectionsResponseDto(Long id, boolean success, String message) {
        this.id = id;
        this.success = success;
        this.message = message;
    }

    public ConnectionsResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Long getId() {
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
