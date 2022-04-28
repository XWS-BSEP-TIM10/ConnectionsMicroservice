package com.connections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.connections.model.Role;
import com.connections.service.RoleService;

@SpringBootApplication
public class ConnectionsApplication  implements CommandLineRunner {
	
	@Autowired
	private RoleService roleService;

	public static void main(String[] args) {
		SpringApplication.run(ConnectionsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		roleService.save(new Role("ROLE_USER"));
	}

}
