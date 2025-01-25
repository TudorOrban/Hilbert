package com.hilbert.shared.error.types;

public enum ResourceType {
    USER,
    ARTICLE;

    @Override
    public String toString() {
        return switch (this) {
            case USER -> "User";
            case ARTICLE -> "Article";
        };
    }
}
