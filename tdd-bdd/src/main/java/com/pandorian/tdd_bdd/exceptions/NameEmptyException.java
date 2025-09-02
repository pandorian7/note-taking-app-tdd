package com.pandorian.tdd_bdd.exceptions;

public class NameEmptyException extends ApplicationException {
    public NameEmptyException() {
        super("First Name and Last Name Required");
    }
}
