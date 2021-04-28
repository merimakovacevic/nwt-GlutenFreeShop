package com.example.systemevents;

import com.example.systemevents.model.SystemEvent;
import com.example.systemevents.repository.SystemEventRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class SystemEventsServiceImpl extends SystemEventServiceGrpc.SystemEventServiceImplBase {

    private SystemEventRepository systemEventRepository;

    public SystemEventsServiceImpl(SystemEventRepository systemEventRepository) {
        this.systemEventRepository = systemEventRepository;
    }

    @Override
    public void register(SystemEventRequest request, StreamObserver<SystemEventResponse> responseObserver) {
        SystemEvent systemEvent = new SystemEvent();

        systemEvent.setEventName(request.getEventName());
        systemEvent.setEventType(request.getEventType());
        systemEvent.setServiceName(request.getServiceName());
        systemEvent.setTime(request.getTime());
        systemEvent.setUser(request.getUser());


        systemEvent = systemEventRepository.save(systemEvent);


        SystemEventResponse response = SystemEventResponse.newBuilder()
                .setEventId("" + systemEvent.getId())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}