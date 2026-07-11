package com.govind.cardealershipinventory.service;

import com.govind.cardealershipinventory.entity.User;
import com.govind.cardealershipinventory.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        return user;
    }
}