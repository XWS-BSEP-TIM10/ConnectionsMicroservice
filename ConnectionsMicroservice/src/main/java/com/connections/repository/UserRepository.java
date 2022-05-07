package com.connections.repository;

import com.connections.model.User;
import org.neo4j.springframework.data.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends Neo4jRepository<User, String> {

    Optional<User> findById(String id);
}
