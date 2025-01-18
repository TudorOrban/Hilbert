package com.hilbert.features.article.model;

public enum DifficultyLevel {
    NONE("none"),
    A1("a1"),
    A2("a2"),
    B1("b1"),
    B2("b2"),
    C1("c1"),
    C2("c2");

    private final String dbValue;

    DifficultyLevel(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }
}
