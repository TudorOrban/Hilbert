package com.hilbert.shared.common.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LevelEnumConverter implements AttributeConverter<DifficultyLevel, String> {

    @Override
    public String convertToDatabaseColumn(DifficultyLevel attribute) {
        return attribute.getDbValue();
    }

    @Override
    public DifficultyLevel convertToEntityAttribute(String dbData) {
        return DifficultyLevel.valueOf(dbData);
    }
}
