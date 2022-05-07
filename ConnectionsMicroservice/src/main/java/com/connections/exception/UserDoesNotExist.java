package com.connections.exception;

public class UserDoesNotExist extends RuntimeException {
    public UserDoesNotExist() {
        super("User does not exists!");
    }
}
