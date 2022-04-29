package com.connections.repository;

import com.connections.model.Connection;
import org.neo4j.springframework.data.repository.Neo4jRepository;
import org.neo4j.springframework.data.repository.query.Query;

public interface ConnectionRepository extends Neo4jRepository<Connection, Long> {

    @Query("MATCH (u1:User {username: $0}), (u2:User {username: $1}) CREATE (u1)-[r:CONNECTION {connectionStatus: $2}]->(u2) RETURN r")
    Connection saveConnection(String connectingUser, String connectedUser, String connectionStatus);

    @Query("MATCH (u1:User {username: $1})-[r:CONNECTION]->(u2:User {username: $0}) SET r.connectionStatus = $2 RETURN r")
    Connection updateConnectionStatus(String connectedUser, String connectingUser, String connectionStatus);
}
