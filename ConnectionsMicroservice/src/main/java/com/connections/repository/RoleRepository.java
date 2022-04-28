package com.connections.repository;

import java.util.List;

import org.neo4j.springframework.data.repository.Neo4jRepository;
import org.neo4j.springframework.data.repository.query.Query;

import com.connections.model.Role;

public interface RoleRepository extends Neo4jRepository<Role, Long>{
	
   // List<Role> findByName(String name);
}
