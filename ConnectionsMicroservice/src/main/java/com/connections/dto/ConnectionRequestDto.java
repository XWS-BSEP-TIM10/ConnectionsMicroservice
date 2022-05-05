package com.connections.dto;

import javax.validation.constraints.NotBlank;

public class ConnectionRequestDto {

    @NotBlank
    private String connectingId;

    public ConnectionRequestDto() {
    }

    public String getConnectingId() {
        return connectingId;
    }
}
