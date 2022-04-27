package com.connections.repository;

import com.connections.model.User;
import org.neo4j.springframework.data.repository.Neo4jRepository;

public interface UserRepository extends Neo4jRepository<User, Long> {

}
