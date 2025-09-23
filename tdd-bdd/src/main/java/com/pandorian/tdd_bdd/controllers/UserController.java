package com.pandorian.tdd_bdd.controllers;

import com.pandorian.tdd_bdd.entity.User;
import com.pandorian.tdd_bdd.service.UserService;
import com.pandorian.tdd_bdd.types.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        if (user.getUsername() == null) {
            throw new com.pandorian.tdd_bdd.exceptions.RequiredArgumentMissingException("username");
        }
        if (user.isPasswordNull()) {
            throw new com.pandorian.tdd_bdd.exceptions.RequiredArgumentMissingException("password");
        }
        if (user.getFirstName() == null) {
            throw new com.pandorian.tdd_bdd.exceptions.RequiredArgumentMissingException("firstName");
        }
        if (user.getLastName() == null) {
            throw new com.pandorian.tdd_bdd.exceptions.RequiredArgumentMissingException("lastName");
        }
        userService.signup(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserCredentials credentials) {
        if (credentials.getUsername() == null) {
            throw new com.pandorian.tdd_bdd.exceptions.RequiredArgumentMissingException("username");
        }

        if (credentials.getPassword() == null) {
            throw new com.pandorian.tdd_bdd.exceptions.RequiredArgumentMissingException("password");
        }

        userService.login(credentials.getUsername(), credentials.getPassword());
        return ResponseEntity.ok().build();
    }
}
