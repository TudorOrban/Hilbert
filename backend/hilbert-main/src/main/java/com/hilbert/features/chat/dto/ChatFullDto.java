package com.hilbert.features.chat.dto;

import com.hilbert.core.user.dto.UserSmallDto;
import com.hilbert.shared.search.models.PaginatedResults;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatFullDto {

    private Long id;
    private Long firstUserId;
    private Long secondUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long lastMessageUserId;
    private String lastMessageContent;
    private PaginatedResults<MessageSearchDto> messages;

    private UserSmallDto firstUser;
    private UserSmallDto secondUser;
}
