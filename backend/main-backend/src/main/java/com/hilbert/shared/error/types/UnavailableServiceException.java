package com.hilbert.shared.error.types;

public class UnavailableServiceException extends RuntimeException {

    public UnavailableServiceException(HilbertServiceType serviceType) {
        super("Unavailable Hilbert Service: " + serviceType.toString());
    }
}
