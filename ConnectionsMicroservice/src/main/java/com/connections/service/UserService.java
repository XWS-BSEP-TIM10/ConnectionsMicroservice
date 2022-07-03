package com.connections.service;

import com.connections.model.User;

public interface UserService {
    User save(User user);

    User findById(String id);

    void deleteById(String id);

    void updateStatus(String id, boolean isPrivate);
}
