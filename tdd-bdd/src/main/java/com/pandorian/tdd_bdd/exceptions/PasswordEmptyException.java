package com.pandorian.tdd_bdd.exceptions;

public class PasswordEmptyException extends RuntimeException {
    public PasswordEmptyException() {
        super("Password Cannot Be Empty.");
    }
}
