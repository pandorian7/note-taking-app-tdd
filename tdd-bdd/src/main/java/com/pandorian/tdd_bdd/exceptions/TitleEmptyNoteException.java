package com.pandorian.tdd_bdd.exceptions;

public class TitleEmptyNoteException extends RuntimeException {
    public TitleEmptyNoteException() {
        super("Note Title is required");
    }
}
