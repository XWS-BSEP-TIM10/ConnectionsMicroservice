package com.connections.service.impl;

import com.connections.exception.UserDoesNotExist;
import com.connections.model.ConnectionStatus;
import com.connections.model.Connection;
import com.connections.model.User;
import com.connections.repository.ConnectionRepository;
import com.connections.service.ConnectionService;
import com.connections.service.UserService;

import java.util.List;

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
    public Connection sendConnectionRequest(String uuid) throws UserDoesNotExist {

        User loggedUser = userService.findByUsername("kina"); //uzeti ulogovanog usera iz konteksta
        User user = userService.findById(uuid);

        if(user == null){throw new UserDoesNotExist();}

        ConnectionStatus status = user.getPrivate() ? ConnectionStatus.PENDING : ConnectionStatus.CONNECTED;

        return connectionRepository.saveConnection(loggedUser.getId(), user.getId(), status.toString());
    }

    @Override
    public Connection approveConnectionRequest(String uuid) throws UserDoesNotExist {
        User loggedUser = userService.findByUsername("pera"); //uzeti ulogovanog usera iz konteksta
        if(userService.findById(uuid) == null){throw new UserDoesNotExist();}
        return  connectionRepository.updateConnectionStatus(loggedUser.getId(), uuid, "CONNECTED");
    }

    @Override
    public Connection refuseConnectionRequest(String uuid) throws UserDoesNotExist {
        User loggedUser = userService.findByUsername("pera"); //uzeti ulogovanog usera iz konteksta
        if(userService.findById(loggedUser.getId()) == null){throw new UserDoesNotExist();}
        return  connectionRepository.updateConnectionStatus(loggedUser.getId(), uuid, "REFUSED");
    }
    
    @Override
    public List<String> getFollowing(String uuid){
    	return connectionRepository.findFollowing(uuid);
    }
    
    @Override
    public List<String> getFollowers(String uuid){
    	return connectionRepository.findFollowers(uuid);
    }
    
    
}
