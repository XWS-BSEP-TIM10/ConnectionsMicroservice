package com.connections.dto;

import javax.validation.constraints.NotBlank;

public class ConnectionRequestDto {

    @NotBlank
    private String initiatorId;
    @NotBlank
    private String receiverId;


    public String getInitiatorId() {
        return initiatorId;
    }

    public String getReceiverId() {
        return receiverId;
    }
}
