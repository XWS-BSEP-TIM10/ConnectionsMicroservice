package com.connections.controller;

import com.connections.dto.ConnectionRequestDto;
import com.connections.exception.ConnectionAlreadyExistsException;
import com.connections.exception.NoPendingConnectionException;
import com.connections.exception.UserDoesNotExist;
import com.connections.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping(value = "users/{id}/connections")
    public ResponseEntity<HttpStatus> connect(@RequestBody @Valid ConnectionRequestDto connectionRequestDto, @PathVariable String id) {
        try {
            connectionService.sendConnectionRequest(id, connectionRequestDto.getConnectingId());
            return ResponseEntity.ok().build();
        } catch (UserDoesNotExist ex) {
            return ResponseEntity.notFound().build();
        } catch (ConnectionAlreadyExistsException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("users/{id}/connections/approve")
    public ResponseEntity<HttpStatus> approveConnectionRequest(@RequestBody @Valid ConnectionRequestDto connectionRequestDto, @PathVariable String id) {
        try {
            if (!connectionService.respondConnectionRequest(id, connectionRequestDto.getConnectingId(), true))
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok().build();
        } catch (UserDoesNotExist | NoPendingConnectionException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("users/{id}/connections/refuse")
    public ResponseEntity<HttpStatus> refuseConnectionRequest(@RequestBody @Valid ConnectionRequestDto connectionRequestDto, @PathVariable String id) {
        try {
            if (!connectionService.respondConnectionRequest(id, connectionRequestDto.getConnectingId(), false))
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok().build();
        } catch (UserDoesNotExist | NoPendingConnectionException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
