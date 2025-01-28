package com.hilbert.features.chat.repository;

import com.hilbert.features.chat.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    
}
