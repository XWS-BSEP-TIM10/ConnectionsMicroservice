package com.connections.service;

import java.util.List;

import com.connections.exception.UserDoesNotExist;
import com.connections.model.Connection;
import com.connections.model.User;

public interface ConnectionService {

    Connection sendConnectionRequest(String username) throws UserDoesNotExist;

    Connection approveConnectionRequest(String username) throws UserDoesNotExist;

    Connection refuseConnectionRequest(String username) throws UserDoesNotExist;
    
    List<String> getFollowing(String uuid);
    
    List<String> getFollowers(String uuid);

}
