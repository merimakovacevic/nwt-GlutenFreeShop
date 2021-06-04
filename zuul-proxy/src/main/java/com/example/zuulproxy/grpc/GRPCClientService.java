package com.example.zuulproxy.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class GRPCClientService {

    private String grpcHost = "localhost";
    private int grpcPort = 9090;

    public String sendSystemEvent(com.example.systemevents.SystemEventRequest.LogType logType, String serviceName,
                                  String userEmail, com.example.systemevents.SystemEventRequest.Action action) {
        /*
        Ovaj dio koda je potrebno zakomentarisati, a zatim odraditi mvn clean install u terminalu
        kako bi se generisale Java klase koje smo definisali u proto fajlu.
        Staviti na kraju return ""; jer ova metoda vraca string
        Kad se odradi mvn clean install odkomentarisati i trebalo bi da radi
        */

        ManagedChannel channel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort)
                .usePlaintext()
                .build();
        com.example.systemevents.SystemEventServiceGrpc.SystemEventServiceBlockingStub stub
                = com.example.systemevents.SystemEventServiceGrpc.newBlockingStub(channel);

        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());;

        com.example.systemevents.SystemEventResponse helloResponse = stub.register(com.example.systemevents.SystemEventRequest.newBuilder()
                .setLogType(logType)
                .setServiceName(serviceName)
                .setUserEmail(userEmail)
                .setAction(action)
                .setTime(currentTime)
                .build());
        channel.shutdown();
        return helloResponse.getEventId();
    }
}