package com.hilbert.shared.error.types;

public enum HilbertServiceType {
    HILBERT_MAIN,
    HILBERT_ML;

    @Override
    public String toString() {
        return switch (this) {
            case HILBERT_MAIN -> "Hilbert Main Service";
            case HILBERT_ML -> "Hilbert ML Service";
        };
    }
}
