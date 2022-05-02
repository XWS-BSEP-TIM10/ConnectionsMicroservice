package com.connections.service.impl;

import com.connections.model.User;
import com.connections.repository.UserRepository;
import com.connections.service.RoleService;
import com.connections.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Override
    public User save(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null)
            return null;
        user.setRoles(roleService.findByName("ROLE_USER"));
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(String uuid) {
        return userRepository.findById(uuid).get();
    }

    @Override
    public void deleteById(String uuid) {
        userRepository.deleteById(uuid);
    }
}
