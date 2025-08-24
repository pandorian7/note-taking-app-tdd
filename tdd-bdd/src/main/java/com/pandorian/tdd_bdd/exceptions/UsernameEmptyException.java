package com.pandorian.tdd_bdd.exceptions;

public class UsernameEmptyException extends RuntimeException {
    public UsernameEmptyException() {
        super("Username Cannot Be Empty");
    }
}
