package com.pandorian.tdd_bdd.exceptions;

public class IncompleteUserSignupException extends RuntimeException {
    public IncompleteUserSignupException() {
        super("All fields (username, password, firstName, lastName) are required for signup.");
    }
}
