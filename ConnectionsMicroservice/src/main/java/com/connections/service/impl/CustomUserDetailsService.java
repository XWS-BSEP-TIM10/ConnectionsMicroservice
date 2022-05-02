package com.connections.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.connections.model.User;
import com.connections.repository.UserRepository;

@Service
public class CustomUserDetailsService  implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		if (!userRepository.findById(id).isPresent()) {
			throw new UsernameNotFoundException(String.format("No user found with id '%s'.", id));
		} else {
			return userRepository.findById(id).get();
		}
	}


}
