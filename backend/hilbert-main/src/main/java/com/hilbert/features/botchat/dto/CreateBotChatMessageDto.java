package com.hilbert.features.botchat.dto;

import com.hilbert.shared.common.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBotChatMessageDto {

    private Long userId;
    private Long botChatId;
    private Boolean isUser;
    private String content;
    private Language language;
}
