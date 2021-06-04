package com.example.zuulproxy.interceptor;

import com.example.systemevents.SystemEventRequest;
import com.example.zuulproxy.grpc.GRPCClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.stream.Collectors;


public class SystemEventsInterceptor implements HandlerInterceptor {
    private static Logger log = LoggerFactory.getLogger(SystemEventsInterceptor.class);

    private final GRPCClientService systemEventService;

    public SystemEventsInterceptor() {
        systemEventService = new GRPCClientService();
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        if (ex != null) {
            ex.printStackTrace();
        }
        log.info("[INFO][" + request + "][exception: " + ex + "]");


        SystemEventRequest.LogType logType = response.getStatus() == 200 ? SystemEventRequest.LogType.INFO : SystemEventRequest.LogType.ERROR;


        com.example.systemevents.SystemEventRequest.Action action = com.example.systemevents.SystemEventRequest.Action.valueOf(request.getMethod());

        String serviceName = request.getRequestURI();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userEmail;
        if (principal instanceof UserDetails) {
            userEmail = ((UserDetails)principal).getUsername();
        } else {
            userEmail = principal.toString();
        }

        systemEventService.sendSystemEvent(logType, serviceName, userEmail, action);
    }
}

