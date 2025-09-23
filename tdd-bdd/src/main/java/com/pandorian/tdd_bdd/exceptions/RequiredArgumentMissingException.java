package com.pandorian.tdd_bdd.exceptions;

public class RequiredArgumentMissingException extends RuntimeException {
    private final String propertyName;

    public RequiredArgumentMissingException(String propertyName) {
        super("Required argument missing: " + propertyName);
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
