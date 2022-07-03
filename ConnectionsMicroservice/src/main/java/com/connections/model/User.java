package com.connections.model;

import com.connections.dto.NewUserDto;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Property;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.springframework.data.annotation.Id;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Node("User")
public class User {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Property
    private Boolean isPrivate;

    @Relationship(type = "HAS_ROLE", direction = Relationship.Direction.INCOMING)
    private List<Role> roles;

    @Relationship(type = "CONNECTED", direction = Relationship.Direction.INCOMING)
    private Map<User, Connection> connections;

    public User() {
        super();
        this.connections = new HashMap<>();
    }

    public User(String uuid, Boolean isPrivate, List<Role> roles, Map<User, Connection> connections) {
        this.id = uuid;
        this.isPrivate = isPrivate;
        this.roles = roles;
        this.connections = connections;
    }

    public User(NewUserDto dto) {
        this.id = dto.getUuid();
        this.isPrivate = false;
        this.connections = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setConnections(Map<User, Connection> connections) {
        this.connections = connections;
    }

    public Map<User, Connection> getConnections() {
        return connections;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

  
}
