package com.pandorian.tdd_bdd.exceptions;

public class UsernameEmptyException extends ApplicationException {
    public UsernameEmptyException() {
        super("Username Cannot Be Empty", 400);
    }
}
