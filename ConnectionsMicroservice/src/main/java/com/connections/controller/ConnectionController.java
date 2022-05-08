package com.connections.controller;

import com.connections.dto.ConnectionRequestDto;
import com.connections.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1")
public class ConnectionController {

    private ConnectionService connectionService;

    @Autowired
    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @PostMapping(value = "connections")
    public ResponseEntity<HttpStatus> connect(@RequestBody @Valid ConnectionRequestDto newConnectionRequestDTO) {
        connectionService.sendConnectionRequest(newConnectionRequestDTO.getInitiatorId(), newConnectionRequestDTO.getReceiverId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("connections/approve")
    public ResponseEntity<HttpStatus> approveConnectionRequest(@RequestBody @Valid ConnectionRequestDto connectionRequestDto) {
        connectionService.respondConnectionRequest(connectionRequestDto.getInitiatorId(), connectionRequestDto.getReceiverId(), true);
        return ResponseEntity.ok().build();
    }

    @PutMapping("connections/refuse")
    public ResponseEntity<HttpStatus> refuseConnectionRequest(@RequestBody @Valid ConnectionRequestDto connectionRequestDto) {
        connectionService.respondConnectionRequest(connectionRequestDto.getInitiatorId(), connectionRequestDto.getReceiverId(), false);
        return ResponseEntity.ok().build();
    }

}
