package com.pandorian.tdd_bdd.exceptions;

public class OwnerlessNoteException extends ApplicationException {
    public OwnerlessNoteException() {
        super("Note does not have an owner", 409);
    }
}
