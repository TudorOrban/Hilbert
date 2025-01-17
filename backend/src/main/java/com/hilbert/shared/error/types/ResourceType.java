package com.hilbert.shared.error.types;

public enum ResourceType {
    USER;

    @Override
    public String toString() {
        return switch (this) {
            case USER -> "User";
        };
    }
}
