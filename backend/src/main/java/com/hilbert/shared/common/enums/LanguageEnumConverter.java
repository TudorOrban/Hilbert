package com.hilbert.shared.common.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LanguageEnumConverter implements AttributeConverter<Language, String> {

    @Override
    public String convertToDatabaseColumn(Language attribute) {
        return attribute.getDbValue();
    }

    @Override
    public Language convertToEntityAttribute(String dbData) {
        return Language.valueOf(dbData);
    }
}
