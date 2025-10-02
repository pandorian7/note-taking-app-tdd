package com.pandorian.tdd_bdd.exceptions;

public class UserNotAuthorized extends ApplicationException {
    public UserNotAuthorized() {
        super("User Not Authorized", 401);
    }
}
