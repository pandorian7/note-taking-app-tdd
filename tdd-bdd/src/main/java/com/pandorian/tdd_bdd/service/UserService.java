package com.pandorian.tdd_bdd.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;

import com.pandorian.tdd_bdd.entity.User;

@Service
public class UserService {

    public User signup(@NonNull User user) {
        return new User();
    }

    public User login(String username, String password) {
        return new User();
    }

    public Long count() {
        return 1L;
    }
}
