package com.hilbert.shared.error.types;

public enum HilbertServiceType {
    MainService,
    TranslationService;

    @Override
    public String toString() {
        return switch (this) {
            case MainService -> "Main Service";
            case TranslationService -> "Translation Service";
        };
    }
}
