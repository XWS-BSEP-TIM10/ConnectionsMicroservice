package com.connections.controller;

import com.connections.dto.ConnectionRequestDto;
import com.connections.exception.UserDoesNotExist;
import com.connections.model.Connection;
import com.connections.model.ConnectionStatus;
import com.connections.model.User;
import com.connections.service.ConnectionService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/connections")
public class ConnectionController {

    private ConnectionService connectionService;

    @Autowired
    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @PostMapping
    public ResponseEntity<String> connect(@RequestBody ConnectionRequestDto connectionRequestDto) {
        try {
            Connection newConnection = connectionService.sendConnectionRequest(connectionRequestDto.getId());

            if(newConnection.getConnectionStatus() == ConnectionStatus.CONNECTED)
                return new ResponseEntity<>("Connection created.", HttpStatus.OK);
            return new ResponseEntity<>("Connection request sent.", HttpStatus.OK);
        }catch(UserDoesNotExist ex){
            return new ResponseEntity<>("User doest not exist.", HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>("Something went wrong.", HttpStatus.OK);
        }
    }

    @PutMapping("approve")
    public ResponseEntity<String> approveConnectionRequest(@RequestBody ConnectionRequestDto connectionRequestDto) {
        try {
            if(connectionService.approveConnectionRequest(connectionRequestDto.getId()) == null)
                return new ResponseEntity<>("Connection not found.", HttpStatus.OK);
            return new ResponseEntity<>("Connection approved.", HttpStatus.OK);
        }catch(UserDoesNotExist ex){
            return new ResponseEntity<>("User doest not exist.", HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>("Something went wrong.", HttpStatus.OK);
        }
    }

    @PutMapping("refuse")
    public ResponseEntity<String> refuseConnectionRequest(@RequestBody ConnectionRequestDto connectionRequestDto) {
        try {
            if(connectionService.refuseConnectionRequest(connectionRequestDto.getId()) == null)
                return new ResponseEntity<>("Connection not found.", HttpStatus.OK);
            return new ResponseEntity<>("Connection refused.", HttpStatus.OK);
        }catch(UserDoesNotExist ex){
            return new ResponseEntity<>("User doest not exist.", HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>("Something went wrong.", HttpStatus.OK);
        }
    }
}
