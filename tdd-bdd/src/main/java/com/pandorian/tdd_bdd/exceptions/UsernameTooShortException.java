package com.pandorian.tdd_bdd.exceptions;

public class UsernameTooShortException extends RuntimeException{
    public UsernameTooShortException(int length) {
        super("Username Should be longer than %d characters.".formatted(length));
    }
}
