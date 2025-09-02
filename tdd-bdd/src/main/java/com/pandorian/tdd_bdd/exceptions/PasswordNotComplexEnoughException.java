package com.pandorian.tdd_bdd.exceptions;

public class PasswordNotComplexEnoughException extends ApplicationException{
    public PasswordNotComplexEnoughException() {
        super("Password Should have at least 1 Capital Letter, 1 Simple Letter, 1 Number, 1 Special Character");
    }
}

