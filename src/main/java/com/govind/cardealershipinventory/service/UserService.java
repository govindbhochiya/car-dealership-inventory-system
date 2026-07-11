package com.govind.cardealershipinventory.service;

import com.govind.cardealershipinventory.entity.User;
import com.govind.cardealershipinventory.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
    	  // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return user;
    }
}