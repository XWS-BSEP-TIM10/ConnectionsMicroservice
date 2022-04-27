package com.connections.service.impl;

import com.connections.model.User;
import com.connections.repository.UserRepository;
import com.connections.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null)
            return null;
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
