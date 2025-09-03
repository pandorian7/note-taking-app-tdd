package com.pandorian.tdd_bdd.exceptions;

public class IncorrectUsernameOrPasswordException extends ApplicationException {
    public IncorrectUsernameOrPasswordException()  {
        super("Username/Password is Incorrect", 401);
    }
}
