package com.pandorian.tdd_bdd.exceptions;

public class ActionIsForbidden extends ApplicationException {
    public ActionIsForbidden() {
        super("Resource Forbidden", 403);
    }
}
