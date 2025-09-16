package com.example.backend.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeHandler;

import java.util.Map;

public class AuthenticationHandshakeHandler implements HandshakeHandler {

    @Override
    public boolean doHandshake(ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Map<String, Object> attributes) {

        // Get the current authentication from SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // Store the authentication in the WebSocket session attributes
            attributes.put("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            return true;
        }

        // Allow connection even without authentication for now
        // The actual authorization will be checked in the message handler
        return true;
    }
}
