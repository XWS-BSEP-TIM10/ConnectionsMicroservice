package com.connections.service.impl;

import com.connections.exception.UserDoesNotExist;
import com.connections.model.User;
import com.connections.repository.UserRepository;
import com.connections.service.RoleService;
import com.connections.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Override
    public User save(User user) {
        try {
            if (userRepository.findById(user.getId()).isPresent())
                return null;
            user.setRoles(roleService.findByName("ROLE_USER"));
            return userRepository.save(user);
        }catch(Exception ex){
            return null;
        }
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateStatus(String id, boolean isPrivate) {
        User user = userRepository.findById(id).orElseThrow(UserDoesNotExist::new);
        user.setPrivate(isPrivate);
        userRepository.save(user);
    }
}
