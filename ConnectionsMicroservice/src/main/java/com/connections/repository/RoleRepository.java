package com.connections.repository;

import java.util.List;

import org.neo4j.springframework.data.repository.Neo4jRepository;
import org.neo4j.springframework.data.repository.query.Query;
import org.springframework.stereotype.Repository;

import com.connections.model.Role;

@Repository
public interface RoleRepository extends Neo4jRepository<Role, Long>{
	
   // List<Role> findByName(String name);
}
