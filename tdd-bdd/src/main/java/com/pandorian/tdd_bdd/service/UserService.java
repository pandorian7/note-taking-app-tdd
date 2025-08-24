package com.pandorian.tdd_bdd.service;

import com.pandorian.tdd_bdd.exceptions.UserAlreadyExistsException;
import com.pandorian.tdd_bdd.exceptions.UsernameEmptyException;
import com.pandorian.tdd_bdd.exceptions.UsernameTooShortException;
import com.pandorian.tdd_bdd.repository.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pandorian.tdd_bdd.entity.User;

@Service
public class UserService {

    private static final int MINIMUM_USERNAME_LENGTH = 5;

    @Autowired
    private UserRepository userRepository;

    public User signup(@NonNull User user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException();
        }

        if (user.getUsername().isBlank()) {
            throw new UsernameEmptyException();
        }

        if (user.getUsername().length() < MINIMUM_USERNAME_LENGTH) {
            throw new UsernameTooShortException(MINIMUM_USERNAME_LENGTH);
        }

        return userRepository.save(user);
    }

    public User login(String username, String password) {
        return new User();
    }

    public Long count() {
        return 1L;
    }
}
