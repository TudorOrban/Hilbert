package com.hilbert.features.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatMessageDto {

    private Long userId;
    private Long chatId;
    private String content;
}
