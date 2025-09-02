package com.pandorian.tdd_bdd.steps;

import com.pandorian.tdd_bdd.exceptions.ApplicationException;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import com.pandorian.tdd_bdd.exceptions.GeneratedConstants;
import static org.junit.jupiter.api.Assertions.*;

public class ErrorSteps {

    @Autowired
    TestContext context;

    @ParameterType(GeneratedConstants.ERROR_REGEX)
    @SuppressWarnings("unchecked")
    public Class<? extends ApplicationException> errorType(String name) {
        try {
            return (Class<? extends ApplicationException>) Class.forName("com.pandorian.tdd_bdd.exceptions." + name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Then("throws error {errorType}")
    public void handleError(Class<? extends ApplicationException> errorClass) {
        assertNotEquals(null, context.error);
        assertEquals(errorClass, context.error.getClass());
        context.error = null;
    }

    /**
     * use <strong>throws error {errorType}</strong>
     * supported values are {@link GeneratedConstants#ERROR_REGEX}
     */
    @Then("Error")
    public String errorHint()  throws Exception {
        throw new Exception();
    }
}
