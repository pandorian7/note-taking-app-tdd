package com.pandorian.tdd_bdd.exceptions;

public class NullNoteOwnerException extends ApplicationException {
    public NullNoteOwnerException() {
        super("Note owner or owner id cannot be null when modifying", 409);
    }
}
