package com.hilbert.features.chat.repository;

import com.hilbert.features.chat.model.BotChat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BotChatRepository extends JpaRepository<BotChat, Long>, BotChatSearchRepository {

}
