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
        if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
            throw new RuntimeException("Full name is required");
        }
        if (isValidEmail(user.getEmail())) {
            throw new RuntimeException("Invalid email format");
        }
        if (user.getPassword() == null ||
        	    !user.getPassword().matches(".*[A-Z].*")) {
        	    throw new RuntimeException("Invalid password format");
        }
        return user;
    }
    //helper method
    private boolean isValidEmail(String email) {
        return email != null &&
               email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
}