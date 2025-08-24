package com.pandorian.tdd_bdd.exceptions;

public class NoteContentTooLongException extends RuntimeException {
    public NoteContentTooLongException(int length, int upperLimit) {
        super("Note Content Too Long! length: %d, upperLimit: %d, exceededBy: %d".formatted(
                length, upperLimit, length - upperLimit
        ));
    }
}
