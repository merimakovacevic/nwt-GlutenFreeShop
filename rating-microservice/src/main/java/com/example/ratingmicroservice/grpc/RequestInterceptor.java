//package com.example.ratingmicroservice.grpc;
//
////import com.example.systemevents.SystemEventRequest;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Enumeration;
//import java.util.stream.Collectors;
//
//public class RequestInterceptor implements HandlerInterceptor {
//    private static Logger log = LoggerFactory.getLogger(RequestInterceptor.class);
//
//    private final SystemEventService systemEventService;
//
//    public RequestInterceptor() {
//        systemEventService = new SystemEventService();
//    }
//
//    @Override
//    public void afterCompletion(
//            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//            throws Exception {
//        if (ex != null) {
//            ex.printStackTrace();
//        }
//        log.info("[INFO][" + request + "][exception: " + ex + "]");
//
//        String reqBody = request.getMethod().equals("POST") ? request.getReader().lines().collect(Collectors.joining(System.lineSeparator()))
//                : getParameters(request);
//        SystemEventRequest.LogType logType = response.getStatus() == 200 ? SystemEventRequest.LogType.INFO : SystemEventRequest.LogType.ERROR;
//
//        systemEventService.sendSystemEvent(logType, request.getRequestURI(),
//                0L, request.getMethod(), reqBody);
//    }
//
//    private String getParameters(HttpServletRequest request) {
//        StringBuffer posted = new StringBuffer();
//        Enumeration<?> e = request.getParameterNames();
//        if (e != null) {
//            posted.append("?");
//        }
//        while (e.hasMoreElements()) {
//            if (posted.length() > 1) {
//                posted.append("&");
//            }
//            String curr = (String) e.nextElement();
//            posted.append(curr + "=");
//            if (curr.contains("password")
//                    || curr.contains("pass")
//                    || curr.contains("pwd")) {
//                posted.append("*****");
//            } else {
//                posted.append(request.getParameter(curr));
//            }
//        }
//        String ip = request.getHeader("X-FORWARDED-FOR");
//        String ipAddr = (ip == null) ? getRemoteAddr(request) : ip;
//        if (ipAddr != null && !ipAddr.equals("")) {
//            posted.append("&_psip=" + ipAddr);
//        }
//        return posted.toString();
//    }
//
//    private String getRemoteAddr(HttpServletRequest request) {
//        String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
//        if (ipFromHeader != null && ipFromHeader.length() > 0) {
//            log.debug("ip from proxy - X-FORWARDED-FOR : " + ipFromHeader);
//            return ipFromHeader;
//        }
//        return request.getRemoteAddr();
//    }
//}
//
