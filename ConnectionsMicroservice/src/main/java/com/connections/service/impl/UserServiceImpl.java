package com.connections.service.impl;

import com.connections.model.Role;
import com.connections.model.User;
import com.connections.repository.UserRepository;
import com.connections.service.RoleService;
import com.connections.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
