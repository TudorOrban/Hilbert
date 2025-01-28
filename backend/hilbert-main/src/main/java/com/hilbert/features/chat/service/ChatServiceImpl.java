package com.hilbert.features.chat.service;

import com.hilbert.features.chat.repository.ChatMessageRepository;
import com.hilbert.features.chat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatServiceImpl(
            ChatRepository chatRepository,
            ChatMessageRepository chatMessageRepository
    ) {
        this.chatRepository = chatRepository;
        this.chatMessageRepository = chatMessageRepository;
    }
}
