package com.hilbert.shared.error;

public enum ResourceType {
    USER;

    @Override
    public String toString() {
        return switch (this) {
            case USER -> "User";
        };
    }
}
