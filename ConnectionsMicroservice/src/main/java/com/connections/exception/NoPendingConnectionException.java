package com.connections.exception;

public class NoPendingConnectionException extends RuntimeException {
    public NoPendingConnectionException() {
        super("No pending request exists!");
    }
}
