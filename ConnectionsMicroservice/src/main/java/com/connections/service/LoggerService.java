package com.connections.service;

public interface LoggerService {
    void connectionRequestSuccessfullySent(String initiatorId, String receiverId);
    void connectionAlreadyExists(String initiatorId, String receiverId);
    void connectingUserDoesNotExists(String initiatorId, String receiverId);
    void connectedUserDoesNotExists(String initiatorId, String receiverId);
    void getFollowers(String userId);
    void getFollowing(String userId);
    void getConnection(String initiatorId, String receiverId);
    void changeConnectionStatus(String initiatorId, String receiverId, String status);
    void changeConnectionStatusFailed(String initiatorId, String receiverId);
    void initiatorUserDoesNotExists(String initiatorId, String receiverId);
    void receiverUserDoesNotExists(String initiatorId, String receiverId);
    void unsuccessfulRegistration(String userId);
}
