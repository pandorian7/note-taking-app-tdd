package com.pandorian.tdd_bdd.exceptions;

public class UserDoesNotExistException extends ApplicationException {
    public UserDoesNotExistException(Long id) {
        super("User with id %d does not exist".formatted(id));
    }
}
