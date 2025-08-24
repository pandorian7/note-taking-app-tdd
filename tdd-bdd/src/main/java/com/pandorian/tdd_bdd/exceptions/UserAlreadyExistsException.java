package com.pandorian.tdd_bdd.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("User Already Exists");
    }
}
