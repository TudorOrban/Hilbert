package com.hilbert.features.chat.repository;

import com.hilbert.features.chat.model.BotChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BotChatMessageRepository extends JpaRepository<BotChatMessage, Long> {

}
