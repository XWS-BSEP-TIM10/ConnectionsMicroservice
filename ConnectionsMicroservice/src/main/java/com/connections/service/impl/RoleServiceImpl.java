package com.connections.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connections.model.Role;
import com.connections.repository.RoleRepository;
import com.connections.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;
	


	@Override
	public void save(Role role) {
		roleRepository.save(role);
		
	}

}
