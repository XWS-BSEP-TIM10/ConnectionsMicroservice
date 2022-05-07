package com.connections.service.impl;

import com.connections.exception.ConnectionAlreadyExistsException;
import com.connections.exception.NoPendingConnectionException;
import com.connections.exception.UserDoesNotExist;
import com.connections.model.Connection;
import com.connections.model.ConnectionStatus;
import com.connections.model.User;
import com.connections.repository.ConnectionRepository;
import com.connections.service.ConnectionService;
import com.connections.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    private UserService userService;
    private ConnectionRepository connectionRepository;

    @Autowired
    public ConnectionServiceImpl(UserService userService, ConnectionRepository connectionRepository) {
        this.userService = userService;
        this.connectionRepository = connectionRepository;
    }

    @Override
    public Connection sendConnectionRequest(String initiatorId, String connectingId) throws UserDoesNotExist {

        User loggedUser = userService.findById(initiatorId);
        User user = userService.findById(connectingId);

        if (user == null || loggedUser == null) {
            throw new UserDoesNotExist();
        }

        ConnectionStatus status = user.getPrivate() ? ConnectionStatus.PENDING : ConnectionStatus.CONNECTED;
        if (connectionRepository.isConnected(initiatorId, connectingId))
            throw new ConnectionAlreadyExistsException();

        return connectionRepository.saveConnection(loggedUser.getId(), user.getId(), status.toString());
    }

    @Override
    public Boolean respondConnectionRequest(String initiatorId, String connectingId, boolean approve) throws UserDoesNotExist {
        User loggedUser = userService.findById(initiatorId);
        if (userService.findById(connectingId) == null || loggedUser == null) {
            throw new UserDoesNotExist();
        }
        if (!connectionRepository.isPending(initiatorId, connectingId))
            throw new NoPendingConnectionException();
        String status = approve ? "CONNECTED" : "REFUSED";
        connectionRepository.updateConnectionStatus(connectingId, initiatorId, status);
        return true;
    }

    @Override
    public List<String> getFollowing(String id) {
        return connectionRepository.findFollowing(id);
    }

    @Override
    public List<String> getFollowers(String id) {
        return connectionRepository.findFollowers(id);
    }


}
