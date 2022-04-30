package com.connections.service;

import com.connections.model.User;

public interface UserService {
    User save(User user);
    User findByUsername(String username);
    User findByUuid(String uuid);
    void deleteById(Long id);
}
