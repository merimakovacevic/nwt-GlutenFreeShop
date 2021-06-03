//package com.example.ratingmicroservice.grpc;
//
////import com.example.systemevents.SystemEventRequest;
////import com.example.systemevents.SystemEventResponse;
////import com.example.systemevents.SystemEventServiceGrpc;
////import io.grpc.ManagedChannel;
////import io.grpc.ManagedChannelBuilder;
//import org.springframework.stereotype.Service;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@Service
//public class SystemEventService {
//    public String sendSystemEvent(SystemEventRequest.LogType logType, String serviceName, Long userId, String action, String reqBody) {
//
//        /*
//        Ovaj dio koda je potrebno zakomentarisati, a zatim odraditi mvn clean install u terminalu
//        kako bi se generisale Java klase koje smo definisali u proto fajlu.
//        Staviti na kraju return ""; jer ova metoda vraca string
//        Kad se odradi mvn clean install odkomentarisati i trebalo bi da radi
//        */
//
//        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
//                .usePlaintext()
//                .build();
//        SystemEventServiceGrpc.SystemEventServiceBlockingStub stub
//                = SystemEventServiceGrpc.newBlockingStub(channel);
//
//        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());;
//
//        SystemEventResponse helloResponse = stub.register(SystemEventRequest.newBuilder()
//                .setLogType(logType)
//                .setServiceName(serviceName)
//                .setUserId(userId.intValue())
//                .setAction(action)
//                .setRequestBody(reqBody)
//                .setTime(currentTime)
//                .build());
//        channel.shutdown();
//        return helloResponse.getEventId();
//    }
//}
