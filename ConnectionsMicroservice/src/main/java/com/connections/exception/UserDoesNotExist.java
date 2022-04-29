package com.connections.exception;

public class UserDoesNotExist extends Exception{
    public UserDoesNotExist() {
        super("User does not exists!");
    }
}
