package com.connections.service;

import com.connections.model.User;

public interface UserService {
    User save(User user);
    User findByUsername(String username);
    void deleteById(Long id);
}
