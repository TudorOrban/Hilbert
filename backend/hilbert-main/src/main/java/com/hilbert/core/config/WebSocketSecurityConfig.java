package com.hilbert.core.config;

import com.hilbert.core.security.service.ChatSecurityService;
import com.hilbert.features.chat.controller.AuthChannelInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

//@Configuration
//@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketSecurityConfig implements WebSocketMessageBrokerConfigurer {

    private final ChatSecurityService chatSecurityService;

    @Autowired
    public WebSocketSecurityConfig(ChatSecurityService chatSecurityService) {
        this.chatSecurityService = chatSecurityService;
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new AuthChannelInterceptor(chatSecurityService));
    }
}
