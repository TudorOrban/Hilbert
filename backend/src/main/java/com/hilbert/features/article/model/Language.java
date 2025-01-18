package com.hilbert.features.article.model;


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

    public String getDbValue() {
        return dbValue;
    }
}