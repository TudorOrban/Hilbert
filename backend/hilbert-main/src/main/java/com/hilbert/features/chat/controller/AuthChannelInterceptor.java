package com.hilbert.features.chat.controller;

import com.hilbert.core.security.service.ChatSecurityService;
import com.hilbert.shared.error.types.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class AuthChannelInterceptor implements ChannelInterceptor {

    private final ChatSecurityService chatSecurityService;

    @Autowired
    public AuthChannelInterceptor(ChatSecurityService chatSecurityService) {
        this.chatSecurityService = chatSecurityService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String destination = accessor.getDestination();

        if (destination != null && destination.startsWith("/topc/chat/")) {
            Long chatId = Long.parseLong(destination.substring("/topic/chat/".length()));

            if (!chatSecurityService.canAccessChat(chatId)) {
                throw new UnauthorizedException("Chat with ID: " + chatId);
            }
        }

        return message;
    }

}
