package com.connections.grpc;

import com.connections.exception.NoPendingConnectionException;
import com.connections.exception.UserDoesNotExist;
import com.connections.model.Connection;
import com.connections.repository.ConnectionRepository;
import com.connections.service.ConnectionService;
import com.connections.service.UserService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import proto.*;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class ConnectionsService extends ConnectionsGrpcServiceGrpc.ConnectionsGrpcServiceImplBase {

    private ConnectionService connectionService;
    private UserService userService;
    private ConnectionRepository repository;

    @Autowired
    public ConnectionsService(ConnectionService connectionService, UserService userService, ConnectionRepository repository) {
        this.connectionService = connectionService;
        this.userService = userService;
        this.repository = repository;
    }

    @Override
    public void getConnections(ConnectionsProto request, StreamObserver<ConnectionsResponseProto> responseObserver) {

        List<String> connections = new ArrayList<>(connectionService.getFollowing(request.getId()));

        ConnectionsResponseProto response = ConnectionsResponseProto.newBuilder()
                .addAllConnections(connections)
                .setStatus("Status 200").build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void createConnection(CreateConnectionRequestProto request, StreamObserver<ConnectionResponseProto> responseObserver) {
        String initiatorId = request.getInitiatorId();
        String receiverId = request.getReceiverId();

        connectionService.sendConnectionRequest(initiatorId, receiverId);

        ConnectionResponseProto response = ConnectionResponseProto.newBuilder()
                .setStatus("Status 200").build();


        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void respondConnection(RespondConnectionRequestProto request, StreamObserver<ConnectionResponseProto> responseObserver) {
        String initiatorId = request.getInitiatorId();
        String receiverId = request.getReceiverId();
        boolean approve = request.getApprove();
        try {
            connectionService.respondConnectionRequest(initiatorId, receiverId, approve);
        } catch (UserDoesNotExist | NoPendingConnectionException ex) {
            ConnectionResponseProto response = ConnectionResponseProto.newBuilder()
                    .setStatus("Status 404").build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        ConnectionResponseProto response = ConnectionResponseProto.newBuilder()
                .setStatus("Status 200").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getConnectionStatus(ConnectionStatusProto request, StreamObserver<ConnectionStatusResponseProto> responseObserver) {
        Connection connection = connectionService.getConnection(request.getInitiatorId(), request.getReceiverId());
        ConnectionStatusResponseProto connectionStatusResponseProto;
        if(connection == null)
            connectionStatusResponseProto = ConnectionStatusResponseProto.newBuilder().setConnectionStatus("")
                                                .setStatus("Status 200").build();
        else
            connectionStatusResponseProto = ConnectionStatusResponseProto.newBuilder()
                .setConnectionStatus(connection.getConnectionStatus().toString())
                .setStatus("Status 200").build();
        responseObserver.onNext(connectionStatusResponseProto);
        responseObserver.onCompleted();
    }
}
