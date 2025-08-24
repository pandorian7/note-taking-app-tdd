package com.pandorian.tdd_bdd.service;

import com.pandorian.tdd_bdd.repository.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pandorian.tdd_bdd.entity.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User signup(@NonNull User user) {
        return userRepository.save(user);
    }

    public User login(String username, String password) {
        return new User();
    }

    public Long count() {
        return 1L;
    }
}
