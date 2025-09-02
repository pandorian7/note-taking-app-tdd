package com.pandorian.tdd_bdd.exceptions;

public class NullNoteIdException extends ApplicationException {
    public NullNoteIdException() {
        super("Note id cannot be null when modifying");
    }
}
