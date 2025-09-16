package com.example.backend.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            // For CONNECT frames, extract authentication from session attributes
            Object securityContext = accessor.getSessionAttributes().get("SPRING_SECURITY_CONTEXT");
            if (securityContext instanceof SecurityContext) {
                SecurityContext context = (SecurityContext) securityContext;
                Authentication authentication = context.getAuthentication();
                if (authentication != null && authentication.isAuthenticated()) {
                    SecurityContextHolder.setContext(context);
                }
            }
        } else if (StompCommand.SEND.equals(accessor.getCommand()) ||
                StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            // For SEND and SUBSCRIBE frames, restore authentication from session
            Object securityContext = accessor.getSessionAttributes().get("SPRING_SECURITY_CONTEXT");
            if (securityContext instanceof SecurityContext) {
                SecurityContext context = (SecurityContext) securityContext;
                Authentication authentication = context.getAuthentication();
                if (authentication != null && authentication.isAuthenticated()) {
                    SecurityContextHolder.setContext(context);
                }
            }
        }

        return message;
    }
}
