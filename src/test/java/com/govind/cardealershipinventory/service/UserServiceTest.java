package com.govind.cardealershipinventory.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.govind.cardealershipinventory.entity.User;

class UserServiceTest {

    private final UserService userService = new UserService();

    @Test
    void shouldRegisterUserSuccessfully() {

        // Arrange
        User user = new User();
        user.setFullName("Govind");
        user.setEmail("govind@gmail.com");
        user.setPassword("password123");
        user.setRole("USER");

        // Act
        User registeredUser = userService.register(user);

        // Assert
        assertNotNull(registeredUser);
    }
}