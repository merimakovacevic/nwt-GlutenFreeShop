package com.example.systemevents;

import com.example.systemevents.model.Enum;
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

        systemEvent.setLogType(Enum.LogType.valueOf(request.getLogType().name()));
        systemEvent.setServiceName(request.getServiceName());
        systemEvent.setUserId(request.getUserId());
        systemEvent.setAction(request.getAction());
        systemEvent.setRequestBody(request.getRequestBody());
        systemEvent.setTime(request.getTime());


        systemEvent = systemEventRepository.save(systemEvent);


        SystemEventResponse response = SystemEventResponse.newBuilder()
                .setEventId("" + systemEvent.getId())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
