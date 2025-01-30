package com.hilbert.features.botchat.controller;

import com.hilbert.features.botchat.dto.BotChatMessageSearchDto;
import com.hilbert.features.botchat.dto.CreateBotChatMessageDto;
import com.hilbert.features.botchat.service.BotChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class BotChatLiveController {

    private final SimpMessagingTemplate messagingTemplate;
    private final BotChatMessageService chatMessageService;

    @Autowired
    public BotChatLiveController(
            SimpMessagingTemplate messagingTemplate,
            BotChatMessageService chatMessageService
    ) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping("/botChat.sendMessage")
    public void sendMessage(@Payload CreateBotChatMessageDto messageDto) {
        // Validate and persist message
        BotChatMessageSearchDto savedMessage = chatMessageService.createMessage(messageDto);

        String destination = "/topic/bot-chat/" + messageDto.getBotChatId();
        messagingTemplate.convertAndSend(destination, savedMessage);
    }
}
