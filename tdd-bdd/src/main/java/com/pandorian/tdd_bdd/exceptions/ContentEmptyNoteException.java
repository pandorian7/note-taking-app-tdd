package com.pandorian.tdd_bdd.exceptions;

public class ContentEmptyNoteException extends RuntimeException {
    public ContentEmptyNoteException() {
        super("Note Content Required");
    }
}
