package com.pandorian.tdd_bdd.exceptions;

public class UserAlreadyExistsException extends ApplicationException {
    public UserAlreadyExistsException() {
        super("User Already Exists", 409);
    }
}
