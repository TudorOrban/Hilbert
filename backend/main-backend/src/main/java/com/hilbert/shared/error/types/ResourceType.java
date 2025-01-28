package com.hilbert.shared.error.types;

public enum ResourceType {
    USER,
    ARTICLE,
    VOCABULARY,
    COMMENT;

    @Override
    public String toString() {
        return switch (this) {
            case USER -> "User";
            case ARTICLE -> "Article";
            case VOCABULARY -> "Vocabulary";
            case COMMENT -> "Comment";
        };
    }
}
