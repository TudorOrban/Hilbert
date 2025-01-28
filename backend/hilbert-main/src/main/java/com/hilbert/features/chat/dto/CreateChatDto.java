package com.hilbert.features.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatDto {
    private Long firstUserId;
    private Long secondUserId;
    private Long creatorUserId;
}

