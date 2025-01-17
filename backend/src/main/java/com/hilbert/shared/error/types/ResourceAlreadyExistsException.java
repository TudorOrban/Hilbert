package com.hilbert.shared.error.types;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String identifier, ResourceType resourceType, ResourceIdentifierType identifierType) {
        super("The " + resourceType.toString() +
                " with " + identifierType.toString() + ": " + identifier +
                " already exists.");
    }
}
