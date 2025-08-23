package com.pandorian.tdd_bdd.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pandorian.tdd_bdd.entity.User;

@Service
public class UserService {

    @Autowired
    public User signup(@NonNull User user) {
        return new User();
    }
}
