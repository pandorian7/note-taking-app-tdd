package com.pandorian.tdd_bdd.exceptions;

public class UsernameTooShortException extends ApplicationException {
    public UsernameTooShortException(int length) {
        super("Username Should be longer than %d characters.".formatted(length), 400);
    }
}
