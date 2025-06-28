package com.hilbert.shared.error.types;

public enum ResourceType {
    USER,
    USER_PROFILE,
    ARTICLE,
    COMMENT,
    VOCABULARY,
    EXERCISE,
    LESSON,
    CHAT,
    MESSAGE;

    @Override
    public String toString() {
        return switch (this) {
            case USER -> "User";
            case USER_PROFILE -> "User Profile";
            case ARTICLE -> "Article";
            case COMMENT -> "Comment";
            case VOCABULARY -> "Vocabulary";
            case EXERCISE -> "Exercise";
            case LESSON -> "Lesson";
            case CHAT -> "Chat";
            case MESSAGE -> "Message";
        };
    }
}
