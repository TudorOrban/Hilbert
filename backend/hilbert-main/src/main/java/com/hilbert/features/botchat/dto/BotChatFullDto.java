package com.hilbert.features.botchat.dto;

import com.hilbert.core.user.dto.UserSmallDto;
import com.hilbert.shared.search.models.PaginatedResults;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BotChatFullDto {

    private Long id;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean lastMessageFromUser;
    private String lastMessageContent;
    private LocalDateTime lastMessageDate;
    private PaginatedResults<BotChatMessageSearchDto> messages;

    private UserSmallDto user;
}
