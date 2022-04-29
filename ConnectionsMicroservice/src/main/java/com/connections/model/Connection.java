package com.connections.model;

import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Property;
import org.neo4j.springframework.data.core.schema.RelationshipProperties;
import org.springframework.data.annotation.Id;

@RelationshipProperties
public class Connection {

    @Id
    @GeneratedValue
    private Long id;
    @Property("connectionStatus")
    private ConnectionStatus connectionStatus;

    public Connection() {
    }

    public Connection(ConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setConnectionStatus(ConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public Long getId() {
        return id;
    }

    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

}
