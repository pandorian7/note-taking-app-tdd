package com.pandorian.tdd_bdd.exceptions;

public class PasswordEmptyException extends ApplicationException {
    public PasswordEmptyException() {
        super("Password Cannot Be Empty.", 400);
    }
}
