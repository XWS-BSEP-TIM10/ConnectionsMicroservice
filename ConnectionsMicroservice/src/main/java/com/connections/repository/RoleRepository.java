package com.connections.repository;

import com.connections.model.Role;
import org.neo4j.springframework.data.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends Neo4jRepository<Role, Long> {

    List<Role> findByName(String name);
}
