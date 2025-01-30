package com.hilbert.features.botchat.repository;

import com.hilbert.features.botchat.model.BotChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BotChatMessageRepository extends JpaRepository<BotChatMessage, Long> {

}
