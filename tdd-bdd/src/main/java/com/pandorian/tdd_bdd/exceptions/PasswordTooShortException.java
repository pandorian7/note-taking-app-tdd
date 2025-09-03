package com.pandorian.tdd_bdd.exceptions;

public class PasswordTooShortException extends ApplicationException {
    public PasswordTooShortException(int length) {
        super("Password Should be longer than %d characters.".formatted(length), 400);
    }
}
