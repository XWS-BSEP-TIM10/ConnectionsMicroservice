package com.connections.model;

import com.connections.model.Role;
import com.connections.dto.NewUserDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Property;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Node("User")
public class User implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;

	@Property
	private String uuid;

	@Property("username")
	private String username;
	
	@Relationship(type = "HAS_ROLE", direction = Relationship.Direction.INCOMING)
	private List<Role> roles;

	public User() {
		super();
	}

	public User(NewUserDto dto){
		this.username = dto.getUsername();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	
    public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
    public String getUsername() {
        return username;
    }
    

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	 List<GrantedAuthority> authorities = new ArrayList<>();
         for (Role role : this.getRoles()) {
             authorities.add(new SimpleGrantedAuthority(role.getName()));
         }
         return authorities;
    }

    
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return "";
	}
}
