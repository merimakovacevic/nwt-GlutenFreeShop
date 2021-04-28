package com.example.orderservice.grpc;

import com.example.systemevents.SystemEventRequest;
import com.example.systemevents.SystemEventResponse;
import com.example.systemevents.SystemEventServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class GRPCClientService {

    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${grpc.server.host}")
    private String grpcHost;

    @Value("${grpc.server.port}")
    private int grpcPort;

    public String sendSystemEvent(String eventName, String eventType, String user) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort)
                .usePlaintext()
                .build();
        SystemEventServiceGrpc.SystemEventServiceBlockingStub stub
                = SystemEventServiceGrpc.newBlockingStub(channel);

        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());;

        SystemEventResponse helloResponse = stub.register(SystemEventRequest.newBuilder()
                .setEventName(eventName)
                .setEventType(eventType)
                .setUser("Test User")
                .setServiceName(serviceName)
                .setTime(currentTime)
                .build());
        channel.shutdown();
        return helloResponse.getEventId();
    }
}