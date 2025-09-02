package com.pandorian.tdd_bdd.steps;

import com.pandorian.tdd_bdd.entity.User;
import com.pandorian.tdd_bdd.service.UserService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class UserSteps extends StepsClass {
    @Autowired
    UserService userService;

    @Autowired
    TestContext context;

    final String AliceRowlingPassword = "Alice@123";

    User getAliceRowling() {
        return new User(
                "alice", AliceRowlingPassword,
                "Alice", "Rowling"
        );
    }

    @When("user {string} {string} sign up with {string} {string}")
    public void user_sign_up(String firstName, String lastName, String username, String password) {
        var user = new User(
                username, password, firstName, lastName
        );
        collectApplicationError(() -> {
            userService.signup(user);
        });
    }

    @Given("user with username {string} and password {string} has signed up")
    public void user_has_signed_up(String username, String password) {
        userService.login(username, password);
    }

    /**
     * Following are the same
     * <ul>
     *     <li>Alice Rowling has signed up</li>
     *     <li>Alice Rowling sign up</li>
     * </ul>
     */
    @Given("Alice Rowling has signed up")
    @Then("Alice Rowling sign up")
    public void alice_rowling_has_signed_up() {
        var user = getAliceRowling();

        collectApplicationError(() -> {
            userService.signup(user);
        });
    }

    @Given("user with username {string} and password {string} has logged in")
    public void user_has_logged_in(String username, String password) {
        collectApplicationError(() -> {
            context.user = userService.login(username, password);
        });
    }

    @Given("Alice Rowling has logged in")
    @Then("Alice Rowling log in")
    public void alice_rowling_has_logged_in() {
        var user = getAliceRowling();
        collectApplicationError(() -> {
            context.user = userService.login(user.getUsername(), AliceRowlingPassword);
        });
    }

    @Given("Alice Rowling has signed up and logged in")
    public void alice_rowling_has_signed_up_and_logged_in() {
        alice_rowling_has_signed_up();
        alice_rowling_has_logged_in();
    }

}
