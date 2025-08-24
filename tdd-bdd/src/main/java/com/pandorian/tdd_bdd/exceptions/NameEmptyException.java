package com.pandorian.tdd_bdd.exceptions;

public class NameEmptyException extends RuntimeException {
    public NameEmptyException() {
        super("First Name and Last Name Required");
    }
}
