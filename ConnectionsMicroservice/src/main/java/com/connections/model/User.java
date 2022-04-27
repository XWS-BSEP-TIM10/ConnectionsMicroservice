package com.connections.model;

import com.connections.dto.NewUserDto;
import org.hibernate.validator.constraints.UniqueElements;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Property;
import org.springframework.data.annotation.Id;

@Node("User")
public class User {

	@Id
	@GeneratedValue
	private Long id;
	@Property("username")
	private String username;

	public User() {
		super();
	}

	public User(NewUserDto dto){
		this.username = dto.getUsername();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
