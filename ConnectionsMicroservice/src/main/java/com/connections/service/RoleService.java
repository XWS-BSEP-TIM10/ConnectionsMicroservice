package com.connections.service;

import com.connections.model.Role;

import java.util.List;

public interface RoleService {
	Role save(Role role);
	List<Role> findByName(String name);
}
