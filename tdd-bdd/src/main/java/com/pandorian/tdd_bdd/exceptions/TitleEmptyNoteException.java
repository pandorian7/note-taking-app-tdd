package com.pandorian.tdd_bdd.exceptions;

public class TitleEmptyNoteException extends ApplicationException {
    public TitleEmptyNoteException() {
        super("Note Title is required", 400);
    }
}
