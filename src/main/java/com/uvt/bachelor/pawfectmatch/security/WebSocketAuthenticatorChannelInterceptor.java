package com.uvt.bachelor.pawfectmatch.security;

import com.sun.security.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

@Configuration
public class WebSocketAuthenticatorChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authToken = accessor.getFirstNativeHeader("Authorization");
            if (authToken != null && jwtTokenUtil.validateToken(authToken)) {
                String username = jwtTokenUtil.getUsernameFromToken(authToken);
                accessor.setUser(new UserPrincipal(username));
            } else {
                throw new IllegalArgumentException("Invalid JWT token");
            }
        }
        return message;
    }
}
