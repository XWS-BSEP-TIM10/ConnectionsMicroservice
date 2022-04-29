package com.connections.controller;

import com.connections.dto.ConnectionRequestDto;
import com.connections.exception.UserDoesNotExist;
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
            connectionService.sendConnectionRequest(connectionRequestDto.getUsername());
            return new ResponseEntity<>("Connection created.", HttpStatus.OK);
        }catch(UserDoesNotExist ex){
            return new ResponseEntity<>("User doest not exist.", HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>("Something went wrong.", HttpStatus.OK);
        }
    }

    @PutMapping("approve")
    public ResponseEntity<String> approveConnectionRequest(@RequestBody ConnectionRequestDto connectionRequestDto) {
        try {
            if(connectionService.approveConnectionRequest(connectionRequestDto.getUsername()) == null)
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
            if(connectionService.refuseConnectionRequest(connectionRequestDto.getUsername()) == null)
                return new ResponseEntity<>("Connection not found.", HttpStatus.OK);
            return new ResponseEntity<>("Connection refused.", HttpStatus.OK);
        }catch(UserDoesNotExist ex){
            return new ResponseEntity<>("User doest not exist.", HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>("Something went wrong.", HttpStatus.OK);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<User>> getFollowing(){
    	List<User> retVal = new ArrayList<User>();
    	retVal.addAll(connectionService.getFollowing());
    	retVal.addAll(connectionService.getFollowers());
    	return new ResponseEntity<>(retVal,  HttpStatus.OK);
    }
}
