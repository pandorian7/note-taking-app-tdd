package com.pandorian.tdd_bdd.exceptions;

public class ContentEmptyNoteException extends ApplicationException {
    public ContentEmptyNoteException() {
        super("Note Content Required", 400);
    }
}
