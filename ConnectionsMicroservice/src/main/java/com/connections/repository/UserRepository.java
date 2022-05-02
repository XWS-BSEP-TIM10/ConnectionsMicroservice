package com.connections.repository;

import com.connections.model.User;
import org.neo4j.springframework.data.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends Neo4jRepository<User, String> {

    User findByUsername(String username);
}
