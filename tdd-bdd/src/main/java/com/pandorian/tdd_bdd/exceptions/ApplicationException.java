package com.pandorian.tdd_bdd.exceptions;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final int statusCode;
    public ApplicationException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

}
