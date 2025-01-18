package com.hilbert.shared.error.types;

public enum ResourceIdentifierType {
    ID,
    USERNAME,
    EMAIL,
    TITLE;

    @Override
    public String toString() {
        return switch (this) {
            case ID -> "ID";
            case USERNAME -> "Username";
            case EMAIL -> "Email";
            case TITLE -> "Title";
        };
    }
}
