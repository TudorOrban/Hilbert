package com.hilbert.features.chat.controller;

import com.hilbert.features.chat.dto.CreateMessageDto;
import com.hilbert.features.chat.dto.MessageSearchDto;
import com.hilbert.features.chat.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ChatLiveController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatLiveController(
            SimpMessagingTemplate messagingTemplate,
            ChatMessageService chatMessageService
    ) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload CreateMessageDto messageDto) {
        // Validate and persist message
        MessageSearchDto savedMessage = chatMessageService.createMessage(messageDto);

        String destination = "/topic/chat/" + messageDto.getChatId();
        messagingTemplate.convertAndSend(destination, savedMessage);
    }
}
