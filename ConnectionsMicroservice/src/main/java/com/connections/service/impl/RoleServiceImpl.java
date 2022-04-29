package com.connections.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connections.model.Role;
import com.connections.repository.RoleRepository;
import com.connections.service.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;
	


	@Override
	public Role save(Role role) {
		return roleRepository.save(role);
		
	}

	@Override
	public List<Role> findByName(String name) {
		return roleRepository.findByName(name);
	}

}
