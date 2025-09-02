package com.pandorian.tdd_bdd.steps;

import com.pandorian.tdd_bdd.exceptions.ApplicationException;
import io.cucumber.java.After;
import org.springframework.beans.factory.annotation.Autowired;

public class StepsClass {

    @Autowired
    TestContext context;

    protected void collectApplicationError(Runnable runnable) {
        try {
            runnable.run();
        } catch (ApplicationException err) {
            context.error = err;
        }
    }
}
