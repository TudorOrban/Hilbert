package com.hilbert.features.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMessageDto {

    private Long userId;
    private Long chatId;
    private String content;
}
