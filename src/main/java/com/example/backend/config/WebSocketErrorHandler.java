package com.example.backend.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebSocketErrorHandler extends StompSubProtocolErrorHandler {

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(clientMessage);
        String destination = accessor.getDestination();

        // Create error response
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", true);
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("timestamp", System.currentTimeMillis());

        // Convert to JSON
        String errorJson = "{\"error\":true,\"message\":\"" + ex.getMessage() + "\",\"timestamp\":"
                + System.currentTimeMillis() + "}";

        // Create error message
        StompHeaderAccessor errorAccessor = StompHeaderAccessor.create(StompCommand.ERROR);
        errorAccessor.setMessage(ex.getMessage());
        errorAccessor.setDestination(destination);
        errorAccessor.setContentType(org.springframework.util.MimeTypeUtils.APPLICATION_JSON);

        return MessageBuilder.createMessage(errorJson.getBytes(), errorAccessor.getMessageHeaders());
    }
}
