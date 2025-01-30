package com.hilbert.features.botchat.dto;

import com.hilbert.shared.common.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BotChatInputDto {

    private String inputText;
    private List<BotChatMessageSearchDto> messages;
    private Language language;
    private Language destLanguage;
    private Boolean shouldTranslate;
}
