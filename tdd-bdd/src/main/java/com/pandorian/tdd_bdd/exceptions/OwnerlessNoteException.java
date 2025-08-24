package com.pandorian.tdd_bdd.exceptions;

public class OwnerlessNoteException extends RuntimeException {
    public OwnerlessNoteException() {
        super("Note does not have an owner");
    }
}
