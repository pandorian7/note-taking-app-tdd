package com.pandorian.tdd_bdd.exceptions;

public class NoteDoesNotExistException extends RuntimeException {
    public NoteDoesNotExistException(Long id) {
        super("Note with id %d does not exist".formatted(id));
    }
}
