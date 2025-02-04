package com.hilbert.features.botchat.dto;

import com.hilbert.shared.common.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBotChatDto {
    private Long userId;
    private Language language;
}

