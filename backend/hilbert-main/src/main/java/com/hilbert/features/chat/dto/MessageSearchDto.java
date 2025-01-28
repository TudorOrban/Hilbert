package com.hilbert.features.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageSearchDto {
    private Long id;
    private Long userId;
    private Long chatId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
