package com.pandorian.tdd_bdd.service;

import com.pandorian.tdd_bdd.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User signup(User user) {
        return new User();
    }
}
