package com.pandorian.tdd_bdd.steps;

import io.cucumber.java.After;
import org.springframework.beans.factory.annotation.Autowired;

public class SharedHooks {
    @Autowired
    TestContext context;

    @After
    public void checkExceptions() {
        if (context.error != null) {
            throw context.error;
        }
    }
}
