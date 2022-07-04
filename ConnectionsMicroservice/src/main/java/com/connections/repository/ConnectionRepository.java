package com.connections.repository;

import com.connections.model.Connection;
import com.connections.model.User;
import org.neo4j.springframework.data.repository.Neo4jRepository;
import org.neo4j.springframework.data.repository.query.Query;

import java.util.List;

public interface ConnectionRepository extends Neo4jRepository<Connection, Long> {

    @Query("MATCH (u1:User {id: $0}), (u2:User {id: $1}) CREATE (u1)-[r:CONNECTION {connectionStatus: $2}]->(u2) RETURN r")
    Connection saveConnection(String initiatorId, String receiverId, String connectionStatus);

    @Query("MATCH (u1:User {id: $0})-[r:CONNECTION]->(u2:User {id: $1}) SET r.connectionStatus = $2 RETURN r")
    Connection updateConnectionStatus(String initiatorId, String receiverId, String connectionStatus);

    @Query("MATCH(:User {id:$0})-[:CONNECTION {connectionStatus:'CONNECTED'}]->(f:User) RETURN f.id")
    List<String> findFollowing(String id);

    @Query("MATCH(f:User)-[:CONNECTION {connectionStatus:'CONNECTED'}]->(User {id:$0}) RETURN f.id")
    List<String> findFollowers(String id);

    @Query("MATCH(f:User)-[:CONNECTION {connectionStatus:'PENDING'}]->(User {id:$0}) RETURN f.id")
    List<String> getPending(String id);

    @Query("MATCH(u1:User {id:$0})-[c:CONNECTION]->(u2:User {id:$1}) RETURN count(c) > 0")
    boolean isConnected(String id1, String id2);

    @Query("MATCH(u1:User {id:$0})-[c:CONNECTION {connectionStatus:'PENDING'}]->(u2:User {id:$1}) RETURN count(c) > 0")
    boolean isPending(String id1, String id2);

    @Query("MATCH (u1:User {id: $0})-[r:CONNECTION]->(u2:User {id: $1}) RETURN r")
    Connection getConnection(String initiatorId, String receiverId);

    @Query("MATCH(u1:User {id:$0})-[c:CONNECTION {connectionStatus:'BLOCKED'}]->(u2:User {id:$1}) RETURN count(c) > 0")
    boolean isBlocked(String id1, String id2);

    @Query("MATCH (u1:User {id: $0}), (u2:User {id: $1}) CREATE (u1)-[r:CONNECTION {connectionStatus: 'BLOCKED'}]->(u2) RETURN r")
    Connection saveBlock(String initiatorId, String receiverId);

    @Query("MATCH (u1:User {id: $0})-[r:CONNECTION]->(u2:User {id: $1}) DELETE r")
    void deleteConnection(String initiatorId, String receiverId);

    @Query("MATCH (:User {id: $0})-[:CONNECTION*2 {connectionStatus: 'CONNECTED'}]->(u:User) RETURN DISTINCT u")
    List<User> findSecondLevelConnections(String username);

}
