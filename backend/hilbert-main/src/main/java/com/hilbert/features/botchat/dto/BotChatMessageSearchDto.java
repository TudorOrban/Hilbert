package com.hilbert.features.botchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BotChatMessageSearchDto {
    private Long id;
    private Long userId;
    private Long botChatId;
    private Boolean isUser;
    private String content;
    private String translation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
