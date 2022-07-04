package com.connections.exception;

public class BlockAlreadyExistsException extends RuntimeException {
    public BlockAlreadyExistsException() {
        super("Block already exists!");
    }
}
