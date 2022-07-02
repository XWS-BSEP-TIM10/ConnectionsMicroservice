package com.connections.dto;

public class ConnectionsResponseDto {

    private String id;
    private boolean success;
    private String message;
    private final String service = "Connections";

    public ConnectionsResponseDto() {
    }

    public ConnectionsResponseDto(String id, boolean success, String message) {
        this.id = id;
        this.success = success;
        this.message = message;
    }

    public ConnectionsResponseDto(boolean success, String message, String id) {
        this.success = success;
        this.message = message;
        this.id = id;
    }

    public String getService() {
        return service;
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
