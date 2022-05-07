package com.connections.controller;

import com.connections.dto.ConnectionsResponseDto;
import com.connections.dto.NewUserDto;
import com.connections.model.User;
import com.connections.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ConnectionsResponseDto> register(@RequestBody NewUserDto dto) {
        User user = service.save(new User(dto));
        if (user == null)
            return ResponseEntity.ok(new ConnectionsResponseDto(false, "failed"));
        return ResponseEntity.ok(new ConnectionsResponseDto(user.getId(), true, "sucess"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
