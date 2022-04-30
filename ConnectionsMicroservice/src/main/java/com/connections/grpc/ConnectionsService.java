package com.connections.grpc;

import com.connections.model.User;
import com.connections.service.ConnectionService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import proto.ConnectionsGrpcServiceGrpc;
import proto.ConnectionsProto;
import proto.ConnectionsResponseProto;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class ConnectionsService extends ConnectionsGrpcServiceGrpc.ConnectionsGrpcServiceImplBase {

    private ConnectionService connectionService;

    @Autowired
    public ConnectionsService(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @Override
    public void getConnections(ConnectionsProto request, StreamObserver<ConnectionsResponseProto> responseObserver){

        List<String> connections = new ArrayList<String>();
        connections.addAll(connectionService.getFollowing());
        connections.addAll(connectionService.getFollowers());

        ConnectionsResponseProto response  = ConnectionsResponseProto.newBuilder()
                .addAllConnections(connections)
                .setStatus("Status 200").build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

}
