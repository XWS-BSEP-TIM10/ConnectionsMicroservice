package com.connections.service;

import com.connections.exception.UserDoesNotExist;
import com.connections.model.Connection;

import java.util.List;

public interface ConnectionService {

    Connection sendConnectionRequest(String initiatorId, String connectingId) throws UserDoesNotExist;

    Boolean respondConnectionRequest(String initiatorId, String connectingId, boolean approve) throws UserDoesNotExist;

    List<String> getFollowing(String id);

    List<String> getFollowers(String id);

    Connection getConnection(String initiatorId, String receiverId);

}
