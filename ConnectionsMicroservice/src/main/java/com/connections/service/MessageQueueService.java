package com.connections.service;

import com.connections.dto.ConnectionsResponseDto;
import com.connections.dto.NewUserDto;
import com.connections.dto.UpdateUserStatusDTO;
import com.connections.model.User;
import com.connections.service.impl.LoggerServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.istack.logging.Logger;
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class MessageQueueService {

    private Connection nats;

    private LoggerService loggerService;

    private final UserService service;

    public MessageQueueService(UserService service) {
        this.loggerService = new LoggerServiceImpl(this.getClass());
        this.service = service;
        try {
            String natsURI = System.getenv("NATS_URI") == null ? "localhost" : System.getenv("NATS_URI");
            if (natsURI.equals("localhost")) {
                nats = Nats.connect();
            } else {
                nats = Nats.connect(natsURI);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void subscribe() {
        Dispatcher dispatcher = nats.createDispatcher(msg -> {
        });

        dispatcher.subscribe("nats.connections", msg -> {

            Gson gson = new Gson();
            String json = new String(msg.getData(), StandardCharsets.UTF_8);
            NewUserDto newUserDTO = gson.fromJson(json, NewUserDto.class);
            System.out.println(newUserDTO);

            User user = service.save(new User(newUserDTO));
            ConnectionsResponseDto responseDto;

            if (user == null) {
                responseDto = new ConnectionsResponseDto(false, "failed", newUserDTO.getUuid());
                loggerService.unsuccessfulRegistration(newUserDTO.getUuid());
            }else {
                responseDto = new ConnectionsResponseDto(user.getId(), true, "success");
            }
            publish(responseDto);
        });

        dispatcher.subscribe("nats.update.user.connections", msg -> {

            Gson gson = new Gson();
            String json = new String(msg.getData(), StandardCharsets.UTF_8);
            UpdateUserStatusDTO updateUserStatusDTO = gson.fromJson(json, UpdateUserStatusDTO.class);
            service.updateStatus(updateUserStatusDTO.getId(), !updateUserStatusDTO.isProfilePublic());
        });
    }

    public void publish(ConnectionsResponseDto responseDto) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(responseDto);
        nats.publish("nats.demo.reply", json.getBytes());
    }
}
