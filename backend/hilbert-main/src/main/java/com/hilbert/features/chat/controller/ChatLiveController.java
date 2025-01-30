package com.hilbert.features.chat.controller;

import com.hilbert.features.chat.dto.CreateChatMessageDto;
import com.hilbert.features.chat.dto.ChatMessageSearchDto;
import com.hilbert.features.chat.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
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
    public void sendMessage(@Payload CreateChatMessageDto messageDto) {
        // Validate and persist message
        ChatMessageSearchDto savedMessage = chatMessageService.createMessage(messageDto);

        String destination = "/topic/chat/" + messageDto.getChatId();
        messagingTemplate.convertAndSend(destination, savedMessage);
    }
}
