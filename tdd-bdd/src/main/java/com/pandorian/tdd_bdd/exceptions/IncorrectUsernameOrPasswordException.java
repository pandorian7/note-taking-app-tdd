package com.pandorian.tdd_bdd.exceptions;

public class IncorrectUsernameOrPasswordException extends RuntimeException {
    public IncorrectUsernameOrPasswordException()  {
        super("Username/Password is Incorrect");
    }
}
