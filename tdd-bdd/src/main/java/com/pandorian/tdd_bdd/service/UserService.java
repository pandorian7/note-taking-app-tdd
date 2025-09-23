package com.pandorian.tdd_bdd.service;

import com.pandorian.tdd_bdd.exceptions.*;
import com.pandorian.tdd_bdd.repository.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pandorian.tdd_bdd.entity.User;

@Service
public class UserService {

    private static final int MINIMUM_USERNAME_LENGTH = 5;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;

    @Autowired
    private UserRepository userRepository;

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    public User signup(@NonNull User user) {

        if (existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException();
        }

        if (user.getUsername().isBlank()) {
            throw new UsernameEmptyException();
        }

        if (user.getUsername().length() < MINIMUM_USERNAME_LENGTH) {
            throw new UsernameTooShortException(MINIMUM_USERNAME_LENGTH);
        }

        if (user.isPasswordBlank()) {
            throw new PasswordEmptyException();
        }

        if (user.passwordLength() < MINIMUM_PASSWORD_LENGTH) {
            throw new PasswordTooShortException(user.passwordLength());
        }

        if (user.validatePassword()) {
            throw new PasswordNotComplexEnoughException();
        }

        if (user.getFirstName().isBlank()) {
            throw new NameEmptyException();
        }

        if (user.getLastName().isBlank())  {
            throw new NameEmptyException();
        }

        return userRepository.save(user);
    }

    public User login(String username, String password) {

        if (username.isBlank()) throw new UsernameEmptyException();

        if (password.isBlank()) throw new PasswordEmptyException();

        var maybeUser = userRepository.findByUsernameAndPassword(username, password);

        if (maybeUser.isEmpty()) throw new IncorrectUsernameOrPasswordException();

        return maybeUser.get();
    }

    public Long count() {
        return 1L;
    }
}
