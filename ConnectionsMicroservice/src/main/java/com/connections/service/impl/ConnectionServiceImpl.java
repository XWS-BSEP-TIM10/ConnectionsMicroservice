package com.connections.service.impl;

import com.connections.exception.UserDoesNotExist;
import com.connections.model.ConnectionStatus;
import com.connections.model.Connection;
import com.connections.model.User;
import com.connections.repository.ConnectionRepository;
import com.connections.service.ConnectionService;
import com.connections.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Connection sendConnectionRequest(String username) throws UserDoesNotExist {

        User loggedUser = userService.findByUsername("kina"); //uzeti ulogovanog usera iz konteksta
        User user = userService.findByUsername(username);

        if(user == null){throw new UserDoesNotExist();}

        ConnectionStatus status = user.getPrivate() ? ConnectionStatus.PENDING : ConnectionStatus.CONNECTED;

        return connectionRepository.saveConnection(loggedUser.getUsername(), user.getUsername(), status.toString());
    }

    @Override
    public Connection approveConnectionRequest(String username) throws UserDoesNotExist {
        User loggedUser = userService.findByUsername("pera"); //uzeti ulogovanog usera iz konteksta
        if(userService.findByUsername(username) == null){throw new UserDoesNotExist();}
        return  connectionRepository.updateConnectionStatus(loggedUser.getUsername(), username, "CONNECTED");
    }

    @Override
    public Connection refuseConnectionRequest(String username) throws UserDoesNotExist {
        User loggedUser = userService.findByUsername("pera"); //uzeti ulogovanog usera iz konteksta
        if(userService.findByUsername(username) == null){throw new UserDoesNotExist();}
        return  connectionRepository.updateConnectionStatus(loggedUser.getUsername(), username, "REFUSED");
    }
}
