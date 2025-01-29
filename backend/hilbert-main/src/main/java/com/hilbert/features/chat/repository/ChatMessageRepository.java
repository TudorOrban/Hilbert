package com.hilbert.features.chat.repository;

import com.hilbert.features.chat.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>, ChatMessageSearchRepository {

}
