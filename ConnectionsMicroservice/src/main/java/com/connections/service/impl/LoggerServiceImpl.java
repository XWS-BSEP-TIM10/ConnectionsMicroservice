package com.connections.service.impl;

import com.connections.service.LoggerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerServiceImpl implements LoggerService {

    private final Logger logger;

    public LoggerServiceImpl(Class<?> parentClass) {this.logger = LogManager.getLogger(parentClass); }

    @Override
    public void connectionRequestSuccessfullySent(String initiatorId, String receiverId) {
        logger.info("User with id: " + initiatorId + " successfully sent connecting request to user with id: " + receiverId);
    }

    @Override
    public void connectionAlreadyExists(String initiatorId, String receiverId) {
        logger.warn("User with id: " + initiatorId + " already connected to user with id: " + receiverId + " tried to connect again");
    }

    @Override
    public void connectingUserDoesNotExists(String initiatorId, String receiverId) {
        logger.warn("User with id: " + initiatorId + " that does not exists tried to connect with user with id: " + receiverId);
    }

    @Override
    public void connectedUserDoesNotExists(String initiatorId, String receiverId) {
        logger.warn("User with id: " + initiatorId + " tried to connect to user with id: " + receiverId + " that does not exists");
    }

    @Override
    public void getFollowers(String userId) {
        logger.info("User with id: " + userId + " has gotten his followers");
    }

    @Override
    public void getFollowing(String userId) {
        logger.info("User with id: " + userId + " has gotten following users");
    }

    @Override
    public void getConnection(String initiatorId, String receiverId) {
        logger.info("User with id: " + initiatorId + " checked connection status with user with id: " + receiverId);
    }

    @Override
    public void changeConnectionStatus(String initiatorId, String receiverId, String status) {
        logger.info("User with id: " + initiatorId + " change status of connection with user with id: " + receiverId + " to: " + status);
    }

    @Override
    public void changeConnectionStatusFailed(String initiatorId, String receiverId) {
        logger.warn("User with id: " + initiatorId + " tried to change status of connection that is not pending with user with id: " + receiverId);
    }

    @Override
    public void initiatorUserDoesNotExists(String initiatorId, String receiverId) {
        logger.warn("User with id: " + initiatorId + " that does not exists tried to change status of connection with user with id: " + receiverId);
    }

    @Override
    public void receiverUserDoesNotExists(String initiatorId, String receiverId) {
        logger.warn("User with id: " + initiatorId + " tried to change connection status with user with id: " + receiverId + " that does not exists");
    }
}
