package com.pandorian.tdd_bdd.exceptions;

public class PasswordTooShortException extends RuntimeException {
    public PasswordTooShortException(int length) {
        super("Password Should be longer than %d characters.".formatted(length));
    }
}
