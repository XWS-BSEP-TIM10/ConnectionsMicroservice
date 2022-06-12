package com.connections.service.impl;

import com.connections.service.LoggerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerServiceImpl implements LoggerService {

    private final Logger logger;

    public LoggerServiceImpl(Class<?> parentClass) {this.logger = LogManager.getLogger(parentClass); }

    @Override
    public void connectionRequestSuccessfullySent(String initiatorId, String receiverId) {
        logger.info("Connection request successfully sent. Initiator id: {} Receiver id: {}", initiatorId, receiverId);
    }

    @Override
    public void connectionAlreadyExists(String initiatorId, String receiverId) {
        logger.warn("User tried to connect with existing connection. Initiator id: {} Receiver id: {}", initiatorId, receiverId);
    }

    @Override
    public void connectingUserDoesNotExists(String initiatorId, String receiverId) {
        logger.warn("User with id that does not exist tried to connect with another user. Initiator id: {} Receiver id: {}", initiatorId, receiverId);
    }

    @Override
    public void connectedUserDoesNotExists(String initiatorId, String receiverId) {
        logger.warn("User tried to connect with user that does not exist. Initiator id: {} Receiver id: {}", initiatorId, receiverId);
    }

    @Override
    public void getFollowers(String userId) {
        logger.info("Followers successfully gotten. User id: {}", userId);
    }

    @Override
    public void getFollowing(String userId) {
        logger.info("Followers successfully gotten. User id: {}", userId);
    }

    @Override
    public void getConnection(String initiatorId, String receiverId) {
        logger.info("Connection status checked successfully. Initiator id: {} Receiver id: {}", initiatorId, receiverId);
    }

    @Override
    public void changeConnectionStatus(String initiatorId, String receiverId, String status) {
        logger.info("Connection status successfully changed to: {} Initiator id: {} ReceiverId: {}", status, initiatorId, receiverId);
    }

    @Override
    public void changeConnectionStatusFailed(String initiatorId, String receiverId) {
        logger.warn("Not pending connection status tried to be changed. Initiator id: {} Receiver id: {}", initiatorId, receiverId);
    }

    @Override
    public void initiatorUserDoesNotExists(String initiatorId, String receiverId) {
        logger.warn("User with id that does not exist tried to change connection status. Initiator id: {} Receiver id: {}", initiatorId, receiverId);
    }

    @Override
    public void receiverUserDoesNotExists(String initiatorId, String receiverId) {
        logger.warn("User tried to change connection status with user that does not exist. Initiator id: {} Receiver id: {}", initiatorId, receiverId);
    }
}
