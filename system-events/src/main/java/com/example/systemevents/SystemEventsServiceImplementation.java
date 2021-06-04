package com.example.systemevents;

import com.example.systemevents.model.SystemEvent;
import com.example.systemevents.repository.SystemEventRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class SystemEventsServiceImplementation extends SystemEventServiceGrpc.SystemEventServiceImplBase {
    private SystemEventRepository systemEventRepository;

    public SystemEventsServiceImplementation(SystemEventRepository systemEventRepository) {
        this.systemEventRepository = systemEventRepository;
    }

    @Override
    public void register(SystemEventRequest request, StreamObserver<SystemEventResponse> responseObserver) {
        SystemEvent systemEvent = new SystemEvent();

        systemEvent.setLogType(SystemEvent.LogType.valueOf(request.getLogType().name()));
        systemEvent.setServiceName(request.getServiceName());
        systemEvent.setUserEmail(request.getUserEmail());
        systemEvent.setAction(SystemEvent.Action.valueOf(request.getAction().name()));
        systemEvent.setTime(request.getTime());


        systemEvent = systemEventRepository.save(systemEvent);


        SystemEventResponse response = SystemEventResponse.newBuilder()
                .setEventId("" + systemEvent.getId())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
