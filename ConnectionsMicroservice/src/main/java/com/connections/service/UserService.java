package com.connections.service;

import com.connections.model.User;

public interface UserService {
    User save(User user);

    void deleteById(Long id);
}
