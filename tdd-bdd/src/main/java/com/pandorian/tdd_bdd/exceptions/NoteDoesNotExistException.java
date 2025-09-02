package com.pandorian.tdd_bdd.exceptions;

public class NoteDoesNotExistException extends ApplicationException {
    public NoteDoesNotExistException(Long id) {
        super("Note with id %d does not exist".formatted(id));
    }
}
