package com.connections.service;

import java.util.List;

import com.connections.exception.UserDoesNotExist;
import com.connections.model.Connection;
import com.connections.model.User;

public interface ConnectionService {

    Connection sendConnectionRequest(String id) throws UserDoesNotExist;

    Connection approveConnectionRequest(String id) throws UserDoesNotExist;

    Connection refuseConnectionRequest(String id) throws UserDoesNotExist;
    
    List<String> getFollowing(String id);
    
    List<String> getFollowers(String id);

}
