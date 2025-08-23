package com.pandorian.tdd_bdd.service;

import com.pandorian.tdd_bdd.entity.User;
import com.pandorian.tdd_bdd.exceptions.*;
import com.pandorian.tdd_bdd.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserServiceTest {


    @Autowired
    private UserService userService;

    private User user;

    @BeforeEach
    void init() {
        user = new User(
                "alice", "Alice@123",
                "Alice", "Rowling"
        );
    }

    @Test
    void signup_success() {

        User res = userService.signup(user);

        assertEquals(1, userService.count(), "User Object was not saved.");
        assertNotNull(res.getId(), "User id was not assigned");
    }

    @Test
    void signup_failure_unique() {
        userService.signup(user);

        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.signup(user);
        }, "Username Taken; Unique Constraint was not enforced");
    }

    @Test
    void signup_failure_username_required() {
        user.setUsername("");

        assertThrows(UsernameEmptyException.class, () -> {
            userService.signup(user);
        }, "Username cannot be Empty");
    }

    @Test
    void signup_failure_username_short() {
        user.setUsername("alc");

        assertThrows(UsernameTooShortException.class, () -> {
            userService.signup(user);
        }, "Username Too Short");
    }

    @Test
    void signup_failure_password_required() {
        user.setPassword("");

        assertThrows(PasswordEmptyException.class, () -> {
            userService.signup(user);
        }, "Password cannot be Empty");
    }

    @Test
    void signup_failure_password_short() {
        user.setPassword("alice");

        assertThrows(PasswordTooShortException.class, () -> {
            userService.signup(user);
        }, "Password Too Short");
    }

    @Test
    void signup_failure_password_security() {
        String[] passwords = {
                "alice@123",
                "ALICE@123",
                "alice@Alice",
                "aliceALICE"
        };

        for (String password: passwords) {
            assertThrows(PasswordNotComplexEnoughException.class, () -> {
                user.setPassword(password);
                userService.signup(user);
            }, "Password Complexity Test Failed for password: %s".formatted(password));
        }
    }
    
}
