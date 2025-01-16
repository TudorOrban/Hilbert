package com.hilbert.shared.error;

public enum ResourceIdentifierType {
    ID,
    USERNAME;

    @Override
    public String toString() {
        return switch (this) {
            case ID -> "ID";
            case USERNAME -> "Username";
        };
    }
}
