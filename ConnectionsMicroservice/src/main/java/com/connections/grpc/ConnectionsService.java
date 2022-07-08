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
import proto.CreateConnectionResponseProto;
import proto.MutualsResponseProto;
import proto.PendingRequestProto;
import proto.PendingResponseProto;
import proto.RecommendationsProto;
import proto.RecommendationsResponseProto;
import proto.RespondConnectionRequestProto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public void createConnection(CreateConnectionRequestProto request, StreamObserver<CreateConnectionResponseProto> responseObserver) {
        String initiatorId = request.getInitiatorId();
        String receiverId = request.getReceiverId();
        CreateConnectionResponseProto response;
        try {
            Connection connection = connectionService.sendConnectionRequest(initiatorId, receiverId);
            response = CreateConnectionResponseProto.newBuilder()
                    .setStatus(OK_STATUS)
                    .setConnectionStatus(connection.getConnectionStatus().toString())
                    .build();
        } catch (Exception e) {
            response = CreateConnectionResponseProto.newBuilder()
                    .setStatus("Status 400")
                    .build();
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void respondConnection(RespondConnectionRequestProto request, StreamObserver<ConnectionResponseProto> responseObserver) {
        String initiatorId = request.getInitiatorId();
        String receiverId = request.getReceiverId();
        boolean approve = request.getApprove();
        ConnectionResponseProto response;
        try {
            connectionService.respondConnectionRequest(initiatorId, receiverId, approve);
            response = ConnectionResponseProto.newBuilder()
                    .setStatus(OK_STATUS).build();
        } catch (UserDoesNotExist | NoPendingConnectionException ex) {
            response = ConnectionResponseProto.newBuilder()
                    .setStatus(NOT_FOUND_STATUS).build();
        }
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

    @Override
    public void getRecommendations(RecommendationsProto request, StreamObserver<RecommendationsResponseProto> responseObserver) {
        RecommendationsResponseProto recommendationsResponseProto = RecommendationsResponseProto.newBuilder()
                .addAllUserId(connectionService.getRecommendations(request.getUserId()))
                .build();
        responseObserver.onNext(recommendationsResponseProto);
        responseObserver.onCompleted();
    }

    @Override
    public void getPending(PendingRequestProto request, StreamObserver<PendingResponseProto> responseObserver) {
        PendingResponseProto pendingResponseProto = PendingResponseProto.newBuilder()
                .addAllUserId(connectionService.getPending(request.getUserId()))
                .build();
        responseObserver.onNext(pendingResponseProto);
        responseObserver.onCompleted();
    }
    @Override
    public void getMutuals(ConnectionsProto request, StreamObserver<MutualsResponseProto> responseObserver) {
    	List<String> followers = connectionService.getFollowers(request.getId());
    	List<String> following = connectionService.getFollowing(request.getId());
    	List<String> intersect = followers.stream()
                .filter(following::contains)
                .collect(Collectors.toList());
    	MutualsResponseProto mutualsResponseProto = MutualsResponseProto.newBuilder()
                 .addAllUserId(intersect)
                 .build();
         responseObserver.onNext(mutualsResponseProto);
         responseObserver.onCompleted();
    }
    
    @Override
    public void getFollowers(ConnectionsProto request, StreamObserver<ConnectionsResponseProto> responseObserver) {

        List<String> connections = new ArrayList<>(connectionService.getFollowers(request.getId()));

        ConnectionsResponseProto response = ConnectionsResponseProto.newBuilder()
                .addAllConnections(connections)
                .setStatus(OK_STATUS).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}
