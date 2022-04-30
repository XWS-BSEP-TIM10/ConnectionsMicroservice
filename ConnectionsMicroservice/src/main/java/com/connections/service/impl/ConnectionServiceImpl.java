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
        User user = userService.findByUuid(uuid);

        if(user == null){throw new UserDoesNotExist();}

        ConnectionStatus status = user.getPrivate() ? ConnectionStatus.PENDING : ConnectionStatus.CONNECTED;

        return connectionRepository.saveConnection(loggedUser.getUuid(), user.getUuid(), status.toString());
    }

    @Override
    public Connection approveConnectionRequest(String uuid) throws UserDoesNotExist {
        User loggedUser = userService.findByUsername("pera"); //uzeti ulogovanog usera iz konteksta
        if(userService.findByUuid(uuid) == null){throw new UserDoesNotExist();}
        return  connectionRepository.updateConnectionStatus(loggedUser.getUuid(), uuid, "CONNECTED");
    }

    @Override
    public Connection refuseConnectionRequest(String uuid) throws UserDoesNotExist {
        User loggedUser = userService.findByUsername("pera"); //uzeti ulogovanog usera iz konteksta
        if(userService.findByUuid(loggedUser.getUuid()) == null){throw new UserDoesNotExist();}
        return  connectionRepository.updateConnectionStatus(loggedUser.getUuid(), uuid, "REFUSED");
    }
    
    @Override
    public List<User> getFollowing(){
    	User loggedUser = userService.findByUsername("kina");
    	return connectionRepository.findFollowing(loggedUser.getUuid());
    }
    
    @Override
    public List<User> getFollowers(){
    	User loggedUser = userService.findByUsername("kina");
    	return connectionRepository.findFollowers(loggedUser.getUuid());
    }
    
    
}
