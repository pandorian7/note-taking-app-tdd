package com.pandorian.tdd_bdd.exceptions;

public class NullNoteOwnerException extends RuntimeException {
    public NullNoteOwnerException() {
        super("Note owner or owner id cannot be null when modifying");
    }
}
