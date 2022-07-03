package com.connections.grpc;

import com.connections.exception.BlockAlreadyExistsException;
import com.connections.exception.NoPendingConnectionException;
import com.connections.exception.UserDoesNotExist;
import com.connections.model.Connection;
import com.connections.service.ConnectionService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import proto.BlockResponseProto;
import proto.ConnectionResponseProto;
import proto.ConnectionStatusProto;
import proto.ConnectionStatusResponseProto;
import proto.ConnectionsGrpcServiceGrpc;
import proto.ConnectionsProto;
import proto.ConnectionsResponseProto;
import proto.CreateBlockRequestProto;
import proto.CreateConnectionRequestProto;
import proto.RespondConnectionRequestProto;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class ConnectionsService extends ConnectionsGrpcServiceGrpc.ConnectionsGrpcServiceImplBase {

    private final ConnectionService connectionService;
    private static final String OK_STATUS = "Status 200";
    private static final String NOT_FOUND_STATUS = "Status 404";


    @Autowired
    public ConnectionsService(ConnectionService connectionService) {
        this.connectionService = connectionService;

    }

    @Override
    public void getConnections(ConnectionsProto request, StreamObserver<ConnectionsResponseProto> responseObserver) {

        List<String> connections = new ArrayList<>(connectionService.getFollowing(request.getId()));

        ConnectionsResponseProto response = ConnectionsResponseProto.newBuilder()
                .addAllConnections(connections)
                .setStatus(OK_STATUS).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void createConnection(CreateConnectionRequestProto request, StreamObserver<ConnectionResponseProto> responseObserver) {
        String initiatorId = request.getInitiatorId();
        String receiverId = request.getReceiverId();

        connectionService.sendConnectionRequest(initiatorId, receiverId);

        ConnectionResponseProto response = ConnectionResponseProto.newBuilder()
                .setStatus(OK_STATUS).build();


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
                .setStatus(OK_STATUS).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getConnectionStatus(ConnectionStatusProto request, StreamObserver<ConnectionStatusResponseProto> responseObserver) {
        Connection connection = connectionService.getConnection(request.getInitiatorId(), request.getReceiverId());
        ConnectionStatusResponseProto connectionStatusResponseProto;
        if (connection == null)
            connectionStatusResponseProto = ConnectionStatusResponseProto.newBuilder().setConnectionStatus("")
                    .setStatus(OK_STATUS).build();
        else
            connectionStatusResponseProto = ConnectionStatusResponseProto.newBuilder()
                    .setConnectionStatus(connection.getConnectionStatus().toString())
                    .setStatus(OK_STATUS).build();
        responseObserver.onNext(connectionStatusResponseProto);
        responseObserver.onCompleted();
    }

    @Override
    public void createBlock(CreateBlockRequestProto request, StreamObserver<BlockResponseProto> responseObserver) {
        BlockResponseProto blockResponseProto;
        try {
            connectionService.createBlock(request.getInitiatorId(), request.getReceiverId());
            blockResponseProto = BlockResponseProto.newBuilder().setStatus(OK_STATUS).build();
        } catch (UserDoesNotExist | BlockAlreadyExistsException exception) {
            blockResponseProto = BlockResponseProto.newBuilder().setStatus(NOT_FOUND_STATUS).build();
        }
        responseObserver.onNext(blockResponseProto);
        responseObserver.onCompleted();
    }
}
