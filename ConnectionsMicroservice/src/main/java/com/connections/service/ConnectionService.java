package com.connections.service;

import com.connections.exception.UserDoesNotExist;
import com.connections.model.Connection;

public interface ConnectionService {

    Connection sendConnectionRequest(String username) throws UserDoesNotExist;

    Connection approveConnectionRequest(String username) throws UserDoesNotExist;

    Connection refuseConnectionRequest(String username) throws UserDoesNotExist;

}
