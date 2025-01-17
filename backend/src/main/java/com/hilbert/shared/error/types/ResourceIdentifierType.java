package com.hilbert.shared.error.types;

public enum ResourceIdentifierType {
    ID,
    USERNAME,
    EMAIL;

    @Override
    public String toString() {
        return switch (this) {
            case ID -> "ID";
            case USERNAME -> "Username";
            case EMAIL -> "Email";
        };
    }
}
