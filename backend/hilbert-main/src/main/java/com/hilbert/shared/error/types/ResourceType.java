package com.hilbert.shared.error.types;

public enum ResourceType {
    USER,
    ARTICLE,
    COMMENT,
    VOCABULARY,
    CHAT,
    MESSAGE;

    @Override
    public String toString() {
        return switch (this) {
            case USER -> "User";
            case ARTICLE -> "Article";
            case COMMENT -> "Comment";
            case VOCABULARY -> "Vocabulary";
            case CHAT -> "Chat";
            case MESSAGE -> "Message";
        };
    }
}
