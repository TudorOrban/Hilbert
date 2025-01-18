package com.hilbert.shared.common.enums;

import lombok.Getter;

@Getter
public enum Language {
    NONE("none"),
    ENGLISH("en"),
    SPANISH("es"),
    FRENCH("fr"),
    GERMAN("de"),
    PORTUGUESE("pt"),
    ITALIAN("it"),
    JAPANESE("ja"),
    CHINESE("zh"),
    RUSSIAN("ru");

    private final String dbValue;

    Language(String dbValue) {
        this.dbValue = dbValue;
    }
}