package com.hilbert.features.botchat.repository;

import com.hilbert.features.botchat.model.BotChat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BotChatRepository extends JpaRepository<BotChat, Long>, BotChatSearchRepository {

}
