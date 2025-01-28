package com.hilbert.features.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<MessageSearchDto> messages;
}
