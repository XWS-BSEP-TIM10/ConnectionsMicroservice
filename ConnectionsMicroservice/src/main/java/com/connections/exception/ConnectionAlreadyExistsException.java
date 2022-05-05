package com.connections.exception;

public class ConnectionAlreadyExistsException extends RuntimeException {
    public ConnectionAlreadyExistsException() {
        super("Connection already exists!");
    }
}
