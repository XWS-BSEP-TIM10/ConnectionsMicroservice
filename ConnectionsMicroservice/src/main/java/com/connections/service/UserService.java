package com.connections.service;

import com.connections.model.User;

public interface UserService {
    User save(User user);
    User findByUsername(String username);
    User findById(String uuid);
    void deleteById(String uuid);
}
