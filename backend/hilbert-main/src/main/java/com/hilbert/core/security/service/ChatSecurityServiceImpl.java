package com.hilbert.core.security.service;

import com.hilbert.features.chat.model.Chat;
import com.hilbert.features.chat.repository.ChatRepository;
import com.hilbert.shared.error.types.ResourceIdentifierType;
import com.hilbert.shared.error.types.ResourceNotFoundException;
import com.hilbert.shared.error.types.ResourceType;
import com.hilbert.shared.error.types.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ChatSecurityServiceImpl implements ChatSecurityService {

    private final ChatRepository chatRepository;

    @Autowired
    public ChatSecurityServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public boolean canAccessChat(Long chatId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(auth.getName());

        Chat chat = chatRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(chatId.toString(), ResourceType.CHAT, ResourceIdentifierType.ID));

        boolean isAllowed = Objects.equals(chat.getFirstUserId(), userId) ||
                Objects.equals(chat.getSecondUserId(), userId);
        return !isAllowed;
    }
}
