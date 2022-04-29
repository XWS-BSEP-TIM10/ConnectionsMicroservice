package com.connections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.connections.model.Role;
import com.connections.model.User;
import com.connections.service.RoleService;
import com.connections.service.UserService;

@SpringBootApplication
public class ConnectionsApplication  implements CommandLineRunner {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(ConnectionsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Role role = new Role("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        roleService.save(role);

		User user1=new User();
		user1.setUsername("pera");
		user1.setConnections(new HashMap<>());
		user1.setRoles(roles);
		user1.setPrivate(true);
		userService.save(user1);

		user1=new User();
		user1.setUsername("kina");
		user1.setRoles(roles);
		user1.setPrivate(false);
		user1.setConnections(new HashMap<>());
		userService.save(user1);


	}

}
