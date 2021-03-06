package com.connections;

import com.connections.model.Role;
import com.connections.model.User;
import com.connections.service.RoleService;
import com.connections.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.List;

@SpringBootApplication
public class ConnectionsApplication implements CommandLineRunner {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(ConnectionsApplication.class, args);
    }

    @Override
    public void run(String... args) {

        List<Role> roles = roleService.findByName("ROLE_USER");
        if (roles.isEmpty()) {
            roleService.save(new Role("ROLE_USER"));
        }
        User user1 = new User();
        user1.setId("pera");
        user1.setConnections(new HashMap<>());
        user1.setRoles(roles);
        user1.setPrivate(true);
        userService.save(user1);

        user1 = new User();
        user1.setId("kina");
        user1.setRoles(roles);
        user1.setPrivate(false);
        user1.setConnections(new HashMap<>());
        userService.save(user1);

        user1 = new User();
        user1.setId("marko");
        user1.setRoles(roles);
        user1.setPrivate(false);
        user1.setConnections(new HashMap<>());
        userService.save(user1);

        user1 = new User();
        user1.setId("d12602fd-b7af-4da1-b1ca-bad8166d1fb2");
        user1.setRoles(roles);
        user1.setPrivate(false);
        user1.setConnections(new HashMap<>());
        userService.save(user1);

        user1 = new User();
        user1.setId("d12602fd-b7af-4da1-b1ca-bad8166d1fb4");
        user1.setRoles(roles);
        user1.setPrivate(false);
        user1.setConnections(new HashMap<>());
        userService.save(user1);


    }

}
