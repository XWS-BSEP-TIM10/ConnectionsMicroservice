package com.connections.service.impl;

import com.connections.exception.BlockAlreadyExistsException;
import com.connections.exception.ConnectionAlreadyExistsException;
import com.connections.exception.NoPendingConnectionException;
import com.connections.exception.UserDoesNotExist;
import com.connections.model.Connection;
import com.connections.model.ConnectionStatus;
import com.connections.model.User;
import com.connections.repository.ConnectionRepository;
import com.connections.service.ConnectionService;
import com.connections.service.LoggerService;
import com.connections.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.connections.model.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    private final UserService userService;
    private final ConnectionRepository connectionRepository;
    private final LoggerService loggerService;

    @Autowired
    public ConnectionServiceImpl(UserService userService, ConnectionRepository connectionRepository) {
        this.userService = userService;
        this.connectionRepository = connectionRepository;
        this.loggerService = new LoggerServiceImpl(this.getClass());
    }

    @Override
    public Connection sendConnectionRequest(String initiatorId, String receiverId) throws UserDoesNotExist {

        User loggedUser = userService.findById(initiatorId);
        User user = userService.findById(receiverId);

        if (loggedUser == null) {
            loggerService.connectingUserDoesNotExists(initiatorId, receiverId);
            throw new UserDoesNotExist();
        }

        if (user == null) {
            loggerService.connectedUserDoesNotExists(initiatorId, receiverId);
            throw new UserDoesNotExist();
        }

        ConnectionStatus status = Boolean.TRUE.equals(user.getPrivate()) ? ConnectionStatus.PENDING : ConnectionStatus.CONNECTED;
        if (connectionRepository.isConnected(initiatorId, receiverId)) {
            loggerService.connectionAlreadyExists(initiatorId, receiverId);
            throw new ConnectionAlreadyExistsException();
        }

        loggerService.connectionRequestSuccessfullySent(initiatorId, receiverId);
        return connectionRepository.saveConnection(loggedUser.getId(), user.getId(), status.toString());
    }

    @Override
    public Boolean respondConnectionRequest(String initiatorId, String receiverId, boolean approve) throws UserDoesNotExist {
        User loggedUser = userService.findById(initiatorId);
        if (userService.findById(receiverId) == null) {
            loggerService.receiverUserDoesNotExists(initiatorId, receiverId);
            throw new UserDoesNotExist();
        }
        if (loggedUser == null) {
            loggerService.initiatorUserDoesNotExists(initiatorId, receiverId);
            throw new UserDoesNotExist();
        }
        if (!connectionRepository.isPending(initiatorId, receiverId)) {
            loggerService.changeConnectionStatusFailed(initiatorId, receiverId);
            throw new NoPendingConnectionException();
        }
        String status = approve ? "CONNECTED" : "REFUSED";
        loggerService.changeConnectionStatus(initiatorId, receiverId, status);
        connectionRepository.updateConnectionStatus(initiatorId, receiverId, status);
        return true;
    }

    @Override
    public List<String> getFollowing(String id) {
        loggerService.getFollowers(id);
        return connectionRepository.findFollowing(id);
    }

    @Override
    public List<String> getFollowers(String id) {
        loggerService.getFollowing(id);
        return connectionRepository.findFollowers(id);
    }

    @Override
    public Connection getConnection(String initiatorId, String receiverId) {
        loggerService.getConnection(initiatorId, receiverId);
        return connectionRepository.getConnection(initiatorId, receiverId);
    }

    @Override
    public Connection createBlock(String initiatorId, String receiverId) {
        User loggedUser = userService.findById(initiatorId);
        User user = userService.findById(receiverId);
        if (loggedUser == null || user == null)
            throw new UserDoesNotExist();
        if (connectionRepository.isBlocked(initiatorId, receiverId))
            throw new BlockAlreadyExistsException();
        connectionRepository.deleteConnection(initiatorId, receiverId);
        if (!connectionRepository.isBlocked(receiverId, initiatorId))
            connectionRepository.deleteConnection(receiverId, initiatorId);
        return connectionRepository.saveBlock(initiatorId, receiverId);
    }

    @Override
    public List<String> getRecommendations(String userId) {
        List<String> recommendationIds = new ArrayList<String>();
        for(User user : connectionRepository.findSecondLevelConnections(userId)){
            if(!connectionRepository.isConnected(user.getId(), userId) && !connectionRepository.isPending(user.getId(), userId))
                recommendationIds.add(user.getId());
        }
        return recommendationIds;
    }


}
